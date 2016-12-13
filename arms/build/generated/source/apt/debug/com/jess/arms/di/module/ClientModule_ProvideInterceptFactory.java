package com.jess.arms.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import okhttp3.Interceptor;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ClientModule_ProvideInterceptFactory implements Factory<Interceptor> {
  private final ClientModule module;

  public ClientModule_ProvideInterceptFactory(ClientModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public Interceptor get() {
    return Preconditions.checkNotNull(
        module.provideIntercept(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Interceptor> create(ClientModule module) {
    return new ClientModule_ProvideInterceptFactory(module);
  }
}
