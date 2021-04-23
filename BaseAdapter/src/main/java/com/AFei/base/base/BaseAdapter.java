package com.AFei.base.base;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.AFei.base.R;
import com.AFei.base.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>
{
    private Context mContext;
    private final int resourceId;
    private List<T> mList;
    private SparseArray<View> mHeadeLayout = new SparseArray<>();
    private SparseArray<View> mFooteLayout = new SparseArray<>();
    private View mEmpty;
    private List<T> tempList;
    private int myPosition;


    private static final int TYPE_HEADE = 10000;
    private static final int TYPE_FOOTE = 20000;
    private static final int TYPE_EMPTY = -1;


    public BaseAdapter(Context context, int resourceId)
    {
        mContext = context.getApplicationContext();
        this.resourceId = resourceId;
        mList = new ArrayList<>();
        tempList = new ArrayList<>();
    }

    public void setIndex(int position){
        this.myPosition = position;
    }
    public List<T> getRecentNews(){
        List<T> list = new ArrayList<>();
        if (mList.size() < 1){
            return new ArrayList<>();
        }else if (mList.size() > 30){
            list.addAll(this.mList.subList(0,30));
        }else {
            list.addAll(this.mList.subList(0,mList.size()));
        }

        return list;
    }

    public List<T> getData()
    {
        return mList;
    }

    public void setData(List<T> list)
    {
        if (this.mList != null){
            tempList.addAll(mList);
            this.mList.clear();
            this.mList.addAll(list == null ? new ArrayList<T>() : list);
            this.mList.addAll(tempList);
            //LogUtils.d("Blinger list size    ",this.mList.size()+"");
            tempList.clear();
            notifyDataSetChanged();
        }else {
            this.mList = new ArrayList<T>();
        }
    }

    public void setData(List<T> list, @IntRange(from = 0) int index)
    {
        if (index < 0 || index >= mList.size())
        {
            index = mList.size();
        }
        mList.addAll(index, list == null ? new ArrayList<T>() : list);
        notifyItemRangeInserted(index, list == null ? 0 : list.size());
    }

    public void removeData(int position) {
        this.mList.remove(position);
        notifyItemRemoved(position);
    }

    public void setHeadeView(View view, @IntRange(from = 0) int index)
    {
        if (view == null)
        {
            return;
        }
        int position = mHeadeLayout.indexOfValue(view);
        if (position < 0)
        {
//            if (index < 0 || index >= getHeadeLayoutCount())
//            {
//                index = getHeadeLayoutCount();
//            }
            mHeadeLayout.put(TYPE_HEADE + index, view);
        }
        notifyItemInserted(getHeadeLayoutCount());
    }

    public void setFooteView(View view)
    {
        if (view == null)
        {
            return;
        }
        int position = mFooteLayout.indexOfValue(view);
        if (position < 0)
        {
            mFooteLayout.put(TYPE_FOOTE + getFooteLayoutCount(), view);
        }
        notifyDataSetChanged();
    }


    public void setEmptyView(View view)
    {
        if (view == null)
        {
            return;
        }
        this.mEmpty = view;
        notifyDataSetChanged();
    }

    /**
     * 判断是否是头部View
     * @param position 当前Item位置
     * @return
     */
    private boolean isHeadeView(@IntRange(from = 0) int position)
    {
        if (position >= 0 && position < getHeadeLayoutCount())
        {
            return true;
        }
        return false;
    }

    private int getHeadeLayoutCount()
    {
        return mHeadeLayout.size();
    }


    private boolean isFooteView(@IntRange(from = 0) int position)
    {
        if (position >= getHeadeLayoutCount() + getListCount() && position < getHeadeLayoutCount() + getListCount() + getFooteLayoutCount())
        {
            return true;
        }
        return false;
    }


    private int getFooteLayoutCount()
    {
        return mFooteLayout.size();
    }


    private int getListCount()
    {
        return mList.isEmpty() ? 1 : mList.size();
    }

    private T getItem(@IntRange(from = 0) int position)
    {
        if (mList.isEmpty())
        {
            return null;
        }
        if (position < 0 || position >= getHeadeLayoutCount() + getListCount())
        {
            return null;
        }
        return mList.get(position - getHeadeLayoutCount());
    }

    @Override
    public int getItemViewType(int position)
    {
        if (isHeadeView(position))
        {
            return mHeadeLayout.keyAt(position);
        }
        if (isFooteView(position))
        {
            return mFooteLayout.keyAt(position - getHeadeLayoutCount() - getListCount());
        }
        if (mList.isEmpty())
        {
            return TYPE_EMPTY;
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View layout = null;
        if (mHeadeLayout.get(viewType) != null)
        {
            layout = mHeadeLayout.get(viewType);

        } else if (mFooteLayout.get(viewType) != null)
        {
            layout = mFooteLayout.get(viewType);

        } else if (mList.isEmpty())
        {
            layout = mEmpty == null ? LayoutInflater.from(mContext).inflate(R.layout.layout_empty, parent, false) : mEmpty;
        } else
        {
            layout = LayoutInflater.from(mContext).inflate(resourceId, parent, false);
        }
        return BaseViewHolder.getViewHolder(layout, mContext);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position)
    {
        if (isHeadeView(position) || isFooteView(position))
        {
            return;
        }
        if (mList.isEmpty())
        {
            return;
        }
        convert(holder, position, getItem(position));
    }

    @Override
    public int getItemCount()
    {
        return getHeadeLayoutCount() + getListCount() + getFooteLayoutCount();
    }


    /**
     * RecyclerVeiw.ViewHolder convert
     * @param holder
     * @param position
     * @param t
     */
    public abstract void convert(BaseViewHolder holder, int position, T t);
}
