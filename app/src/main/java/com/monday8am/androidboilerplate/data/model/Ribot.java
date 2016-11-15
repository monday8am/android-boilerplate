package com.monday8am.androidboilerplate.data.model;


import android.support.annotation.NonNull;


import io.realm.RealmObject;

public class Ribot extends RealmObject implements Comparable<Ribot> {

    private Profile profile;

    public static Ribot create(Profile profile) {
        return new AutoValue_Ribot(profile);
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public int compareTo(@NonNull Ribot another) {
        return profile.getName().getFirst().compareToIgnoreCase(another.profile.getName().getFirst());
    }
}

