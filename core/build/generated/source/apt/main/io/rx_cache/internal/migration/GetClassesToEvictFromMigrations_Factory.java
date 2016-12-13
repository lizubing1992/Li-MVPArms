package io.rx_cache.internal.migration;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public enum GetClassesToEvictFromMigrations_Factory
    implements Factory<GetClassesToEvictFromMigrations> {
  INSTANCE;

  @Override
  public GetClassesToEvictFromMigrations get() {
    return new GetClassesToEvictFromMigrations();
  }

  public static Factory<GetClassesToEvictFromMigrations> create() {
    return INSTANCE;
  }
}
