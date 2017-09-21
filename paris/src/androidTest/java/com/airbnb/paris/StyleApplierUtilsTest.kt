package com.airbnb.paris

import android.graphics.*
import android.support.test.*
import android.support.test.runner.*
import android.util.*
import android.widget.*
import com.airbnb.paris.proxies.*
import com.airbnb.paris.styles.*
import com.airbnb.paris.test.R
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.*

@RunWith(AndroidJUnit4::class)
class StyleApplierUtilsTest {

    private val context = InstrumentationRegistry.getTargetContext()!!
    lateinit var textView: TextView
    lateinit var textViewApplier: TextViewProxyStyleApplier

    init {
        // Necessary to test AppCompat attributes like "?attr/selectableItemBackground"
        // TODO Not working for background() test
        context.setTheme(R.style.Theme_AppCompat)
    }

    @Before
    fun setup() {
        textView = TextView(context)
        textViewApplier = TextViewProxyStyleApplier(textView)
    }

    @Test
    fun sameStyle() {
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_1),
                ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_1))
    }

    @Test
    fun sameAttributes() {
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_1),
                ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_2))
    }

    @Test(expected = AssertionError::class)
    fun differentAttributes() {
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_1),
                ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textSizePadding))
    }

    @Test
    fun inheritance() {
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_1),
                ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_withInheritance))
    }

    @Test
    fun multiStyleResCombinationNoOverlap() {
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_1),
                MultiStyle("MultiStyle_textColorTextSizePadding",
                        R.style.StyleApplierUtilsTest_TextView_textColor,
                        R.style.StyleApplierUtilsTest_TextView_textSizePadding
                )
        )
    }

    @Test
    fun multiStyleResCombinationWithOverlap() {
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_1),
                MultiStyle("MultiStyle_textColorTextSizePadding",
                        R.style.StyleApplierUtilsTest_TextView_textColorTextSize,
                        R.style.StyleApplierUtilsTest_TextView_textSizePadding
                )
        )
    }

    @Test(expected = AssertionError::class)
    fun multiStyleResCombinationMissingAttribute() {
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_1),
                MultiStyle("MultiStyle_textColorTextSize",
                        R.style.StyleApplierUtilsTest_TextView_textColor,
                        R.style.StyleApplierUtilsTest_TextView_textSize
                )
        )
    }

    @Test(expected = AssertionError::class)
    fun multiStyleResCombinationAdditionalAttribute() {
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColorTextSize),
                MultiStyle("MultiStyle_textColorTextSizePadding",
                        R.style.StyleApplierUtilsTest_TextView_textColorTextSize,
                        R.style.StyleApplierUtilsTest_TextView_textSizePadding
                )
        )
    }

    @Test
    fun multiStyleCombinationWithOverlap() {
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_1),
                MultiStyle("MultiStyle_textColorTextSizePadding",
                        ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColorTextSize),
                        textViewApplier.builder()
                                .textSize(R.dimen.test_text_view_text_size)
                                .padding(R.dimen.test_view_padding)
                                .build()
                )
        )
    }

    @Test
    fun multiStyleCombinationNoOverlap() {
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_1),
                MultiStyle("MultiStyle_textColorTextSizePadding",
                        ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColor),
                        textViewApplier.builder()
                                .textSize(R.dimen.test_text_view_text_size)
                                .padding(R.dimen.test_view_padding)
                                .build()
                )
        )
    }

    @Test(expected = AssertionError::class)
    fun multiStyleCombinationMissingAttribute() {
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColorTextSizePadding_1),
                MultiStyle("MultiStyle_textColorTextSize",
                        ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColor),
                        textViewApplier.builder().textSize(R.dimen.test_text_view_text_size).build()
                )
        )
    }

    @Test(expected = AssertionError::class)
    fun multiStyleCombinationAdditionalAttribute() {
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColorTextSize),
                MultiStyle("MultiStyle_textColorTextSizePadding",
                        ResourceStyle(R.style.StyleApplierUtilsTest_TextView_textColor),
                        textViewApplier.builder()
                                .textSize(R.dimen.test_text_view_text_size)
                                .padding(R.dimen.test_view_padding)
                                .build()
                )
        )
    }

    @Test(expected = AssertionError::class)
    fun missingFromBoth() {
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                textViewApplier.builder()
                        .textSize(R.dimen.test_text_view_text_size)
                        .build(),
                textViewApplier.builder()
                        .padding(R.dimen.test_view_padding)
                        .build(),
                textViewApplier.builder()
                        .textColor(Color.GREEN)
                        .build()
        )
    }

    @Test
    fun styleNotApplied() {
        // Checks that running the same attributes assertion doesn't actually modify the applier's
        // view in any way

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 10f)
        assertEquals(10f, textView.textSize)
        StyleApplierUtils.assertSameAttributes(textViewApplier,
                textViewApplier.builder()
                        .textSize(15)
                        .build(),
                textViewApplier.builder()
                        .textSize(15)
                        .build()
        )
        assertEquals(10f, textView.textSize)
    }
}