package com.fitscorp.sl.apps.login

import android.content.SharedPreferences
import android.util.Log
import androidx.room.TypeConverter
import com.fitscorp.sl.apps.common.*
import com.fitscorp.sl.apps.home.model.FCMModel
import com.fitscorp.sl.apps.home.model.UserAuthModel
import com.fitscorp.sl.apps.login.LoginServiceMainResponse
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.rest.ApiService
import com.google.gson.Gson
import javax.inject.Inject

class LoginActivityVM @Inject constructor(val apiService: ApiService,
                                          val sharedPref: SharedPreferences
){

     var dataObj : LoginServiceMainResponse?=null
     var loginUserMainResponse: LoginUserMainResponse?=null




    fun getUserToken(p: UserAuthModel) = apiService.getUserToken(p).map {


        if(it.isSuccessful && it.body()!= null){
           val mainDataObj=it.body()

            if(mainDataObj!!.response.code==200){
               val token= mainDataObj.response.data.access_token
                val refreshToken = mainDataObj.response.data.refresh_token
                sharedPref.saveData("key_user_token",token)
                sharedPref.saveData("key_refresh_token",refreshToken)
                State(true, MSG_SUCCESS)

            }
            else if(mainDataObj.response.code==400){

                State(false, "The username or password provided in the request are invalid")

            }
            else {
                State(false, "fffffff")
            }


        } else {

            State(false, it.code().toString())
        }
    }.onErrorReturn {
        Log.d("Erro.........",it.message!!)
        State(false, MSG_FAILED_REQUEST)
    }
    fun getLoginUser() = apiService.getUserLogin(sharedPref.getAuthToken()).map {

        Log.d("This is it.........",it.isSuccessful.toString())
        if(it.isSuccessful && it.body()!= null){
            val mainDataObj=it.body()
            if(mainDataObj!!.response.code==200){
                loginUserMainResponse=it.body()
                val dataRes=it.body()
                sharedPref.saveData("user_data",detailsToJson(dataRes!!))
                val colorData= dataRes.response.data.org.organizationTheme
                sharedPref.saveData("color_data",colorData)
                State(true, MSG_SUCCESS)
            }else if(mainDataObj!!.response.code==401){

                Log.d("3345",mainDataObj!!.response.message)

                State(false, mainDataObj!!.response.message)
            }else if(mainDataObj!!.response.code==400){
                Log.d("3345",it.message())
                State(false, mainDataObj!!.response.message)
            }else{
                State(false, "Internal system error occurred]")
            }



        } else {
            Log.d("AAAAA",it.message())
            State(false, it.message())
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

    fun getData():String{
        return  sharedPref.getCashedDataByName("user_data")
    }



}