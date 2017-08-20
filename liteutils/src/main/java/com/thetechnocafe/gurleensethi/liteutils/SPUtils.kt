package com.thetechnocafe.gurleensethi.liteutils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by gurleensethi on 27/07/17.
 * A DSL for Shared Preference for easier and simple access to shared preferences
 *
 * Motivation: Access to shared preferences that is short concise yet easy to understand
 *             and with full functionality
 */

/*
* Name for the default shared preferences file
* */
private val DEFAULT_SHARED_PREFERENCES_FILE_NAME = "default_shared_preferences_file"

/**
 * DSL for shared preferences
 * Extension function on Context to add data to shared preferences
 *
 * @param fileName Name of the shared preferences file to be used
 * @param mode in which the file should be opened
 * @param function Lambda Extension function on SharedPreferences.Editor which is used to achieve
 * the DSL functionality
 * */
fun Context.sharedPreferences(fileName: String, mode: Int, function: (SharedPreferences.Editor.() -> Unit)) {
    val sharedPreferencesEditor = getSharedPreferences(fileName, mode).edit()
    sharedPreferencesEditor.function()
    sharedPreferencesEditor.apply()
    sharedPreferencesEditor.commit()
}

/**
 * DSL for shared preferences
 * Extension function on Context to add data to shared preferences
 * Default file is used when opening shared preferences
 * SharedPreferences file is opened in Private Mode
 *
 * @param function Lambda Extension function on SharedPreferences.Editor which is used to achieve
 * the DSL functionality
 * */
fun Context.defaultSharedPreferences(function: (SharedPreferences.Editor.() -> Unit)) {
    val sharedPreferencesEditor = getSharedPreferences(DEFAULT_SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE).edit()
    sharedPreferencesEditor.function()
    sharedPreferencesEditor.apply()
    sharedPreferencesEditor.commit()
}

/**
 * Generic function to get data from shared preference file
 * 'default' parameter is used to get the type for the value for return type
 *
 * @param fileName Name of the shared preferences file to be used
 * @param key for getting the corresponding value
 * @param default value to be returned if value for the provided key is not found
 * */
fun <T> Context.getFromSharedPreferences(fileName: String, key: String, default: T): T? where T : Any {
    val sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE)

    val value = when (default) {
        is Int -> sharedPreferences.getInt(key, default)
        is String -> sharedPreferences.getString(key, default)
        is Boolean -> sharedPreferences.getBoolean(key, default)
        is Float -> sharedPreferences.getFloat(key, default)
        is Long -> sharedPreferences.getLong(key, default)
        else -> null
    }

    return value as T
}

/**
 * Generic function to get data from shared preference file
 * 'default' parameter is used to get the type for the value for return type
 * Default file is used when opening shared preferences
 * SharedPreferences file is opened in Private Mode
 *
 * @param key for getting the corresponding value
 * @param default value to be returned if value for the provided key is not found
 * */
fun <T> Context.getFromDefaultSharedPreferences(key: String, default: T): T? where T : Any {
    val sharedPreferences = getSharedPreferences(DEFAULT_SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

    val value = when (default) {
        is Int -> sharedPreferences.getInt(key, default)
        is String -> sharedPreferences.getString(key, default)
        is Boolean -> sharedPreferences.getBoolean(key, default)
        is Float -> sharedPreferences.getFloat(key, default)
        is Long -> sharedPreferences.getLong(key, default)
        else -> null
    }

    return value as T
}