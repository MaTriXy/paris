package com.airbnb.paris.views.kotlin

import android.content.Context
import android.util.AttributeSet
import android.view.View

import com.airbnb.paris.R2
import com.airbnb.paris.annotations.Styleable
import com.airbnb.paris.annotations.StyleableChild

@Styleable("Test_WithStyleableChildInternalView")
class WithStyleableChildInternalView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    @StyleableChild(R2.styleable.Test_WithStyleableChildInternalView_test_arbitraryStyle)
    internal val arbitrarySubView = View(context)
}
