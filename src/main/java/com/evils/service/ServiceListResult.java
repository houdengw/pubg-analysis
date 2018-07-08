package com.evils.service;

import java.util.List;

/**
 * Title: evils
 * CreateDate:  2018/7/8
 *
 * @author houdengw
 * @version 1.0
 */
public class ServiceListResult<T> {
    private long total;
    private List<T> result;

    public ServiceListResult(long total, List<T> result) {
        this.total = total;
        this.result = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getResultSize() {
        if (this.result == null) {
            return 0;
        }
        return this.result.size();
    }
}
