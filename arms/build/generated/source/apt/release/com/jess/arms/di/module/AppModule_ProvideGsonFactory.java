package com.jess.arms.di.module;

import com.google.gson.Gson;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideGsonFactory implements Factory<Gson> {
  private final AppModule module;

  public AppModule_ProvideGsonFactory(AppModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public Gson get() {
    return Preconditions.checkNotNull(
        module.provideGson(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Gson> create(AppModule module) {
    return new AppModule_ProvideGsonFactory(module);
  }
}
