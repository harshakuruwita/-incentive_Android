package com.fitscorp.sl.apps.home

import android.content.SharedPreferences
import android.util.Log
import androidx.room.TypeConverter
import com.fitscorp.sl.apps.common.*
import com.fitscorp.sl.apps.home.model.FCMModel
import com.fitscorp.sl.apps.login.LoginServiceMainResponse
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.menu.data.Contact
import com.fitscorp.sl.apps.rest.ApiService
import com.google.gson.Gson
import javax.inject.Inject

class HomeVM @Inject constructor(val apiService: ApiService,
                                 val sharedPref: SharedPreferences
){

    var dataObj : HomeMainResponse?=null

    fun callLogout(){

        sharedPref.saveData("user_data","")
    }

    fun getAppFilters() = apiService.getAppFilters(sharedPref.getAuthToken()).map {
        if(it.isSuccessful && it.body()!= null){

            dataObj=it.body()

            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........",it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }

    fun getLoginData():String{
        return  sharedPref.getCashedDataByName("user_data")
    }

    @TypeConverter
    fun detailsToJson(value: LoginUserMainResponse): String {
        return Gson().toJson(value)
    }

    fun getData():String{
        return  sharedPref.getCashedDataByName("color_data")
    }


    fun sendMessage(data: Contact) = apiService.sendMessage(sharedPref.getAuthToken(),data).map {
        if (it.isSuccessful && it.body() != null) {

            val dataObj = it.body()

            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........", it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }

    fun sendDeviceToken(p: FCMModel) = apiService.sendDeviceTokenAPI(sharedPref.getAuthToken(),p).map {
        if (it.isSuccessful && it.body() != null) {
            val mainDataObj = it.body()

            if (mainDataObj!!.response.code == 200) {
                Log.d("AXXCC", "ok")
                State(true, MSG_SUCCESS)
            }



            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........", it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }

}