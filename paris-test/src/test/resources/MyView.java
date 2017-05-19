package com.airbnb.paris.test;

import android.content.Context;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.airbnb.paris.annotations.Attr;
import com.airbnb.paris.annotations.Styleable;

@Styleable("MyView")
public class MyView extends View {

    @Attr(R2.styleable.MyView_titleStyle)
    TextView title;

    @Attr(R2.styleable.MyView_verticalPadding)
    @Px
    int verticalPadding;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Attr(R2.styleable.MyView_title)
    public void setTitle(String title) {

    }

    @Attr(R2.styleable.MyView_subtitle)
    public void setSubitle(String subtitle) {

    }
}