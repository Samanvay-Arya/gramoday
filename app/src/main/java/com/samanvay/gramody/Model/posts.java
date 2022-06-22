package com.samanvay.gramody.Model;

public class posts {
    String rawReportPriceUnit;
    String marketType;
    String marketStdName;
    String loclevel3Name;
    String loclevel2ShortName;
    String createdAt;
    String updatedAt;
    String cmdtyStdName;

    public Computed getComputed() {
        return computed;
    }

    public void setComputed(Computed computed) {
        this.computed = computed;
    }

    Computed computed;


    public String getCmdtyStdName() {
        return cmdtyStdName;
    }

    public void setCmdtyStdName(String cmdtyStdName) {
        this.cmdtyStdName = cmdtyStdName;
    }

    public String getRawReportPriceUnit() {
        return rawReportPriceUnit;
    }

    public void setRawReportPriceUnit(String rawReportPriceUnit) {
        this.rawReportPriceUnit = rawReportPriceUnit;
    }

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public String getMarketStdName() {
        return marketStdName;
    }

    public void setMarketStdName(String marketStdName) {
        this.marketStdName = marketStdName;
    }

    public String getLoclevel3Name() {
        return loclevel3Name;
    }

    public void setLoclevel3Name(String loclevel3Name) {
        this.loclevel3Name = loclevel3Name;
    }

    public String getLoclevel2ShortName() {
        return loclevel2ShortName;
    }

    public void setLoclevel2ShortName(String loclevel2ShortName) {
        this.loclevel2ShortName = loclevel2ShortName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }



}
