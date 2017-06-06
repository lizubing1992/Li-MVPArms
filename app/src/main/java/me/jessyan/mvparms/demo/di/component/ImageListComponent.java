package me.jessyan.mvparms.demo.di.component;

import com.jess.arms.di.scope.ActivityScope;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import me.jessyan.mvparms.demo.di.module.ImageListModule;

import me.jessyan.mvparms.demo.mvp.ui.fragment.ImageListFragment;

@ActivityScope
@Component(modules = ImageListModule.class, dependencies = AppComponent.class)
public interface ImageListComponent {

  void inject(ImageListFragment fragment);
}