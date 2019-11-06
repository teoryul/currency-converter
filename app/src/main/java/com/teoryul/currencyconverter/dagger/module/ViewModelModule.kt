package com.teoryul.currencyconverter.dagger.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.teoryul.currencyconverter.dagger.ViewModelFactory
import com.teoryul.currencyconverter.dagger.ViewModelKey
import com.teoryul.currencyconverter.ui.fragment.exchangeratehistory.ExchangeRateHistoryFragment
import com.teoryul.currencyconverter.ui.fragment.exchangeratehistory.ExchangeRateHistoryVM
import com.teoryul.currencyconverter.ui.fragment.exchangerateprediction.ExchangeRatePredictionFragment
import com.teoryul.currencyconverter.ui.fragment.exchangerateprediction.ExchangeRatePredictionVM
import com.teoryul.currencyconverter.ui.fragment.home.HomeFragment
import com.teoryul.currencyconverter.ui.fragment.home.HomeVM
import com.teoryul.currencyconverter.ui.fragment.savedconfigurations.SavedConfigurationsFragment
import com.teoryul.currencyconverter.ui.fragment.savedconfigurations.SavedConfigurationsVM
import com.teoryul.currencyconverter.ui.fragment.searchcurrencies.SearchCurrenciesFragment
import com.teoryul.currencyconverter.ui.fragment.searchcurrencies.SearchCurrenciesVM
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeExchangeRateHistoryFragment(): ExchangeRateHistoryFragment

    @ContributesAndroidInjector
    abstract fun contributeExchangeRatePredictionFragment(): ExchangeRatePredictionFragment

    @ContributesAndroidInjector
    abstract fun contribteSavedConfigurationsFragment(): SavedConfigurationsFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchCurrenciesFragment(): SearchCurrenciesFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeVM::class)
    abstract fun bindHomeVM(homeVM: HomeVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExchangeRateHistoryVM::class)
    abstract fun bindExchangeRateHistoryVM(exchangeRateHistoryVM: ExchangeRateHistoryVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExchangeRatePredictionVM::class)
    abstract fun bindExchangeRatePredictionVM(exchangeRatePredictionVM: ExchangeRatePredictionVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SavedConfigurationsVM::class)
    abstract fun bindSavedConfigurationsVM(savedConfigurationsVM: SavedConfigurationsVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchCurrenciesVM::class)
    abstract fun bindSearchCurrenciesVM(searchCurrenciesVM: SearchCurrenciesVM): ViewModel
}