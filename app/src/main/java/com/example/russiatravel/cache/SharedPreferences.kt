package com.example.russiatravel.cache

import android.content.Context
import com.example.russiatravel.network.model.User
import com.example.russiatravel.presentation.ui.RussiaTravelApplication
import dagger.hilt.android.qualifiers.ApplicationContext

class SharedPreferences{

    companion object {
        private const val APP_PREFERENCES = "app_preferences"
        private const val USER_TOKEN = "user_token"
        private const val USER_NAME = "user_name"
        private const val USER_PHOTO = "user_photo"
        private const val GUEST_TOKEN = "guest_token"
        fun checkTokenExist (context: Context = RussiaTravelApplication.context): Boolean =
            context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).contains(USER_TOKEN)

        fun saveData(
            context: Context = RussiaTravelApplication.context,
            token: String,
            name: String,
            photo: String
        ) =
            context
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .putString(USER_TOKEN,token)
                .putString(USER_NAME,name)
                .putString(USER_PHOTO, photo)
                .apply()

        fun loadUserInfo(): User {
            val context: Context = RussiaTravelApplication.context
            val sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            val token = sharedPreferences.getString(USER_TOKEN, "")
            val name = sharedPreferences.getString(USER_NAME, "")
            val avatar = sharedPreferences.getString(USER_PHOTO, "")
            return User(token = token!!, name = name!!, avatar = avatar!!)
        }

        fun loadToken(context: Context = RussiaTravelApplication.context): String? =
            context
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                .getString(USER_TOKEN,"")

        fun removeData (context: Context = RussiaTravelApplication.context){
            context
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .remove(USER_TOKEN)
                .remove(USER_PHOTO)
                .remove(USER_NAME)
                .apply()
        }

        fun saveGuest(context: Context = RussiaTravelApplication.context,){
            context
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .putString(USER_TOKEN, GUEST_TOKEN)
                .apply()
        }

        fun isGuest(): Boolean = loadToken() == GUEST_TOKEN || loadToken()?.isEmpty() == true

    }
}