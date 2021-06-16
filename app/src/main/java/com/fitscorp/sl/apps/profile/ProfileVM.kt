package com.fitscorp.sl.apps.profile

import android.content.SharedPreferences
import android.util.Log
import androidx.room.TypeConverter
import com.fitscorp.sl.apps.common.*
import com.fitscorp.sl.apps.home.model.UserAuthModel
import com.fitscorp.sl.apps.login.LoginServiceMainResponse
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.rest.ApiService
import com.google.gson.Gson
import javax.inject.Inject

class ProfileVM @Inject constructor(val apiService: ApiService,
                                    val sharedPref: SharedPreferences
){

    var dataObj : LoginServiceMainResponse?=null
    var loginUserMainResponse: LoginUserMainResponse?=null


  fun callLogout(){

      sharedPref.saveData("user_data","")
  }

    fun getLoginData():String{
        return  sharedPref.getCashedDataByName("user_data")
    }

    @TypeConverter
    fun detailsToJson(value: LoginUserMainResponse): String {
        return Gson().toJson(value)
    }




}