package com.fitscorp.sl.apps.profile

import android.content.SharedPreferences
import android.util.Log
import androidx.room.TypeConverter
import com.fitscorp.sl.apps.common.*
import com.fitscorp.sl.apps.home.model.UserAuthModel
import com.fitscorp.sl.apps.home.model.UserEditModel
import com.fitscorp.sl.apps.login.LoginServiceMainResponse
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.rest.ApiService
import com.google.gson.Gson
import javax.inject.Inject

class EditProfileVM@Inject constructor(val apiService: ApiService,
                                       val sharedPref: SharedPreferences
) {

    var dataObj : LoginServiceMainResponse?=null

    fun editProfile(p: UserEditModel) = apiService.editProfile(sharedPref.getAuthToken(),p).map {
       // sharedPref.saveData("user_data",p.toString())

        if(it.isSuccessful && it.body()!= null){
            val mainDataObj=it.body()

            if(mainDataObj!!.response.code==200){
                Log.d("this.........",it.toString())
                State(true, it.code().toString())

            }

            else {
                Log.d("this.........",it.toString())
                State(false, it.code().toString())
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
                  //  loginUserMainResponse=it.body()
                    val dataRes=it.body()
                    sharedPref.saveData("user_data",detailsToJson(dataRes!!))
                    val colorData= dataRes.response.data.org.organizationTheme
                    sharedPref.saveData("color_data",colorData)
                    State(true, MSG_SUCCESS)
                }else if(mainDataObj!!.response.code==401){

                    State(false, "Your account has been deactivated, please contact the system admin!")
                }else{
                    State(false, "Internal system error occurred]")
                }



            }

        }


    @TypeConverter
    fun detailsToJson(value: LoginUserMainResponse): String {
        return Gson().toJson(value)
    }


}