package com.monday8am.androidboilerplate.data.local;

import android.support.annotation.UiThread;
import android.util.Log;

import java.io.Closeable;
import java.util.Collection;

import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.IOException;
import rx.Observable;
import rx.Subscriber;

import com.monday8am.androidboilerplate.data.model.Ribot;

@Singleton
public class RealmHelper implements Closeable {

    private final Realm mRealm;

    @UiThread
    public RealmHelper() {
        mRealm = Realm.getDefaultInstance();
    }

    @UiThread
    public Observable<Ribot> setRibots(final Collection<Ribot> newRibots) {
        return Observable.create(new Observable.OnSubscribe<Ribot>() {
            @Override
            public void call(final Subscriber<? super Ribot> subscriber) {
                if (subscriber.isUnsubscribed()) return;

                mRealm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for (Ribot ribot : newRibots) {
                            Ribot managedRibot = realm.copyToRealm(ribot);
                            subscriber.onNext(managedRibot);
                        }
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        subscriber.onCompleted();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Log.d(RealmHelper.class.getName(), error.getMessage());
                        subscriber.onError(error);
                    }
                });
            }
        });
    }

    public Observable<RealmResults<Ribot>> getRibots() {
        return mRealm.where(Ribot.class).findAll().asObservable();
    }

    /**
     * Closes all underlying resources used by the Repository.
     */
    @UiThread
    @Override
    public void close() throws IOException {
        mRealm.close();
    }

}
