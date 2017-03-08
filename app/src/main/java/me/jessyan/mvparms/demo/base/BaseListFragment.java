package me.jessyan.mvparms.demo.base;

import android.widget.AbsListView;
import com.jess.arms.R;
import com.jess.arms.base.BaseFragment;
import java.util.List;
import me.jessyan.mvparms.demo.widget.EmptyLayout;

public abstract class BaseListFragment extends BaseFragment {

    protected int mState = STATE_NONE;
    protected int pageSize = 10; //一页加载的条数
    protected int mCurrentPage = 1;
    protected EmptyLayout emptyLayout;

    protected BaseListAdapter mListAdapter;
    protected AbsListView mListView;

    // 请求列表数据
    protected abstract void requestList(int page);

    protected AbsListView.OnScrollListener mScrollListener = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            checkFooter();
        }
    };

    /**
     * 加载数据列表结束
     *
     * @param isSuccess 加载是否成功
     * @param list      加载到的数据list
     */
    public void requestListFinish(boolean isSuccess, List list) {

        if (getActivity() == null) {
            return;
        }

        if (mCurrentPage == 1) mState = STATE_REFRESH; //加载第一页的时候当作是刷新状态

        if (mState == STATE_REFRESH) {
            refreshFinish();
            if (isSuccess) {
                if (list != null && list.size() > 0) {
//                    showLoadSuccessLayout();
                    emptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                    mCurrentPage = 2;// 刷新成功
                    mListAdapter.setData(list);
                    if (isCanLoadMore()) {
                        if (list.size() >= pageSize) {
                            mListAdapter.setListState(BaseListAdapter.LIST_STATE_LOAD_MORE);
                        } else {

                            mListAdapter.setListState(BaseListAdapter.LIST_STATE_EMPTY);
                        }
                    } else {
                        mListAdapter.setListState(BaseListAdapter.LIST_STATE_EMPTY);
                    }
                } else {
                    emptyLayout.setErrorType(EmptyLayout.NODATA);
//                    showLoadingFailLayout(getNothingMsg(), null);
                }
            } else {
                emptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                /*showLoadingFailLayout(getString(R.string.error_view_load_error), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshData();
                    }
                });*/
            }
        } else if (mState == STATE_LOAD_MORE) {
            if (isSuccess) {
                if (list == null || list.size() == 0) {
                    mListAdapter.setListState(BaseListAdapter.LIST_STATE_NO_MORE);
                } else {
                    if (list.size() > 0 && list.size() < pageSize) {
                        mListAdapter.setListState(BaseListAdapter.LIST_STATE_NO_MORE);
                    } else {
                        mListAdapter.setListState(BaseListAdapter.LIST_STATE_LOAD_MORE);
                    }
                    mListAdapter.addData(list);
                    mCurrentPage = mCurrentPage + 1;// 加载更多成功
                }
            } else {
                mListAdapter.setListState(BaseListAdapter.LIST_STATE_ERROR);
            }
        }
        mState = STATE_NONE;
    }

    /**
     * 下拉刷新结束
     */
    protected void refreshFinish() {
        if (ptr != null && ptr.isRefreshing()) {
            ptr.refreshComplete();
        }
    }

    /**
     * 刷新列表数据
     */
    @Override
    public void refreshData() {
        super.refreshData();
        mState = STATE_REFRESH;
        requestList(1);
    }

    /**
     * 加载下一页数据
     */
    protected void loadMore() {
        mState = STATE_LOAD_MORE;
        requestList(mCurrentPage);
    }

    /**
     * 是否允许加载更多
     *
     * @return ture为允许加载更多
     */
    protected boolean isCanLoadMore() {
        return true;
    }

    public void checkFooter() {
        if (mState == STATE_NONE
                && mListAdapter != null
                && mListAdapter.getListState() == BaseListAdapter.LIST_STATE_LOAD_MORE
                && mListAdapter.getDataSize() > 0
                && mListView.getLastVisiblePosition() == (
                mListView.getCount() - 1)) {
            loadMore();
        }
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    protected String getNothingMsg() {
        return getString(R.string.error_view_no_data);
    }
}
