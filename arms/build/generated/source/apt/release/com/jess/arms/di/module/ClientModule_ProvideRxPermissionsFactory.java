package com.jess.arms.di.module;

import android.app.Application;
import com.tbruyelle.rxpermissions.RxPermissions;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ClientModule_ProvideRxPermissionsFactory implements Factory<RxPermissions> {
  private final ClientModule module;

  private final Provider<Application> applicationProvider;

  public ClientModule_ProvideRxPermissionsFactory(
      ClientModule module, Provider<Application> applicationProvider) {
    assert module != null;
    this.module = module;
    assert applicationProvider != null;
    this.applicationProvider = applicationProvider;
  }

  @Override
  public RxPermissions get() {
    return Preconditions.checkNotNull(
        module.provideRxPermissions(applicationProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<RxPermissions> create(
      ClientModule module, Provider<Application> applicationProvider) {
    return new ClientModule_ProvideRxPermissionsFactory(module, applicationProvider);
  }
}
