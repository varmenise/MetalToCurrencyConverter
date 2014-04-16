package com.valentina.converter_metals.app;

/**
 * Created by valentinaarmenise on 06/04/2014.
 */

import java.util.List;
public class Currency {

    private String currency_name;
    private List<Metal> metals_value;



    public List<Metal> getMetals_value() {
        return metals_value;
    }

    public void setMetals_value(List<Metal> metals_value) {
        this.metals_value = metals_value;
    }



    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }
}
