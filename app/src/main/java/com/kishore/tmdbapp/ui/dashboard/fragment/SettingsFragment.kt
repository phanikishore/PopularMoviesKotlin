package com.kishore.tmdbapp.ui.dashboard.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.kishore.tmdbapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}