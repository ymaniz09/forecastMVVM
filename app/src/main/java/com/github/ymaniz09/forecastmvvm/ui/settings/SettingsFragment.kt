package com.github.ymaniz09.forecastmvvm.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.github.ymaniz09.forecastmvvm.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}
