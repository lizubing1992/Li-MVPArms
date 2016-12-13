package com.jess.arms.di.module;

import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.io.File;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ClientModule_ProvideCacheFileFactory implements Factory<File> {
  private final ClientModule module;

  private final Provider<Application> applicationProvider;

  public ClientModule_ProvideCacheFileFactory(
      ClientModule module, Provider<Application> applicationProvider) {
    assert module != null;
    this.module = module;
    assert applicationProvider != null;
    this.applicationProvider = applicationProvider;
  }

  @Override
  public File get() {
    return Preconditions.checkNotNull(
        module.provideCacheFile(applicationProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<File> create(
      ClientModule module, Provider<Application> applicationProvider) {
    return new ClientModule_ProvideCacheFileFactory(module, applicationProvider);
  }
}
