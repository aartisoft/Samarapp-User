package com.smartloan.smtrick.samarapp_user;

public class MainCatalog {

    public String maincat;
    public String mainpro;
    public String subpro;

    public MainCatalog() {
    }

    public MainCatalog(String maincat, String mainpro, String subpro) {
        this.maincat = maincat;
        this.mainpro = mainpro;
        this.subpro = subpro;
    }

    public String getMaincat() {
        return maincat;
    }

    public void setMaincat(String maincat) {
        this.maincat = maincat;
    }

    public String getMainpro() {
        return mainpro;
    }

    public void setMainpro(String mainpro) {
        this.mainpro = mainpro;
    }

    public String getSubpro() {
        return subpro;
    }

    public void setSubpro(String subpro) {
        this.subpro = subpro;
    }
}
