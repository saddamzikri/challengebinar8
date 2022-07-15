package com.saddam.challengebinar8.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.preferences.clear
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences

class UserLoginManager(context: Context) {
    private val dataStore: DataStore<androidx.datastore.preferences.Preferences> = context.createDataStore("login-prefs")

    companion object {
        val EMAIL = preferencesKey<String>("EMAIL")
        val NAME = preferencesKey<String>("NAME")
        val PASSWORD = preferencesKey<String>("PASSWORD")
        val TANGGAL_LAHIR = preferencesKey<String>("TANGGAL_LAHIR")
        val USERNAME = preferencesKey<String>("USERNAME")
        val BOOLEAN = preferencesKey<Boolean>("BOOLEAN")
    }

    suspend fun saveDataLogin(
        email: String,
        name: String,
        password: String,
        tanggalLahir: String,
        username: String
    ) {
        dataStore.edit {
            it[NAME] = name
            it[PASSWORD] = password
            it[EMAIL] = email
            it[TANGGAL_LAHIR] = tanggalLahir
            it[USERNAME] = username
        }
    }

    suspend fun setBoolean(boolean: Boolean) {
        dataStore.edit {
            it[BOOLEAN] = boolean
        }
    }

    suspend fun clearDataLogin() {
        dataStore.edit {
            it.clear()
        }
    }

    val name: Flow<String> = dataStore.data.map {
        it[NAME] ?: ""
    }

    val username: Flow<String> = dataStore.data.map {
        it[USERNAME] ?: ""
    }

    val boolean: Flow<Boolean> = dataStore.data.map {
        it[BOOLEAN] ?: false
    }
}