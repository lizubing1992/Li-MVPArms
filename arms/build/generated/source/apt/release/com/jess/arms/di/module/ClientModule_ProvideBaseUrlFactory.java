package com.jess.arms.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import okhttp3.HttpUrl;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ClientModule_ProvideBaseUrlFactory implements Factory<HttpUrl> {
  private final ClientModule module;

  public ClientModule_ProvideBaseUrlFactory(ClientModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public HttpUrl get() {
    return Preconditions.checkNotNull(
        module.provideBaseUrl(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<HttpUrl> create(ClientModule module) {
    return new ClientModule_ProvideBaseUrlFactory(module);
  }
}
