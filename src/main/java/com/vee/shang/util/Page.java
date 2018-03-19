package com.vee.shang.util;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @date 2017/12/14.
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = -4547076275563194814L;
    private int pageNum;
    private int pageSize;
    private long total;
    private boolean hasNextPage;
    private List<T> list;

    public Page(){
        this.pageNum = 1;
        this.pageSize = 10;
        this.total = 0;
        this.list = new ArrayList<>();
        this.hasNextPage = false;
    }

    public Page(List<T> list) {
        PageInfo pageInfo = new PageInfo(list);
        if (pageInfo.getList().size() > 0) {
            this.pageNum = pageInfo.getPageNum();
            this.pageSize = pageInfo.getPageSize();
            this.list = pageInfo.getList();
            this.hasNextPage = pageInfo.isHasNextPage();
            this.total = pageInfo.getTotal();
        } else {
            this.pageNum = 1;
            this.pageSize = 10;
            this.total = 0;
            this.list = new ArrayList<>();
            this.hasNextPage = false;
        }
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
