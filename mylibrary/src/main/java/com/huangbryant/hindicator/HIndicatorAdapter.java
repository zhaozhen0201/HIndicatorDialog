package com.huangbryant.hindicator;

import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者:huangshuang
 * 事件 16/8/29.
 * 邮箱： 15378412400@163.com
 */
public abstract class HIndicatorAdapter extends RecyclerView.Adapter<HIndicatorAdapter.BaseViewHolder> {


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {


        if (clickable()) {
            holder.getConvertView().setClickable(true);
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(v, position);
                }
            });
        }

        onBindView(holder, holder.getLayoutPosition());

    }

    public abstract void onBindView(BaseViewHolder holder, int position);

    @Override
    public int getItemViewType(int position) {
        return getLayoutID(position);
    }


    public abstract int getLayoutID(int position);

    public abstract boolean clickable();

    public void onItemClick(View v, int position) {
    }


    public static class BaseViewHolder extends RecyclerView.ViewHolder {

        protected final SparseArray<View> mViews;
        protected View mConvertView;


        public BaseViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
            mConvertView = itemView;
        }


        /**
         * 通过控件的Id获取对应的控件，如果没有则加入mViews，则从item根控件中查找并保存到mViews中
         *
         * @param viewId
         * @return
         */
        public <T extends View> T getView(@IdRes int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public View getConvertView() {
            return mConvertView;
        }

        public BaseViewHolder setBgColor(@IdRes int resID, int color) {
            getView(resID).setBackgroundColor(color);
            return this;
        }

        public BaseViewHolder setBgDrawable(@IdRes int resID, Drawable drawable) {
            getView(resID).setBackground(drawable);
            return this;

        }

        public BaseViewHolder setText(@IdRes int resID, String text) {
            ((TextView) getView(resID)).setText(text);
            return this;
        }

        public BaseViewHolder setTextSize(@IdRes int resID, int spSize) {
            ((TextView) getView(resID)).setTextSize(spSize);
            return this;
        }

        public BaseViewHolder setVisibility(@IdRes int resID, @Visibility int visibility) {
            switch (visibility) {
                case VISIBLE:
                    getView(resID).setVisibility(View.VISIBLE);
                    break;
                case INVISIBLE:
                    getView(resID).setVisibility(View.INVISIBLE);
                    break;
                case GONE:
                    getView(resID).setVisibility(View.GONE);
                    break;

            }
            return this;

        }

        public BaseViewHolder setTextColor(int id, int textColor) {
            ((TextView) getView(id)).setTextColor(textColor);
            return this;
        }


        @IntDef({VISIBLE, INVISIBLE, GONE})
        @Retention(RetentionPolicy.SOURCE)
        public @interface Visibility {
        }

        public static final int VISIBLE = 0x00000000;
        public static final int INVISIBLE = 0x00000004;

        public static final int GONE = 0x00000008;


    }

}