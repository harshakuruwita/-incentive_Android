package com.fitscorp.sl.apps.register

import android.content.SharedPreferences
import android.util.Log
import com.fitscorp.sl.apps.common.MSG_FAILED_REQUEST
import com.fitscorp.sl.apps.common.MSG_SUCCESS
import com.fitscorp.sl.apps.common.MSG_FAILED_REQUEST_SALESID
import com.fitscorp.sl.apps.common.State
import com.fitscorp.sl.apps.login.LoginServiceMainResponse
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.rest.ApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import retrofit2.adapter.rxjava2.Result.response
import org.json.JSONObject



class RegisterActivityVM @Inject constructor(val apiService: ApiService,
                    val sharedPref: SharedPreferences
){

    var registerdataObj : RegisterMainResponse?=null

    var storeDataObj : StoreMainResponse?=null


    fun getStoreData(organizationId:String) = apiService.getStoreData("",organizationId).map {
        if(it.isSuccessful && it.body()!= null){

            storeDataObj=it.body()

            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {

        Log.d("Erro.........",it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }



    fun registerUser(user:Register) = apiService.registerUser(user).map {

        if(it.isSuccessful && it.body()!= null){
            Log.d("ERR#221","Sucess")
          //  dataObj=it.body()

            State(true, MSG_SUCCESS)
        } else {


            val error = Gson().fromJson<RegisterMainResponse>(it.errorBody()?.string()!!)
            val errormsg = error.response.message
            Log.d("Error body",error.response.message)
            State(false,errormsg)
//            val mainDataObj=it.body()
//            Log.d("ERR#223",mainDataObj!!.response.code.toString())
//            if(mainDataObj!!.response.code==400){
//
//                State(false,"Abc")
//            }else{
//                State(false,"SSSS ")
//            }


        }
    }.onErrorReturn {
        Log.d("ERR#222","On ERR")
      //  Log.d("Erro.........",it.b)
        State(false, "loi")
    }





}

inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)