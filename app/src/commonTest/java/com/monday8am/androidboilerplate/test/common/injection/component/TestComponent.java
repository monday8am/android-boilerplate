package com.monday8am.androidboilerplate.test.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import com.monday8am.androidboilerplate.injection.component.ApplicationComponent;
import com.monday8am.androidboilerplate.test.common.injection.module.ApplicationTestModule;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
