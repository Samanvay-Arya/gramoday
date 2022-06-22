package com.samanvay.gramody.Model;

import com.google.gson.annotations.SerializedName;

public class JsonResponse {
    private String status;

    @SerializedName("name")
    private String name;
    private String language;
    private String loclevel2Name;
    private String loclevel1Name;
    private String loclevel3Name;
    private business business;
    private products[] products;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLoclevel2Name() {
        return loclevel2Name;
    }

    public void setLoclevel2Name(String loclevel2Name) {
        this.loclevel2Name = loclevel2Name;
    }

    public String getLoclevel1Name() {
        return loclevel1Name;
    }

    public void setLoclevel1Name(String loclevel1Name) {
        this.loclevel1Name = loclevel1Name;
    }

    public String getLoclevel3Name() {
        return loclevel3Name;
    }

    public void setLoclevel3Name(String loclevel3Name) {
        this.loclevel3Name = loclevel3Name;
    }

    public com.samanvay.gramody.Model.business getBusiness() {
        return business;
    }

    public void setBusiness(com.samanvay.gramody.Model.business business) {
        this.business = business;
    }

    public products[] getProducts() {
        return products;
    }

    public void setProducts(products[] products) {
        this.products = products;
    }
}
