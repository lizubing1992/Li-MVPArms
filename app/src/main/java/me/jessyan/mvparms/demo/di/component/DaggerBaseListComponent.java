/*
 * All rights Reserved, Designed By 农金圈  2017年04月01日17:59
 */
package me.jessyan.mvparms.demo.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import dagger.Component;
import me.jessyan.mvparms.demo.di.module.DaggerBaseListModule;
import me.jessyan.mvparms.demo.mvp.ui.fragment.DaggerBaseListFragment;

/**
 * 类的作用
 *
 * @author: lizubing
 */
@ActivityScope
@Component(modules = DaggerBaseListModule.class, dependencies = AppComponent.class)
public interface DaggerBaseListComponent {

  void inject(DaggerBaseListFragment activity);
}