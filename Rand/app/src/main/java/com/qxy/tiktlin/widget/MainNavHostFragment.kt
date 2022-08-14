package com.qxy.tiktlin.widget

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.navigation.fragment.NavHostFragment


class MainNavHostFragment : NavHostFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnApplyWindowInsetsListener { v, insets ->
            //为给定用户加载固定的[用户会话]列表，
            // 因为在片段转换期间，可以同时添加多个片段的视图层次结构。
            // 如果其中一个使用窗口插入，则另一个可能无法正确布局。
            // 要解决这个问题，请确保我们将插入内容分发给所有子项，而不管它们是如何消费的
            (v as? ViewGroup)?.forEach { child ->
                child.dispatchApplyWindowInsets(insets)
            }
            insets
        }
    }
}