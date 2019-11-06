package com.teoryul.currencyconverter.ui.fragment.savedconfigurations

import com.teoryul.currencyconverter.R
import com.teoryul.currencyconverter.databinding.FragmentSavedConfigurationsBinding
import com.teoryul.currencyconverter.ui.fragment.BaseFragment

class SavedConfigurationsFragment : BaseFragment<SavedConfigurationsVM, FragmentSavedConfigurationsBinding>() {

    override val viewModelClass = SavedConfigurationsVM::class

    override fun getLayoutResId(): Int = R.layout.fragment_saved_configurations

    override fun getTitle(): Int = R.string.title_saved_configurations_fragment
}