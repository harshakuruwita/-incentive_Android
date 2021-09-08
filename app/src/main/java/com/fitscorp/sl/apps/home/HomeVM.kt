package com.fitscorp.sl.apps.home

import android.content.SharedPreferences
import android.util.Log
import androidx.room.TypeConverter
import com.fitscorp.sl.apps.common.*
import com.fitscorp.sl.apps.home.model.*
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
    var loginUserMainResponse: LoginUserMainResponse?=null
    var executiveFilterResponse: ExecutiveFilterResponse?=null
    var defaultIncentiveModel: DefaultIncentiveModel?=null


    fun callLogout(){

        sharedPref.saveData("user_data","")
    }

    fun getAppFilters() = apiService.getAppFilters(sharedPref.getAuthToken()).map {
        Log.d("45446",sharedPref.getAuthToken());
        if(it.isSuccessful && it.body()!= null){

            dataObj = it.body()
            Log.d("45444","getAppFilters");
            if(dataObj!!.response.code==200){
                sharedPref.saveData("filter_dropdown_data",detailsToJson(dataObj!!))
            }

            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........",it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }


    fun getDefaultIncentive() = apiService.getDefaultIncentive(sharedPref.getAuthToken()).map {
        Log.d("45446",sharedPref.getAuthToken());
        if(it.isSuccessful && it.body()!= null){

            defaultIncentiveModel = it.body()

            if(defaultIncentiveModel!!.response.code==200){
                var incentivedata = defaultIncentiveModel!!.response.data.Detail;

                val splitArray: List<String> = incentivedata.split(":")
                val secondSplitArray: List<String> = splitArray[1].split(":")
                val thirdSplitArray: List<String> = secondSplitArray[0].split(",")
                Log.d("3455",thirdSplitArray[0]);
            }

            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........",it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }




    fun getExecutiveFilter() = apiService.getExecutiveFilters(sharedPref.getAuthToken()).map {

        if(it.isSuccessful && it.body()!= null){

            executiveFilterResponse = it.body()

            if(executiveFilterResponse!!.response.code==200){

                sharedPref.saveData("executive_region_list",detailsToJsonExecutive(executiveFilterResponse!!))
            }

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

    fun getFilterDropdown():String{
        return  sharedPref.getCashedDataByName("filter_dropdown_data")
    }

    fun getExecutiveRegionData():String{
        return  sharedPref.getCashedDataByName("executive_region_list")
    }

    @TypeConverter
    fun detailsToJson(value: HomeMainResponse): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun detailsToJsonExecutive(value: ExecutiveFilterResponse): String {
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
    
    fun checkToken() = apiService.getUserLogin(sharedPref.getAuthToken()).map {

        if(it.isSuccessful && it.body()!= null){
            val mainDataObj=it.body()
            if(mainDataObj!!.response.code==200){
                loginUserMainResponse=it.body()
                State(true, MSG_SUCCESS)
            }else if(mainDataObj!!.response.code==401){
                State(false, mainDataObj!!.response.message)
            }else if(mainDataObj!!.response.code==400){

                State(false, mainDataObj!!.response.message)
            }else{
                State(false, "Internal system error occurred]")
            }

        } else {

            State(false, it.message())
        }
    }.onErrorReturn {

        State(false, MSG_FAILED_REQUEST)
    }


    fun reGenerateToken(p: TokenReGenerateModel) = apiService.reGenerateToken(p).map {

        if(it.isSuccessful && it.body()!= null){
            val mainDataObj=it.body()
            if(mainDataObj!!.response.code==200){

                val token= mainDataObj.response.data.access_token
                val refreshToken= mainDataObj.response.data.refresh_token
                sharedPref.saveData("key_user_token",token)
                sharedPref.saveData("key_refresh_token",refreshToken)
                Log.d("token",token)
                Log.d("refreshToken",refreshToken)
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

}