package com.teoryul.currencyconverter.adapter

import android.databinding.ObservableArrayList
import com.teoryul.currencyconverter.ui.fragment.BaseVM

class SavedConfigurationsRecyclerAdapter<T>(
    viewModel: BaseVM,
    items: ObservableArrayList<T>,
    private val layoutId: Int
) : BaseRecyclerAdapter<T>(viewModel, items) {

    override fun getLayoutId(): Int = layoutId
}