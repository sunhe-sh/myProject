package com.sunspring.activiti.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询数据查询结果
 *
 * @author zhangyushuang
 * @create 2017-07-11 10:53
 **/
public class PageList<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 每页显示几条记录
     */
    private int size;
    /**
     * 当前页
     */
    private int page;
    /**
     * 查询结果总条数
     */
    private int totalCount;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 查询结果数据
     */
    private List<T> records;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    /**
     * 分页查询返回结果数据
     * @param pageSize
     * @param pageNumber
     * @param totalCount
     * @param records
     * @param <T>
     * @return
     */
    public static <T> PageList<T> success(int pageSize,int pageNumber, int totalCount, List<T> records) {
        PageList ret = new PageList();
        ret.setSize(pageSize);
        ret.setPage(pageNumber);
        ret.setRecords(records);
        ret.setTotalCount(totalCount);
        ret.setTotalPage(totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1);
        return ret;
    }

    @Override
    public String toString() {
        return "PageList{" +
                "size=" + size +
                ", page=" + page +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", records=" + records +
                '}';
    }
}
