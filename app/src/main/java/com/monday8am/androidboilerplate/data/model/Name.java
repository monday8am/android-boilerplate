package com.monday8am.androidboilerplate.data.model;

import io.realm.RealmObject;


public class Name extends RealmObject {

    private String mFirst;

    private String mLast;

    public String getFirst() {
        return mFirst;
    }

    public void setFirst(String first) {
        this.mFirst = first;
    }

    public String getLast() {
        return mLast;
    }

    public void setLast(String last) {
        this.mLast = last;
    }

    public Name () {}

    public Name(String first, String last) {
        mFirst = first;
        mLast = last;
    }

}
