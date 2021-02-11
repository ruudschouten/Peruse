package com.ruurd.peruse.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.ruurd.peruse.R
import com.ruurd.peruse.data.DataUtil
import com.ruurd.peruse.data.backup.BackupManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            DataUtil.restoreResultCode -> {
                runBlocking {
                    BackupManager.restoreBackup(this@SettingsActivity, data)
                }
            }
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val backupButton = findPreference<Preference>(getString(R.string.backup_data_key))!!
            backupButton.setOnPreferenceClickListener {
                runBlocking {
                    BackupManager.createBackup(requireContext())
                    return@runBlocking true
                }
            }

            val restoreButton = findPreference<Preference>(getString(R.string.restore_data_key))!!
            restoreButton.setOnPreferenceClickListener {
                BackupManager.pickDatabaseFile(requireActivity())
                true
            }
        }
    }
}