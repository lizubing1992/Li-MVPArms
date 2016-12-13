package com.jess.arms.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ClientModule_ProvideClientFactory implements Factory<OkHttpClient> {
  private final ClientModule module;

  private final Provider<Cache> cacheProvider;

  private final Provider<Interceptor> interceptProvider;

  public ClientModule_ProvideClientFactory(
      ClientModule module, Provider<Cache> cacheProvider, Provider<Interceptor> interceptProvider) {
    assert module != null;
    this.module = module;
    assert cacheProvider != null;
    this.cacheProvider = cacheProvider;
    assert interceptProvider != null;
    this.interceptProvider = interceptProvider;
  }

  @Override
  public OkHttpClient get() {
    return Preconditions.checkNotNull(
        module.provideClient(cacheProvider.get(), interceptProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<OkHttpClient> create(
      ClientModule module, Provider<Cache> cacheProvider, Provider<Interceptor> interceptProvider) {
    return new ClientModule_ProvideClientFactory(module, cacheProvider, interceptProvider);
  }
}
