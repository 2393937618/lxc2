package com.example.lxc.test19.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.lxc.test19.DataBean;
import com.example.lxc.test19.R;


/**
 * Created by hbh on 2017/4/20.
 * 子布局ViewHolder
 */

public class ChildViewHolder extends BaseViewHolder {

    private Context mContext;
    private View view;
    private TextView childLeftText;
    private TextView childRightText;
    private TextView create_time;
    private TextView content_text;
    private TextView circle_type;
    private TextView place_name;
    private TextView like_num;
    private TextView comment_num;


    public ChildViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.view = itemView;
    }

    public void bindView(final DataBean dataBean, final int pos){

        childLeftText = (TextView) view.findViewById(R.id.child_left_text);
        childRightText = (TextView) view.findViewById(R.id.child_right_text);

        place_name = (TextView) view.findViewById(R.id.place_name);
        create_time = (TextView) view.findViewById(R.id.create_time);
        content_text = (TextView) view.findViewById(R.id.content_text);
        circle_type = (TextView) view.findViewById(R.id.type);
        like_num = (TextView) view.findViewById(R.id.like_num);
        comment_num = (TextView) view.findViewById(R.id.comment_num);

        childLeftText.setText(dataBean.getChildLeftTxt());
        childRightText.setText(dataBean.getChildRightTxt());

        place_name.setText(dataBean.getPlace_name());
        create_time.setText(dataBean.getCreate_time());
        content_text.setText(dataBean.getContent_text());
        circle_type.setText(" "+dataBean.getCircle_type());
        like_num.setText(dataBean.getLike_num());
        comment_num.setText(dataBean.getComment_num());

    }
}
