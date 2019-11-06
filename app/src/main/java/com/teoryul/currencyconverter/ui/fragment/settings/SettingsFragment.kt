package com.teoryul.currencyconverter.ui.fragment.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceFragmentCompat
import com.teoryul.currencyconverter.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(bundle: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onResume() {
        setActionBarTitle()
        showActionBar()

        super.onResume()
    }

    private fun setActionBarTitle() {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.title_settings_fragment)
    }

    private fun showActionBar() {
        (activity as AppCompatActivity).supportActionBar?.setShowHideAnimationEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}