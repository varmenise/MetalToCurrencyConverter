package com.valentina.converter_metals.app;

/**
 * Created by valentinaarmenise on 04/04/2014.
 */
public class MetalObject {

    public String metal;
    public String currencyValue;
    public int imageId;


    public MetalObject(String metal, String currencyValue, int imageId) {
        this.imageId = imageId;
        this.currencyValue = currencyValue;
        this.metal = metal;
    }

    public String getMetal() {
        return metal;
    }
    public void setMetal(String metal) {
        this.metal = metal;
    }

    public String getCurrencyValue() {
        return currencyValue;
    }
    public void setCurrencyValue(String currencyValue) {
        this.currencyValue = currencyValue;
    }

    public int getImageId() {
        return imageId;
    }
    public void setImageId(int currencyValue) {
        this.imageId = imageId;
    }

}