package com.monday8am.androidboilerplate.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.util.Log;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.io.Closeable;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.IOException;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import com.monday8am.androidboilerplate.data.model.Ribot;

@Singleton
public class RealmHelper implements Closeable {

    private final Realm realm;

    @UiThread
    RealmHelper() {
        realm = Realm.getDefaultInstance();
    }

    @UiThread
    public Observable<Ribot> setRibots(final Collection<Ribot> newRibots) {
        return Observable.create(new Observable.OnSubscribe<Ribot>() {
            @Override
            public void call(final Subscriber<? super Ribot> subscriber) {
                if (subscriber.isUnsubscribed()) return;

                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for (Ribot ribot : newRibots) {
                            realm.insert(ribot);
                            subscriber.onNext(ribot);
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
        return realm.where(Ribot.class).findAll().asObservable();
    }

    /**
     * Closes all underlying resources used by the Repository.
     */
    @UiThread
    @Override
    public void close() throws IOException {
        realm.close();
    }

}
