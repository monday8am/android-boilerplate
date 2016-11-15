package com.monday8am.androidboilerplate.data.model;

import android.support.annotation.Nullable;

import java.util.Date;

import io.realm.RealmObject;

public class Profile extends RealmObject {

    private Name mName;

    private String mEmail;

    private String mHexColor;

    private Date mDateOfBirth;

    @Nullable private String mBio;

    @Nullable private String mAvatar;

    public Name getName() {
        return mName;
    }

    public void setName(Name name) {
        this.mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getHexColor() {
        return mHexColor;
    }

    public void setHexColor(String hexColor) {
        this.mHexColor = hexColor;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.mDateOfBirth = dateOfBirth;
    }

    @Nullable
    public String getBio() {
        return mBio;
    }

    public void setBio(@Nullable String bio) {
        this.mBio = bio;
    }

    @Nullable
    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(@Nullable String avatar) {
        this.mAvatar = avatar;
    }

}
