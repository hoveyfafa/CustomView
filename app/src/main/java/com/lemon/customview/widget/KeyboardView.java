package com.lemon.customview.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lemon.customview.R;
import com.lemon.customview.other.KeyboardDecoration;
import com.lemon.customview.utils.ResourceUtil;

public class KeyboardView extends RelativeLayout {

    private MyAdapter mAdapter;
    private String[] keys = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "0", "完成"};
    private static final int TYPE_COMMON = 996;
    private static final int TYPE_DELETE = 965;

    private OnItemClickListener mOnItemClickListener;


    public KeyboardView(Context context) {
        this(context, null);
    }

    public KeyboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int customWidth = 400;
        int customHeight = ResourceUtil.dp2px(getContext(), 205f);


        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(customWidth, customHeight);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(customWidth, height);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(width, customHeight);
        }
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.layout_keyboard, null);
        addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new MyAdapter();
        rv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rv.addItemDecoration(new KeyboardDecoration(3, ResourceUtil.dp2px(getContext(), 5f), true));
        rv.setAdapter(mAdapter);
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView;
            switch (viewType) {
                case TYPE_DELETE:
                    itemView = View.inflate(parent.getContext(), R.layout.delete_key, null);
                    break;
                default:
                    itemView = View.inflate(parent.getContext(), R.layout.common_key, null);
                    break;
            }
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case TYPE_COMMON:
                    ((TextView) holder.itemView.findViewById(R.id.tv_key)).setText(keys[position]);
                    holder.itemView.findViewById(R.id.ll_key).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onItemClick(holder.getAdapterPosition() + 1);
                            }
                        }
                    });
                    break;
                case TYPE_DELETE:
                    holder.itemView.findViewById(R.id.ll_delete).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onItemClick(holder.getAdapterPosition() + 1);
                            }
                        }
                    });
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return keys.length;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 9)
                return TYPE_DELETE;
            else
                return TYPE_COMMON;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
