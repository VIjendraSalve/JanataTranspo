package com.wht.janatatraspo.Model;

import org.json.JSONObject;

public class MaterialCategory {

    public String id;
    public String parent_id;
    public String category_name;

    public MaterialCategory(String id, String parent_id, String category_name) {
        this.id = id;
        this.parent_id = parent_id;
        this.category_name = category_name;
    }

    public MaterialCategory (JSONObject object) {
        try {
            this.id = object.getString("id");
            this.parent_id = object.getString("parent_id");
            this.category_name = object.getString("category_name");

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

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    @Override
    public String toString() {
        return  category_name;
    }
}
