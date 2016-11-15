package com.monday8am.androidboilerplate.data.model;


import android.support.annotation.NonNull;


import io.realm.RealmObject;

public class Ribot extends RealmObject implements Comparable<Ribot> {

    private Profile mProfile;

    public static Ribot create(Profile profile) {
        return new AutoValue_Ribot(profile);
    }

    public Profile getProfile() {
        return mProfile;
    }

    public void setProfile(Profile profile) {
        this.mProfile = profile;
    }

    @Override
    public int compareTo(@NonNull Ribot another) {
        return mProfile
                .getName()
                .getFirst().compareToIgnoreCase(another.mProfile.getName().getFirst());
    }
}

