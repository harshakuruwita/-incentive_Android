package com.fitscorp.sl.apps.menu.vm

import android.content.SharedPreferences
import android.util.Log
import com.fitscorp.sl.apps.common.MSG_FAILED_REQUEST
import com.fitscorp.sl.apps.common.MSG_SUCCESS
import com.fitscorp.sl.apps.common.State
import com.fitscorp.sl.apps.common.getAuthToken
import com.fitscorp.sl.apps.home.HomeMainResponse
import com.fitscorp.sl.apps.menu.data.Contact
import com.fitscorp.sl.apps.rest.ApiService
import javax.inject.Inject

class ContactVM @Inject constructor(val apiService: ApiService,
val sharedPref: SharedPreferences
) {


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


}