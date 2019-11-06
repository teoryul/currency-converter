package com.teoryul.currencyconverter.ui.fragment.exchangeratehistory

import com.teoryul.currencyconverter.R
import com.teoryul.currencyconverter.databinding.FragmentExchangeRateHistoryBinding
import com.teoryul.currencyconverter.ui.fragment.BaseFragment

class ExchangeRateHistoryFragment : BaseFragment<ExchangeRateHistoryVM, FragmentExchangeRateHistoryBinding>() {

    override val viewModelClass = ExchangeRateHistoryVM::class

    override fun getLayoutResId(): Int = R.layout.fragment_exchange_rate_history

    override fun getTitle(): Int = R.string.title_exchange_rate_history_fragment
}