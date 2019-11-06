package com.teoryul.currencyconverter.ui.activity.currency

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.teoryul.currencyconverter.R
import com.teoryul.currencyconverter.ui.activity.BaseActivity

class CurrencyActivity : BaseActivity() {

    var navHostFragment = NavHostFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpNavHostFragment()
        enableActionBarHomeButton()
    }

    private fun setUpNavHostFragment() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.content) as NavHostFragment
        navHostFragment.navController.setGraph(R.navigation.navigation_graph)
    }

    private fun enableActionBarHomeButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_currency
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                findNavController(R.id.content).popBackStack()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}