package com.teoryul.currencyconverter.binding

import android.databinding.BindingAdapter
import android.databinding.ObservableArrayList
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.LinearLayout
import com.teoryul.currencyconverter.adapter.BaseRecyclerAdapter
import com.teoryul.currencyconverter.adapter.SavedConfigurationsRecyclerAdapter
import com.teoryul.currencyconverter.adapter.SearchCurrenciesRecyclerAdapter
import com.teoryul.currencyconverter.adapter.helper.SwipeToDeleteSavedConfigurations
import com.teoryul.currencyconverter.ui.fragment.BaseVM
import com.teoryul.currencyconverter.ui.fragment.savedconfigurations.SavedConfigurationsVM
import com.teoryul.currencyconverter.ui.fragment.searchcurrencies.SearchCurrenciesVM

@BindingAdapter("viewModel", "items", "itemLayoutId", requireAll = true)
fun <T> bindRecyclerView(recView: RecyclerView, viewModel: BaseVM, items: ObservableArrayList<T>, itemLayoutId: Int) {
    if (recView.layoutManager == null) {
        recView.layoutManager = LinearLayoutManager(recView.context, LinearLayout.VERTICAL, false)
    }

    if (recView.adapter == null) {
        if (viewModel is SearchCurrenciesVM) {
            recView.adapter = SearchCurrenciesRecyclerAdapter(viewModel, items, itemLayoutId)

            return
        }
        if (viewModel is SavedConfigurationsVM) {
            recView.adapter = SavedConfigurationsRecyclerAdapter(viewModel, items, itemLayoutId)
            ItemTouchHelper(SwipeToDeleteSavedConfigurations(viewModel)).attachToRecyclerView(recView)

            return
        }
    } else {
        (recView.adapter as BaseRecyclerAdapter<T>).setItems(items)
    }
}