package com.jess.arms.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * 进一步封装的BaseAdapter
 */
public class Base2Adapter extends BaseAdapter {

    public static final int LIST_STATE_DEFAULT = 0;
    public static final int LIST_STATE_EMPTY = 10; //不足一页
    public static final int LIST_STATE_LOAD_MORE = 11; //加载中
    public static final int LIST_STATE_NO_MORE = 12; //没有更多
    public static final int LIST_STATE_ERROR = 13; //加载失败

    protected LayoutInflater mInflater;

    protected List dataList = new ArrayList();

    public LayoutInflater getLayoutInflater(Context context) {
        if (mInflater == null) {
            mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return mInflater;
    }

    @Override
    public int getCount() {
        return getDataSize();
    }

    @Override
    public Object getItem(int position) {
        if (dataList != null && position >= 0 && dataList.size() > position)
            return dataList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getRealView(position, convertView, parent);
    }

    /**
     * 获取data list size
     * @return
     */
    public int getDataSize() {
        if (dataList != null)
            return dataList.size();
        return 0;
    }

    /**
     * 设置数据list
     * @param list
     */
    public void setData(List list) {
        if(list != null){
            dataList = list;
            notifyDataSetChanged();
        }
    }


    public List getData() {
        return dataList == null ? (dataList = new ArrayList()) : dataList;
    }

    /**
     * 新增数据list
     * @param list
     */
    public void addData(List list) {
        if(list != null && list.size() > 0){
            if (dataList == null)
                dataList = new ArrayList();
            dataList.addAll(list);
            notifyDataSetChanged();
        }
    }


    /**
     * 修改指定的一条数据
     * @param position
     * @param obj
     */
    public void setItem(int position, Object obj) {
        if (dataList != null) {
            dataList.set(position, obj);
            notifyDataSetChanged();
        }
    }


    /**
     * 增加一条数据
     * @param obj
     */
    public void addItem(Object obj) {
        if (dataList != null) {
            dataList = new ArrayList();
        }
        dataList.add(obj);
        notifyDataSetChanged();
    }

    /**
     * 在制定位置后增加一条数据
     * @param position
     * @param obj
     */
    public void addItem(int position, Object obj) {
        if (dataList != null) {
            dataList.add(position, obj);
            notifyDataSetChanged();
        }
    }


    /**
     * 删除指定位置一条
     * @param position
     */
    public void removeItem(int position) {
        if (dataList != null){
            dataList.remove(position);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (dataList != null){
            dataList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 获取界面视图，用于重新封装
     */
    protected View getRealView(int position, View convertView, ViewGroup parent) {
        return null;
    }


}
