package me.jessyan.mvparms.demo.di.component;

import com.jess.arms.di.scope.ActivityScope;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import me.jessyan.mvparms.demo.di.module.NewsListModule;

import me.jessyan.mvparms.demo.mvp.ui.fragment.NewsListFragment;

@ActivityScope
@Component(modules = NewsListModule.class, dependencies = AppComponent.class)
public interface NewsListComponent {

  void inject(NewsListFragment fragment);
}