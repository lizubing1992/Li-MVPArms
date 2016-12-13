package com.jess.arms.di.module;

import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.rx_cache.internal.RxCache;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ClientModule_ProvideRxCacheFactory implements Factory<RxCache> {
  private final ClientModule module;

  private final Provider<Application> applicationProvider;

  public ClientModule_ProvideRxCacheFactory(
      ClientModule module, Provider<Application> applicationProvider) {
    assert module != null;
    this.module = module;
    assert applicationProvider != null;
    this.applicationProvider = applicationProvider;
  }

  @Override
  public RxCache get() {
    return Preconditions.checkNotNull(
        module.provideRxCache(applicationProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<RxCache> create(
      ClientModule module, Provider<Application> applicationProvider) {
    return new ClientModule_ProvideRxCacheFactory(module, applicationProvider);
  }
}
