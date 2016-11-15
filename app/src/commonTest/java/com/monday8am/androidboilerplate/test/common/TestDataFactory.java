package com.monday8am.androidboilerplate.test.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.monday8am.androidboilerplate.data.model.Name;
import com.monday8am.androidboilerplate.data.model.Profile;
import com.monday8am.androidboilerplate.data.model.Ribot;

/**
 * Factory class that makes instances of data models with random field values.
 * The aim of this class is to help setting up test fixtures.
 */
public class TestDataFactory {

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    public static Ribot makeRibot(String uniqueSuffix) {
        return Ribot.create(makeProfile(uniqueSuffix));
    }

    public static List<Ribot> makeListRibots(int number) {
        List<Ribot> ribots = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            ribots.add(makeRibot(String.valueOf(i)));
        }
        return ribots;
    }

    public static Profile makeProfile(String uniqueSuffix) {
        Profile profile = new Profile();
        profile.setName(makeName(uniqueSuffix));
        profile.setEmail("email" + uniqueSuffix + "@ribot.co.uk");
        profile.setDateOfBirth(new Date());
        profile.setHexColor("#0066FF");
        profile.setAvatar("http://api.ribot.io/images/" + uniqueSuffix);
        profile.setBio(randomUuid());

        return profile;
    }

    public static Name makeName(String uniqueSuffix) {
        return new Name("Name-" + uniqueSuffix, "Surname-" + uniqueSuffix);
    }

}