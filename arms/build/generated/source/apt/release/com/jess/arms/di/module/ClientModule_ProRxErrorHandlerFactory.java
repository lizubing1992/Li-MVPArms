package com.jess.arms.di.module;

import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ClientModule_ProRxErrorHandlerFactory implements Factory<RxErrorHandler> {
  private final ClientModule module;

  private final Provider<Application> applicationProvider;

  public ClientModule_ProRxErrorHandlerFactory(
      ClientModule module, Provider<Application> applicationProvider) {
    assert module != null;
    this.module = module;
    assert applicationProvider != null;
    this.applicationProvider = applicationProvider;
  }

  @Override
  public RxErrorHandler get() {
    return Preconditions.checkNotNull(
        module.proRxErrorHandler(applicationProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<RxErrorHandler> create(
      ClientModule module, Provider<Application> applicationProvider) {
    return new ClientModule_ProRxErrorHandlerFactory(module, applicationProvider);
  }
}
