package com.smartloan.smtrick.samarapp_user;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Belal on 2/23/2017.
 */
@IgnoreExtraProperties
public class Upload {

    public String mainproduct;
    public String subproduct;
    public String desc;
    public String name;
    public String url;
    public String poductId;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }


    public Upload(String mainproduct, String subproduct, String desc, String name, String url, String poductId) {
        this.mainproduct = mainproduct;
        this.subproduct = subproduct;
        this.desc = desc;
        this.name = name;
        this.url = url;
        this.poductId = poductId;
    }

    public String getMainproduct() {
        return mainproduct;
    }

    public void setMainproduct(String mainproduct) {
        this.mainproduct = mainproduct;
    }

    public String getSubproduct() {
        return subproduct;
    }

    public void setSubproduct(String subproduct) {
        this.subproduct = subproduct;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getPoductId() {
        return poductId;
    }

    public void setPoductId(String poductId) {
        this.poductId = poductId;
    }

    @Exclude
    public Map<String, Object> getUpdateLeedMap() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("mainproduct", mainproduct);
        objectMap.put("subproduct", subproduct);
        objectMap.put("desc", desc);
        objectMap.put("name", name);
        objectMap.put("url", url);
        objectMap.put("poductId", poductId);

        return objectMap;
    }
}