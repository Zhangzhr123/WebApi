package com.authine.cloudpivot.mybatis.entity;

public class LCBEntity {
    private String parentId;
    private Integer sortKey;
    private String id;
    private String lcbmc;
    private String fktj;
    private String fkbl;
    private String je;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSortKey() {
        return sortKey;
    }

    public void setSortKey(Integer sortKey) {
        this.sortKey = sortKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLcbmc() {
        return lcbmc;
    }

    public void setLcbmc(String lcbmc) {
        this.lcbmc = lcbmc;
    }

    public String getFktj() {
        return fktj;
    }

    public void setFktj(String fktj) {
        this.fktj = fktj;
    }

    public String getFkbl() {
        return fkbl;
    }

    public void setFkbl(String fkbl) {
        this.fkbl = fkbl;
    }

    public String getJe() {
        return je;
    }

    public void setJe(String je) {
        this.je = je;
    }
}
