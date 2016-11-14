package com.monday8am.androidboilerplate.injection.component;

import dagger.Subcomponent;
import com.monday8am.androidboilerplate.injection.PerActivity;
import com.monday8am.androidboilerplate.injection.module.ActivityModule;
import com.monday8am.androidboilerplate.ui.main.MainActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
