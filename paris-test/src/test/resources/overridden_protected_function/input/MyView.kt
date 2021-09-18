package com.airbnb.paris.test

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.airbnb.paris.annotations.Attr
import com.airbnb.paris.annotations.Style
import com.airbnb.paris.annotations.Styleable
import com.airbnb.paris.extensions.myViewStyle

@Styleable("Formats")
class MyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : MyViewSuper(context, attrs, defStyle) {

    // Makes the method public
    @Attr(R2.styleable.Formats_formatBoolean)
    public override fun showDivider(show: Boolean)  {

    }
}