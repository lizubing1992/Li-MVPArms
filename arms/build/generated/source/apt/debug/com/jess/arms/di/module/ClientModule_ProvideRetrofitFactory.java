package com.jess.arms.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ClientModule_ProvideRetrofitFactory implements Factory<Retrofit> {
  private final ClientModule module;

  private final Provider<OkHttpClient> clientProvider;

  private final Provider<HttpUrl> httpUrlProvider;

  public ClientModule_ProvideRetrofitFactory(
      ClientModule module,
      Provider<OkHttpClient> clientProvider,
      Provider<HttpUrl> httpUrlProvider) {
    assert module != null;
    this.module = module;
    assert clientProvider != null;
    this.clientProvider = clientProvider;
    assert httpUrlProvider != null;
    this.httpUrlProvider = httpUrlProvider;
  }

  @Override
  public Retrofit get() {
    return Preconditions.checkNotNull(
        module.provideRetrofit(clientProvider.get(), httpUrlProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Retrofit> create(
      ClientModule module,
      Provider<OkHttpClient> clientProvider,
      Provider<HttpUrl> httpUrlProvider) {
    return new ClientModule_ProvideRetrofitFactory(module, clientProvider, httpUrlProvider);
  }
}
