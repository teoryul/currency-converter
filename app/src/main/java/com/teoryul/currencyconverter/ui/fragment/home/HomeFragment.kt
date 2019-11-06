package com.teoryul.currencyconverter.ui.fragment.home

import com.teoryul.currencyconverter.R
import com.teoryul.currencyconverter.databinding.FragmentHomeBinding
import com.teoryul.currencyconverter.ui.fragment.BaseFragment

class HomeFragment : BaseFragment<HomeVM, FragmentHomeBinding>() {

    override val viewModelClass = HomeVM::class

    override fun getLayoutResId(): Int = R.layout.fragment_home

    override fun shouldHideActionBar(): Boolean = true
}