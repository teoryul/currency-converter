package com.teoryul.currencyconverter.ui.fragment.exchangerateprediction

import com.teoryul.currencyconverter.R
import com.teoryul.currencyconverter.databinding.FragmentExchangeRatePredictionBinding
import com.teoryul.currencyconverter.ui.fragment.BaseFragment

class ExchangeRatePredictionFragment : BaseFragment<ExchangeRatePredictionVM, FragmentExchangeRatePredictionBinding>() {

    override val viewModelClass = ExchangeRatePredictionVM::class

    override fun getLayoutResId(): Int = R.layout.fragment_exchange_rate_prediction

    override fun getTitle(): Int = R.string.title_exchange_rate_prediction_fragment
}