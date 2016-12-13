package com.jess.arms.base;

import com.jess.arms.mvp.BasePresenter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BaseFragment_MembersInjector<P extends BasePresenter>
    implements MembersInjector<BaseFragment<P>> {
  private final Provider<P> mPresenterProvider;

  public BaseFragment_MembersInjector(Provider<P> mPresenterProvider) {
    assert mPresenterProvider != null;
    this.mPresenterProvider = mPresenterProvider;
  }

  public static <P extends BasePresenter> MembersInjector<BaseFragment<P>> create(
      Provider<P> mPresenterProvider) {
    return new BaseFragment_MembersInjector<P>(mPresenterProvider);
  }

  @Override
  public void injectMembers(BaseFragment<P> instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.mPresenter = mPresenterProvider.get();
  }

  public static <P extends BasePresenter> void injectMPresenter(
      BaseFragment<P> instance, Provider<P> mPresenterProvider) {
    instance.mPresenter = mPresenterProvider.get();
  }
}
