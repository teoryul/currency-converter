package com.teoryul.currencyconverter.ui.fragment.searchcurrencies

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuInflater
import com.teoryul.currencyconverter.R
import com.teoryul.currencyconverter.data.CurrencyType
import com.teoryul.currencyconverter.databinding.FragmentSearchCurrenciesBinding
import com.teoryul.currencyconverter.ui.fragment.BaseFragment
import com.teoryul.currencyconverter.utils.BUNDLE_KEY_CURRENCY_TYPE

class SearchCurrenciesFragment : BaseFragment<SearchCurrenciesVM, FragmentSearchCurrenciesBinding>() {

    override val viewModelClass = SearchCurrenciesVM::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        setCurrencyType()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_search_currencies, menu)
        setupSearchView(menu?.findItem(R.id.menu_item_search)?.actionView as SearchView)
    }

    override fun getLayoutResId(): Int = R.layout.fragment_search_currencies

    override fun getTitle(): Int = R.string.title_search_currencies_fragment

    private fun setCurrencyType() {
        val value = arguments?.getInt(BUNDLE_KEY_CURRENCY_TYPE)
        if (value != null) {
            val currencyType = CurrencyType.findByValue(value)
            if (currencyType != null) {
                viewModel.currencyTypeToReplace = currencyType
            }
        }
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.queryHint = getString(R.string.menu_item_title_search_currencies)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { text -> viewModel.onSearchQueryTextChange(text) }

                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { text -> viewModel.onSearchQueryTextChange(text) }

                return true
            }
        })
    }
}