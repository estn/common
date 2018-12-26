package com.argyranthemum.common.domain.pojo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DomainPage<T> implements Serializable {

    private static final long serialVersionUID = 6786042125926490613L;
    private Integer pageIndex;
    private Integer pageSize;
    private Integer pageCount;
    private Integer totalCount;
    private List<T> domains = new ArrayList<>();

    public DomainPage() {
    }

    public DomainPage(Integer pageIndex, Integer pageSize) {
        check(pageIndex, pageSize);

        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = 0;
        if (totalCount % pageSize > 0) {
            pageCount = totalCount / pageSize + 1;
        } else {
            pageCount = totalCount / pageSize;
        }
    }

    public DomainPage(Integer pageIndex, Integer pageSize, Integer totalCount) {
        check(pageIndex, pageSize);
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        if (totalCount % pageSize > 0) {
            pageCount = totalCount / pageSize + 1;
        } else {
            pageCount = totalCount / pageSize;
        }
    }

    private void check(Integer pageIndex, Integer pageSize) {
        if (pageIndex < 0) {
            throw new IllegalArgumentException("pageIndex must greater than 0");
        }

        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize must greater than 0");
        }
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getDomains() {
        return domains;
    }

    public void setDomains(List<T> domains) {
        this.domains = domains;
    }
}
