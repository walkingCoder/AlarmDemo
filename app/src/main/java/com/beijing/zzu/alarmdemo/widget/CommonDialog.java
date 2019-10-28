package com.beijing.zzu.alarmdemo.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beijing.zzu.alarmdemo.R;

/**
 * @author jiayk
 * @date 2019/10/27
 */
public class CommonDialog extends Dialog {

    private TextView titleTv ;

    private Button positiveBn;

    public CommonDialog(Context context) {
        super(context, R.style.CustomDialog);
    }

    private int priority;

    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog);
        setCanceledOnTouchOutside(false);
        initView();
        refreshView();
        initEvent();
    }

    private void initEvent() {
        positiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if ( onClickBottomListener!= null) {
                    onClickBottomListener.onPositiveClick();
                }
            }
        });
    }

    private void refreshView() {
        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
            titleTv.setVisibility(View.VISIBLE);
        }else {
            titleTv.setVisibility(View.GONE);
        }
    }

    @Override
    public void show() {
        super.show();
        refreshView();
    }


    private void initView() {
        positiveBn = (Button) findViewById(R.id.positive);
        titleTv = (TextView) findViewById(R.id.title);
    }

    public OnClickBottomListener onClickBottomListener;

    public CommonDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }
    public interface OnClickBottomListener{
        public void onPositiveClick();
    }

    public int getPriority() {
        return priority;
    }

    public CommonDialog setPriority(int priority) {
        this.priority = priority;
        return this ;
    }

    public String getTitle() {
        return title;
    }

    public CommonDialog setTitle(String title) {
        this.title = title;
        return this ;
    }

}
