package com.example.russiatravel.cache

import android.content.Context
import com.example.russiatravel.presentation.ui.RussiaTravelApplication
import dagger.hilt.android.qualifiers.ApplicationContext

class SharedPreferences{

    companion object {
        private const val APP_PREFERENCES = "app_preferences"
        private const val USER_TOKEN = "user_token"
        fun checkTokenExist (context: Context = RussiaTravelApplication.context): Boolean =
            context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).contains(USER_TOKEN)

        fun saveToken(context: Context = RussiaTravelApplication.context, token: String) =
            context
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .putString(USER_TOKEN,token)
                .apply()

        fun loadToken(context: Context = RussiaTravelApplication.context): String? =
            context
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                .getString(USER_TOKEN,"")

        fun removeToken (context: Context = RussiaTravelApplication.context){
            context
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .remove(USER_TOKEN)
                .apply()
        }


    }
}