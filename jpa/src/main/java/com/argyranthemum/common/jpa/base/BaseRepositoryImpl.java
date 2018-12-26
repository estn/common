package com.argyranthemum.common.jpa.base;

import com.argyranthemum.common.domain.BaseDomain;
import com.argyranthemum.common.domain.enumeration.AvailableEnum;
import com.argyranthemum.common.domain.pojo.DomainCursor;
import com.argyranthemum.common.domain.pojo.DomainPage;
import com.argyranthemum.common.jpa.condition.*;
import com.google.common.collect.Lists;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * BaseRepository只实现CRUD中的方法
 * <p>
 * JpaRepositoryImplementation中的方法不实现. 实际情况中不会使用
 *
 * @param <T>  实体类型
 * @param <ID> 主键ID类型
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends AbstractBaseRepositoryImpl<T, ID> {

    private final Class<T> domainClass;

    private final EntityManager em;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        this.domainClass = domainClass;
        this.em = em;
    }

    @Transactional
    @Override
    public <S extends T> S save(S entity) {
        BaseDomain domain = (BaseDomain) entity;
        domain.setUpdateTime(new Date());
        em.persist(domain);
        return entity;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<T> findById(ID id) {
        T t = em.find(domainClass, id);
        if (t == null) {
            return Optional.empty();
        }

        BaseDomain domain = (BaseDomain) t;
        if (AvailableEnum.UNAVAILABLE.equals(domain.getAvailable())) {
            return Optional.empty();
        }

        return Optional.ofNullable(t);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsById(ID id) {
        Optional<T> optional = findById(id);
        return optional.isPresent();
    }

    @Transactional(readOnly = true)
    @Override
    public long count() {
        String jpql = "select count(c) from " + domainClass.getName() + " c where c.available = 1";
        Query query = em.createQuery(jpql);
        return (Long) query.getResultList().get(0);
    }

    @Transactional
    @Override
    public void deleteById(ID id) {
        T t = findById(id).get();
        BaseDomain domain = (BaseDomain) t;
        domain.setAvailable(AvailableEnum.UNAVAILABLE);
        domain.setUpdateTime(new Date());
        em.merge(domain);
    }

    @Transactional
    @Override
    public void delete(T entity) {
        BaseDomain domain = (BaseDomain) entity;
        ID id = (ID) domain.getId();
        BaseDomain t = (BaseDomain) findById(id).get();
        t.setAvailable(AvailableEnum.UNAVAILABLE);
        t.setUpdateTime(new Date());
        em.merge(t);
    }

    @Transactional
    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        for (T entity : entities) {
            delete(entity);
        }
    }

    @Transactional
    @Override
    public void deleteAll() {
        String jpql = "update " + domainClass.getName() + " c set c.available = 0";
        Query query = em.createQuery(jpql);
        query.executeUpdate();
    }

    @Transactional
    @Override
    public T update(T entity) {
        BaseDomain domain = (BaseDomain) entity;
        domain.setUpdateTime(new Date());
        em.merge(domain);
        return entity;
    }

    @Transactional
    @Override
    public T refresh(T entity) {
        em.refresh(entity);
        return entity;
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> select(SQL sql) {
        String jpql = getDefaultSQL(sql);
        jpql = jpql + getWheres(sql);
        Query query = em.createQuery(jpql);
        setWheres(query, sql);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public DomainPage<T> selectByPage(SQL sql, int pageIndex, int pageSize) {

        List<T> list = getList(sql, pageIndex, pageSize);

        String countSQL = getDefaultCountSQL(sql);
        countSQL = countSQL + getWheres(sql);
        Query query = em.createQuery(countSQL);
        setWheres(query, sql);
        Integer totalCount = ((Long) query.getResultList().get(0)).intValue();

        DomainPage<T> domainPage = new DomainPage<T>(pageIndex, pageSize, totalCount);
        domainPage.getDomains().addAll(list);
        return domainPage;
    }

    @Transactional(readOnly = true)
    @Override
    public DomainCursor<T> selectByCursor(SQL sql, int cursor, int count) {
        List<T> list = getList(sql, cursor, count);
        int nextCursor = DomainCursor.END_CURSOR;
        if (list.size() == count) {
            nextCursor = cursor + 1;
        }
        return new DomainCursor(list, nextCursor);
    }

    private String getDefaultSQL(SQL sql) {
        if (!sql.getAvailable()) {
            return "SELECT c FROM " + domainClass.getName() + " c WHERE 1 = 1";
        }
        return "SELECT c FROM " + domainClass.getName() + " c WHERE c.available = 1";
    }

    private String getDefaultCountSQL(SQL sql) {
        if (!sql.getAvailable()) {
            return "SELECT COUNT(c) FROM " + domainClass.getName() + " c WHERE 1 = 1";
        }
        return "SELECT COUNT(c) FROM " + domainClass.getName() + " c WHERE c.available = 1";
    }

    private String getWheres(SQL sql) {

        if (sql == null) {
            return "";
        }

        List<Where> wheres = sql.getWheres();
        if (wheres == null || wheres.size() == 0) {
            return "";
        }

        String whereString = "";

        int position = 1;
        for (Where where : wheres) {
            if (where.getOperation().equals(Operation.IS_NULL) || where.getOperation().equals(Operation.IS_NOT_NULL)) {
                whereString = whereString + " AND c." + where.getField() + " " + where.getOperation().getValue();
                continue;
            }
            whereString = whereString + " AND c." + where.getField() + " " + where.getOperation().getValue() + " ?" + position;
            position++;
        }

        return whereString;
    }

    private String getOrders(SQL sql) {
        if (sql == null) {
            return "";
        }

        List<Order> orders = sql.getOrders();
        if (orders == null || orders.size() == 0) {
            return "";
        }

        StringBuilder orderByString = new StringBuilder(" ORDER BY");
        for (Order order : orders) {
            orderByString.append(" ").append(order.getField()).append(" ").append(order.getOrderBy().name()).append(",");
        }

        String orderBy = orderByString.toString();
        orderBy = orderBy.substring(0, orderBy.length() - 1);
        return orderBy;
    }

    private void setWheres(Query query, SQL sql) {

        if (sql == null) {
            return;
        }

        List<Where> wheres = sql.getWheres();
        if (wheres == null || wheres.size() == 0) {
            return;
        }

        int position = 1;
        for (Where where : wheres) {
            Object value = where.getValue();
            if ("like".equals(where.getOperation().getValue())) {
                switch (where.getOperation()) {
                    case ALL_LIKE:
                        value = "%" + value + "%";
                        break;
                    case LEFT_LIKE:
                        value = "%" + value;
                        break;
                    case RIGHT_LIKE:
                        value = value + "%";
                        break;
                }
            }
            if (Operation.IS_NULL.equals(where.getOperation()) || Operation.IS_NOT_NULL.equals(where.getOperation())) {
                continue;
            }
            query.setParameter(position, value);
            position++;
        }

    }

    private List<T> getList(SQL sql, int cursor, int count) {
        String jpql = getDefaultSQL(sql);
        jpql = jpql + getWheres(sql);
        jpql = jpql + getOrders(sql);
        Query query = em.createQuery(jpql);
        setWheres(query, sql);
        query.setFirstResult(cursor * count);
        query.setMaxResults(count);
        return query.getResultList();
    }

    @Override
    public List<T> findAll() {
        SQL sql = SQLBuilder.instance()
                .build();
        return this.select(sql);
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        SQL sql = SQLBuilder.instance()
                .where("id", Operation.IN, ids)
                .build();
        return this.select(sql);
    }

    @Transactional
    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        List<S> results = Lists.newArrayList();
        for (S entity : entities) {
            entity = this.save(entity);
            results.add(entity);
        }
        return results;
    }
}

