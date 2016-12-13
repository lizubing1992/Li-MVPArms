package com.jess.arms.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.io.File;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.Cache;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ClientModule_ProvideCacheFactory implements Factory<Cache> {
  private final ClientModule module;

  private final Provider<File> cacheFileProvider;

  public ClientModule_ProvideCacheFactory(ClientModule module, Provider<File> cacheFileProvider) {
    assert module != null;
    this.module = module;
    assert cacheFileProvider != null;
    this.cacheFileProvider = cacheFileProvider;
  }

  @Override
  public Cache get() {
    return Preconditions.checkNotNull(
        module.provideCache(cacheFileProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Cache> create(ClientModule module, Provider<File> cacheFileProvider) {
    return new ClientModule_ProvideCacheFactory(module, cacheFileProvider);
  }
}
