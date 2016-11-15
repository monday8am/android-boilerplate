package com.monday8am.androidboilerplate.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import com.monday8am.androidboilerplate.data.DataManager;
import com.monday8am.androidboilerplate.data.SyncService;
import com.monday8am.androidboilerplate.data.local.RealmHelper;
import com.monday8am.androidboilerplate.data.local.PreferencesHelper;
import com.monday8am.androidboilerplate.data.remote.RibotsService;
import com.monday8am.androidboilerplate.injection.ApplicationContext;
import com.monday8am.androidboilerplate.injection.module.ApplicationModule;
import com.monday8am.androidboilerplate.util.RxEventBus;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    RibotsService ribotsService();
    PreferencesHelper preferencesHelper();
    RealmHelper databaseHelper();
    DataManager dataManager();
    RxEventBus eventBus();

}
