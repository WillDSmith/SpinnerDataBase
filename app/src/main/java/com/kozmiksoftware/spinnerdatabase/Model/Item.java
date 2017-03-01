package com.kozmiksoftware.spinnerdatabase.Model;

/**
 * Created by Will on 3/1/2017.
 */

public class Item {
    private int id;
    private String name;

    public Item(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public Item(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
