package com.teoryul.currencyconverter.adapter

import android.databinding.DataBindingUtil
import android.databinding.ObservableList
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teoryul.currencyconverter.BR
import com.teoryul.currencyconverter.ui.fragment.BaseVM

abstract class BaseRecyclerAdapter<T>(protected val viewModel: BaseVM, private var items: ObservableList<T>) :
    RecyclerView.Adapter<BaseRecyclerAdapter<T>.BaseRecyclerViewHolder<T, ViewDataBinding>>() {

    private var onListChangedCallback: ObservableList.OnListChangedCallback<ObservableList<T>>? = null

    init {
        setOnListChangedCallback()
    }

    abstract fun getLayoutId(): Int

    override fun getItemCount(): Int = if (items.isEmpty()) 0 else items.size

    private fun getViewHolderBinding(parent: ViewGroup, itemLayoutId: Int): ViewDataBinding =
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), itemLayoutId, parent, false)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<T, ViewDataBinding> =
        RecyclerViewHolder(getViewHolderBinding(parent, getLayoutId()))

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<T, ViewDataBinding>, position: Int) {
        holder.bind(items[position] as T)
        holder.binder?.executePendingBindings()
    }

    fun setItems(items: ObservableList<T>) {
        this.items = items
    }

    private fun setOnListChangedCallback() {
        if (onListChangedCallback == null) {
            onListChangedCallback = object : ObservableList.OnListChangedCallback<ObservableList<T>>() {

                override fun onChanged(sender: ObservableList<T>?) = notifyDataSetChanged()

                override fun onItemRangeRemoved(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) =
                    notifyItemRangeRemoved(positionStart, itemCount)

                override fun onItemRangeMoved(
                    sender: ObservableList<T>?,
                    fromPosition: Int,
                    toPosition: Int,
                    itemCount: Int
                ) = notifyDataSetChanged()

                override fun onItemRangeInserted(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) =
                    notifyItemRangeInserted(positionStart, itemCount)

                override fun onItemRangeChanged(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) =
                    notifyItemRangeChanged(positionStart, itemCount)
            }
            items.addOnListChangedCallback(onListChangedCallback)
        }
    }

    abstract inner class BaseRecyclerViewHolder<T, B : ViewDataBinding>(view: View) :
        RecyclerView.ViewHolder(view) {

        var binder: B? = DataBindingUtil.bind(view)

        fun bind(item: T) {
            binder?.setVariable(BR.viewModel, viewModel)
            binder?.setVariable(BR.item, item)
        }
    }

    inner class RecyclerViewHolder(viewDataBinding: ViewDataBinding) :
        BaseRecyclerViewHolder<T, ViewDataBinding>(viewDataBinding.root)
}