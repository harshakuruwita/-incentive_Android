package com.fitscorp.sl.apps.menu.vm

import android.content.SharedPreferences
import android.util.Log
import androidx.room.TypeConverter
import com.fitscorp.sl.apps.common.*
import com.fitscorp.sl.apps.home.HomeMainResponse
import com.fitscorp.sl.apps.menu.data.*
import com.fitscorp.sl.apps.rest.ApiService
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import javax.inject.Inject
import android.R.string
import org.json.JSONException
import org.json.JSONObject


class LeaderboardVM @Inject constructor(val apiService: ApiService,
                                        val sharedPref: SharedPreferences
) {

    var dataObjStoreManager :  List<LeaderboardData>?=null
    var dataObj :  List<LeaderboardData>?=null
    var leaderBoard_colour = "#f5c242"
    var firstPlace_colour = "#f5c242"



    fun getLeaderBoardSM(incentivefield:Int,selectPeriod:String,StartDate:String,EndDate:String,PeriodId:Int,moduleType:String,tableDisplay:Boolean) = apiService.getLeaderBoardSM(sharedPref.getAuthToken(),incentivefield,selectPeriod,StartDate,EndDate,PeriodId,moduleType,tableDisplay).map {
        if (it.isSuccessful && it.body() != null) {

            /*fun getTimeLineStore() = apiService.getTimeineData(sharedPref.getAuthToken(),120,"MONTHLY","2019-08-01T00:00:00.000Z","2019-08-31T11:59:59.000Z",28,"rep","false").map {
                if (it.isSuccessful && it.body() != null) {*/

            val dataOb = it.body()
            sharedPref.saveData("leaderboard_data_SM",detailsToJson(dataOb!!))
            dataObjStoreManager=dataOb!!.response.dataArr

            if(dataOb!!.response.incentive.count() > 0){
                var cSetting=dataOb!!.response.incentive.get(0).ColorSettings


                var jsonObj: JSONObject? = null
                try {
                    jsonObj = JSONObject(cSetting)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                 leaderBoard_colour = jsonObj!!.optString("leaderBoard").toString()
                 firstPlace_colour = jsonObj!!.optString("firstPlace").toString()

            }

            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........", it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }


    fun getLeaderBoard(incentivefield:Int,selectPeriod:String,StartDate:String,EndDate:String,PeriodId:Int,moduleType:String,tableDisplay:Boolean) = apiService.getLeaderBoardData(sharedPref.getAuthToken(),incentivefield,selectPeriod,StartDate,EndDate,PeriodId,moduleType,tableDisplay).map {
        if (it.isSuccessful && it.body() != null) {

            /*fun getTimeLineStore() = apiService.getTimeineData(sharedPref.getAuthToken(),120,"MONTHLY","2019-08-01T00:00:00.000Z","2019-08-31T11:59:59.000Z",28,"rep","false").map {
                if (it.isSuccessful && it.body() != null) {*/

            val dataOb = it.body()
            sharedPref.saveData("leaderboard_data_REP",detailsToJson(dataOb!!))

            dataObj=dataOb!!.response.dataArr
            if(dataOb!!.response.incentive.count() > 0){
                var cSetting=dataOb!!.response.incentive.get(0).ColorSettings


                var jsonObj: JSONObject? = null
                try {
                    jsonObj = JSONObject(cSetting)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                 leaderBoard_colour = jsonObj!!.optString("leaderBoard").toString()
                 firstPlace_colour = jsonObj!!.optString("firstPlace").toString()

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
    fun detailsToJson(value: Leaderboard): String {
        return Gson().toJson(value)
    }

    fun getData_REP():String{
        return  sharedPref.getCashedDataByName("leaderboard_data_REP")
    }

    fun getData_SM():String{
        return  sharedPref.getCashedDataByName("leaderboard_data_SM")
    }

}