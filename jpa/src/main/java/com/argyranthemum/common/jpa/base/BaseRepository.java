package com.argyranthemum.common.jpa.base;

import com.argyranthemum.common.domain.pojo.DomainCursor;
import com.argyranthemum.common.domain.pojo.DomainPage;
import com.argyranthemum.common.jpa.condition.SQL;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {

    /**
     * 通过ID查询.
     * 注: 逻辑删除的数据也会查询
     *
     * @param id 主键ID
     * @return
     */
    T queryById(ID id);

    /**
     * 通过字段查询.
     *
     * @param field 字段名称
     * @param value 字段值
     * @return
     */
    List<T> findByName(String field, String value);

    /**
     * 更新实体
     *
     * @param entity 实体
     * @return
     */
    T update(T entity);

    /**
     * 合并实体
     *
     * @param entity 实体
     * @return
     */
    T merge(T entity);


    /**
     * 删除实体. 数据库执行delete操作
     *
     * @param id
     */
    void remove(ID id);

    /**
     * 刷新实体
     *
     * @param entity 实体
     * @return
     */
    T refresh(T entity);

    /**
     * 根据SQL查询数据
     *
     * @param sql 查询条件
     * @return List
     */
    List<T> select(SQL sql);

    /**
     * 分页查询数据
     *
     * @param sql       查询条件
     * @param pageIndex 页码. 从1开始
     * @param pageSize  每页数量
     * @return DomainPage
     */
    DomainPage<T> selectByPage(SQL sql, int pageIndex, int pageSize);

    /**
     * 游标查询数据
     * <p>
     * 用于不返回TotalCount的情况
     *
     * @param sql    查询条件
     * @param cursor 游标
     * @param count  每页数量
     * @return DomainCursor
     */
    DomainCursor<T> selectByCursor(SQL sql, int cursor, int count);

    /**
     * 获取EntityManager
     *
     * @return
     */
    EntityManager getEntityManager();

}