package com.airbnb.paris.test

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.widget.TextView
import com.airbnb.paris.Style
import com.airbnb.paris.StyleApplierUtils
import com.airbnb.paris.proxy.TextViewProxyStyleApplier
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StyleApplierUtilsTest {

    lateinit var context: Context
    lateinit var textViewApplier: TextViewProxyStyleApplier
    lateinit var myViewApplier: MyViewStyleApplier

    @Before
    fun setup() {
        context = InstrumentationRegistry.getTargetContext()
        // Necessary to test AppCompat attributes like "?attr/selectableItemBackground"
        // TODO Not working for background() test
        context.setTheme(R.style.Theme_AppCompat)
        textViewApplier = TextViewProxyStyleApplier(TextView(context))
        myViewApplier = MyViewStyleApplier(MyView(context))
    }

    @Test
    fun sameStyle() {
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                Style(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_1),
                Style(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_1))
    }

    @Test
    fun sameAttributes() {
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                Style(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_1),
                Style(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_2))
    }

    @Test(expected = AssertionError::class)
    fun differentAttributes() {
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                Style(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_1),
                Style(R.style.StyleApplierUtilsTest_TextView_textSizePadding))
    }

    @Test
    fun inheritance() {
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                Style(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_1),
                Style(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_withInheritance))
    }

    @Test
    fun subStylesSameStyle() {
        StyleApplierUtils.assertSameAttributes(myViewApplier,
                Style(R.style.StyleApplierUtilsTest_MyView_titleStyle_textColorTextSizePadding_1),
                Style(R.style.StyleApplierUtilsTest_MyView_titleStyle_textColorTextSizePadding_1))
    }

    @Test
    fun subStylesSameAttributes() {
        StyleApplierUtils.assertSameAttributes(myViewApplier,
                Style(R.style.StyleApplierUtilsTest_MyView_titleStyle_textColorTextSizePadding_1),
                Style(R.style.StyleApplierUtilsTest_MyView_titleStyle_textColorTextSizePadding_2))
    }

    @Test(expected = AssertionError::class)
    fun subStylesDifferentAttributes() {
        StyleApplierUtils.assertSameAttributes(myViewApplier,
                Style(R.style.StyleApplierUtilsTest_MyView_titleStyle_textColorTextSizePadding_1),
                Style(R.style.StyleApplierUtilsTest_MyView_titleStyle_textSizePadding))
    }

    @Test
    fun emptyStyleSameStyle() {
        StyleApplierUtils.assertSameAttributes(myViewApplier,
                Style(R.style.Empty),
                Style(R.style.Empty))
    }

    @Test(expected = AssertionError::class)
    fun emptyStyleDifferentAttributes() {
        StyleApplierUtils.assertSameAttributes(myViewApplier,
                Style(R.style.StyleApplierUtilsTest_MyView_titleStyle_textColorTextSizePadding_1),
                Style(R.style.Empty))
    }

    @Test
    fun background() {
        // TODO Why isn't this working?
        //StyleApplierUtils.assertSameAttributes(myViewApplier,
        //        Style(R.style.StyleApplierUtilsTest_MyView_background),
        //        Style(R.style.StyleApplierUtilsTest_MyView_background_other))
    }

    @Test
    fun defaultValues() {
        // Because MyView specifies a default value for active this should be fine

        StyleApplierUtils.assertSameAttributes(myViewApplier,
                Style(R.style.StyleApplierUtilsTest_MyView_active),
                Style(R.style.Empty))
    }
}
