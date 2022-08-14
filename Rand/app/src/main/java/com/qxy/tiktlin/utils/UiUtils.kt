package com.qxy.tiktlin.utils

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import kotlinx.coroutines.flow.SharingStarted

private const val StopTimeoutMillis: Long = 5000
/**
 * [SharingStarted]用于与[StateFlow]一起向视图公开数据。
 * 当视图停止观察时，上游流将保持活动状态一段时间，以允许系统运行从短暂的配置更改（如旋转）中恢复。如果视图停止
 * 观察更长时间后，缓存将保留，但上游流将停止。当视图出现时返回时，将重放最新值，并再次执行上游流。
 * 这是为了：应用程序在后台时可以节省资源，用户可以快速切换应用程序。
 */
val WhileViewSubscribed: SharingStarted = SharingStarted.WhileSubscribed(StopTimeoutMillis)



fun View.doOnApplyWindowInsets(f: (View, WindowInsetsCompat, ViewPaddingState) -> Unit) {
//创建视图填充状态的快照
    val paddingState = createStateForView(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        f(v, insets, paddingState)
        insets
    }
    requestApplyInsetsWhenAttached()
}


data class ViewPaddingState(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int,
    val start: Int,
    val end: Int
)


/**
 * Call [View.requestApplyInsets] in a safe away. If we're attached it calls it straight-away.
 * If not it sets an [View.OnAttachStateChangeListener] and waits to be attached before calling
 * [View.requestApplyInsets].
 */
fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}


private fun createStateForView(view: View) = ViewPaddingState(
    view.paddingLeft,
    view.paddingTop,
    view.paddingRight,
    view.paddingBottom,
    view.paddingStart,
    view.paddingEnd
)


/**
以父视图的百分比设置视图应采用的最大宽度。
视图必须是ConstraintLayout的直接子视图。
 */
fun setContentMaxWidth(view: View) {
    val parent = view.parent as? ConstraintLayout ?: return
    val layoutParams = view.layoutParams as ConstraintLayout.LayoutParams
    val screenDensity = view.resources.displayMetrics.density
    val widthDp = parent.width / screenDensity
    val widthPercent = getContextMaxWidthPercent(widthDp.toInt())
    layoutParams.matchConstraintPercentWidth = widthPercent
    view.requestLayout()
}

private fun getContextMaxWidthPercent(maxWidthDp: Int): Float {
    // These match @dimen/content_max_width_percent.
    return when {
        maxWidthDp >= 1024 -> 0.6f
        maxWidthDp >= 840 -> 0.7f
        maxWidthDp >= 600 -> 0.8f
        else -> 1f
    }
}
