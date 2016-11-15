package com.monday8am.androidboilerplate.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;
import com.monday8am.androidboilerplate.data.local.RealmHelper;
import com.monday8am.androidboilerplate.data.local.PreferencesHelper;
import com.monday8am.androidboilerplate.data.model.Ribot;
import com.monday8am.androidboilerplate.data.remote.RibotsService;

@Singleton
public class DataManager {

    private final RibotsService mRibotsService;
    private final RealmHelper mRealmHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(RibotsService ribotsService, PreferencesHelper preferencesHelper,
                       RealmHelper realmHelper) {
        mRibotsService = ribotsService;
        mPreferencesHelper = preferencesHelper;
        mRealmHelper = realmHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Ribot> syncRibots() {
        return mRibotsService.getRibots()
                .concatMap(new Func1<List<Ribot>, Observable<Ribot>>() {
                    @Override
                    public Observable<Ribot> call(List<Ribot> ribots) {
                        return mRealmHelper.setRibots(ribots);
                    }
                });
    }

    public Observable<RealmResults<Ribot>> getRibots() {
        return mRealmHelper.getRibots().distinct();
    }

}
