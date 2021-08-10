package com.fitscorp.sl.apps.login

import android.content.SharedPreferences
import android.util.Log
import androidx.room.TypeConverter
import com.fitscorp.sl.apps.common.*
import com.fitscorp.sl.apps.home.model.UserAuthModel
import com.fitscorp.sl.apps.home.model.UserOtpResendModel
import com.fitscorp.sl.apps.home.model.UserRestModel
import com.fitscorp.sl.apps.login.LoginServiceMainResponse
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.rest.ApiService
import com.google.gson.Gson
import javax.inject.Inject

class FrogotPasswordVM @Inject constructor(val apiService: ApiService,
                                           val sharedPref: SharedPreferences
) {


    fun sendResetLink(p: UserRestModel) = apiService.resetUserPassword(p).map {
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

    fun resendOtp(p: UserOtpResendModel) = apiService.resendOtp(p).map {
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

