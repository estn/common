package com.argyranthemum.common.jpa.base;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


/**
 * 抽象实现类
 * <p>
 * 默认实现不需要的方法. 直接抛出异常
 *
 * @param <T>
 * @param <ID>
 */
public abstract class AbstractBaseRepositoryImpl<T, ID extends Serializable> implements JpaRepositoryImplementation<T, ID>, BaseRepository<T, ID> {

    @Override
    public void setRepositoryMethodMetadata(CrudMethodMetadata crudMethodMetadata) {
    }

    @Override
    public List<T> findAll(Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteInBatch(Iterable<T> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllInBatch() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T getOne(ID id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<T> findOne(Specification<T> spec) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count(Specification<T> spec) {
        throw new UnsupportedOperationException();
    }

}