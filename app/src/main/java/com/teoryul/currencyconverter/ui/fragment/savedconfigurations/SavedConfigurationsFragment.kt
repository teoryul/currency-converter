package com.teoryul.currencyconverter.ui.fragment.savedconfigurations

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teoryul.currencyconverter.R
import com.teoryul.currencyconverter.databinding.FragmentSavedConfigurationsBinding
import com.teoryul.currencyconverter.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_saved_configurations.*

class SavedConfigurationsFragment : BaseFragment<SavedConfigurationsVM, FragmentSavedConfigurationsBinding>() {

    override val viewModelClass = SavedConfigurationsVM::class

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        observeRecViewItemDeleteEvents()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun observeRecViewItemDeleteEvents() {
        viewModel.recViewItemDeleteEvent.observe(this, Observer { event ->
            if (event != null) {
                event.getContent()?.let { currencyPair ->
                    if (isVisible) {
                        showSnackbar(currencyPair)
                    }
                }
            }
        })
    }

    private fun showSnackbar(deletedCurrencyPair: String) {
        val snackbar = Snackbar.make(
            loadingView,
            getString(R.string.snack_bar_deleted).plus(" ").plus(deletedCurrencyPair),
            Snackbar.LENGTH_SHORT
        )
        snackbar.setAction(R.string.snack_bar_undo) { viewModel.onUndoSwipeDelete() }
        snackbar.show()
    }

    override fun getLayoutResId(): Int = R.layout.fragment_saved_configurations

    override fun getTitle(): Int = R.string.title_saved_configurations_fragment
}