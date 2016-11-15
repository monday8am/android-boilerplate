package com.monday8am.androidboilerplate.data.model;

import io.realm.RealmObject;


public class Name extends RealmObject {

    private String first;

    private String last;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
