package com.fitscorp.sl.apps.common


import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


enum class SharedPreferenceKeys {
    AUTH_TOKEN,
    CURRENT_USER,
    CURRENT_APPOINTMENT,
    QB_USER,
    QB_USER_ID,
    CURRENT_SCREEN,
    APP_ONLOAD,
    POSTS,
    TITLES_QUICKLINKS_GOALS,
    PUSH_TOKEN,
    IS_TIMELINE_CASHED,
    IS_WORKOUT_CASHED,
    IS_HEALTH_CASHED,
    IS_RELAX_CASHED,
    IS_DIET_CASHED
}

enum class Screens {
    ENTER_PHONE,
    VERIFY_PHONE,
    DOC_REGISTRATION,
    ENTER_SLMC,
    SLMC_VERIFICATION_PENDING,
    REGISTRATION_OK_VERIFY_REQUIRED,
    EVERYTHING_IS_OK
}


//view group
fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View = LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)


fun SharedPreferences.save(key: SharedPreferenceKeys, value: Float) {
    edit().putFloat(key.name, value).apply()
}
fun SharedPreferences.saveStatus(key: String, value: Boolean) {
    edit().putBoolean(key, value).apply()
}
fun SharedPreferences.getCashedStatusByName(key:String) : Boolean = getBoolean(key, false)
fun SharedPreferences.getCashedDataByName(key:String) : String = getString(key, "").toString()

fun SharedPreferences.getAuthToken(): String = getString("key_user_token", "").toString()

fun SharedPreferences.getStoreImage(): String = getString("storeImage", "").toString()

fun SharedPreferences.saveData(key: String, value: String) {
    edit().putString(key, value).apply()
}
fun SharedPreferences.save(key: SharedPreferenceKeys, value: String) {
    edit().putString(key.name, value).apply()
}

fun SharedPreferences.save(key: SharedPreferenceKeys, value: Int) {
    edit().putInt(key.name, value).apply()
}



fun SharedPreferences.savePushToken(key: SharedPreferenceKeys, value: String) {
    edit().putString(key.name, value).apply()
}

fun SharedPreferences.saveNextScreen(screens: Screens) {
    save(SharedPreferenceKeys.CURRENT_SCREEN, screens.ordinal)
}

fun SharedPreferences.getNextScreen() = Screens.values()[getInt(SharedPreferenceKeys.CURRENT_SCREEN.name, 0)]

