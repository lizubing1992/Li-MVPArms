package io.rx_cache.internal.migration;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public enum GetPendingMigrations_Factory implements Factory<GetPendingMigrations> {
  INSTANCE;

  @Override
  public GetPendingMigrations get() {
    return new GetPendingMigrations();
  }

  public static Factory<GetPendingMigrations> create() {
    return INSTANCE;
  }
}
