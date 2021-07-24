package com.fitscorp.sl.apps.home

import android.content.SharedPreferences
import android.util.Log
import androidx.room.TypeConverter
import com.fitscorp.sl.apps.common.*
import com.fitscorp.sl.apps.home.model.FCMModel
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.rest.ApiService
import com.google.gson.Gson
import javax.inject.Inject

class SplashVM @Inject constructor(val apiService: ApiService,
                                           val sharedPref: SharedPreferences
) {

    var storeImageDataObj : StoreImagesResponse?=null


    fun getStoreImages(organizationId:String) = apiService.getStoreImages("",organizationId).map {
        if(it.isSuccessful && it.body()!= null){

            storeImageDataObj=it.body()
            sharedPref.saveData("storeImage",storeImageDataObj!!.response.data.AgentTheme.background[0].path.toString())
            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........",it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }

    fun sendDeviceToken(p: FCMModel) = apiService.sendDeviceToken(p).map {
        if (it.isSuccessful && it.body() != null) {
            val mainDataObj = it.body()

            if (mainDataObj!!.response.code == 200) {

            }



            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........", it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }

    @TypeConverter
    fun detailsToJson(value: LoginUserMainResponse): String {
        return Gson().toJson(value)
    }

    fun getData(): String {
        return sharedPref.getCashedDataByName("user_data")
    }
}

