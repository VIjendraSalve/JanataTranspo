package com.wht.janatatraspo.Model;

import org.json.JSONObject;

public class Varient {

    public String id;
    public String variant;
    public String category_id;

    public Varient(String id, String variant, String category_id) {
        this.id = id;
        this.variant = variant;
        this.category_id = category_id;
    }

    public Varient(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.variant = object.getString("variant");
            this.category_id = object.getString("category_id");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return variant;
    }
}
