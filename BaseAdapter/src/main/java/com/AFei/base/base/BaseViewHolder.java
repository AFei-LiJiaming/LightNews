package com.AFei.base.base;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.AFei.base.utils.CircleTransform;
import com.AFei.base.utils.LogUtils;

import butterknife.ButterKnife;

public class BaseViewHolder extends RecyclerView.ViewHolder
{
    private Context mContext;
    private final SparseArray<View> mViews;

    private BaseViewHolder(View itemView, Context context)
    {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mViews = new SparseArray<>();
        mContext = context.getApplicationContext();
    }

    public static BaseViewHolder getViewHolder(View itemView, Context context)
    {
        return new BaseViewHolder(itemView, context);
    }


    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = itemView.findViewById(viewId);
            if (view != null)
            {
                mViews.put(viewId, view);
            }
        }
        return (T) view;
    }

    /**
     * Will set an picture of the ImageView for the picture link
     *
     * @param imageUrls The link of the Picture
     * @param viewId    The ImageView id
     * @return The BaseViewHolder for chaining
     */
    public BaseViewHolder setImageResource(String imageUrls, @IdRes int viewId)
    {
        imageUrls = imageUrls.replace("\\","");
        ImageView mView = getView(viewId);
        //LogUtils.d("Blinger imageurl    ",imageUrls);

        Picasso.with(mContext)
                .load(imageUrls)
                .fit()
                .centerCrop()
                .into(mView);
        return this;
    }

    /**
     * Will set the image of an ImageView from a resource id.
     *
     * @param resourceId The image resource id.
     * @param viewId     The view id.
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setImageResource(@DrawableRes int resourceId, @IdRes int viewId)
    {
        ImageView mView = getView(viewId);
        mView.setImageResource(resourceId);
        return this;
    }

    /**
     * Will set Circle Picture of the ImageView
     *
     * @param imageUrls The link of the picture
     * @param viewId    The ImageView id
     * @return The BaseViewHolder for chaining
     */
    public BaseViewHolder setCircleImageResource(String imageUrls, @IdRes int viewId)
    {
        ImageView mView = getView(viewId);
        Picasso.with(mContext)
                .load(imageUrls)
                .transform(new CircleTransform())
                .fit()
                .centerCrop()
                .into(mView);
        return this;
    }

    /**
     * Will set the Text of the TextVeiw
     *
     * @param text   The text to put in the TextView
     * @param viewId The View id
     * @return The BaseViewHolder for chaining
     */
    public BaseViewHolder setText(String text, @IdRes int viewId)
    {
        TextView mView = getView(viewId);
        mView.setText(text);
        return this;
    }

    /**
     * Will set background color of a view.
     *
     * @param viewId The view id.
     * @param color  A color, not a resource id.
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color)
    {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    /**
     * Will set background drawable of a view.
     *
     * @param viewId The view id.
     * @param resId  A drawable, not a color id.
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setBackgroundResource(@IdRes int viewId, @DrawableRes int resId)
    {
        View view = getView(viewId);
        view.setBackgroundResource(resId);
        return this;
    }


    /**
     * Will set text color of a TextView.
     *
     * @param viewId    The view id.
     * @param textColor The text color (not a resource id).
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setTextColor(@IdRes int viewId, @ColorInt int textColor)
    {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        //LogUtils.d("Blinger____NewFragment","set color");
        return this;
    }

    /**
     * Set a view visibility to VISIBLE (true) or GONE (false).
     *
     * @param viewId  The view id.
     * @param visible True for VISIBLE, false for GONE.
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setGone(@IdRes int viewId, boolean visible)
    {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * Set a view visibility to VISIBLE (true) or INVISIBLE (false).
     *
     * @param viewId  The view id.
     * @param visible True for VISIBLE, false for INVISIBLE.
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setVisible(@IdRes int viewId, boolean visible)
    {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * Sets the on click listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on click listener;
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setOnClickListener(@IdRes int viewId, View.OnClickListener listener)
    {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

}
