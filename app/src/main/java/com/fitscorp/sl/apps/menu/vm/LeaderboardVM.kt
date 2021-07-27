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
import com.fitscorp.sl.apps.home.model.ExecutiveLeaderBordResponse
import org.json.JSONException
import org.json.JSONObject


class LeaderboardVM @Inject constructor(val apiService: ApiService,
                                        val sharedPref: SharedPreferences
) {

    var dataObjStoreManager :  List<LeaderboardData>?=null
    var dataObj :  List<LeaderboardData>?=null
    var executiveDataObj :  ExecutiveLeaderBordResponse?=null
    var leaderBoard_colour = "#00B2A9"
    var firstPlace_colour = "#00504c"



    fun getLeaderBoardSM(incentivefield:Int,selectPeriod:String,StartDate:String,EndDate:String,PeriodId:Int,moduleType:String,tableDisplay:Boolean) = apiService.getLeaderBoardSM(sharedPref.getAuthToken(),incentivefield,selectPeriod,StartDate,EndDate,PeriodId,moduleType,tableDisplay).map {
        if (it.isSuccessful && it.body() != null) {



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

    fun getLeaderBoardOnlyRegion(incentivefield:Int,selectPeriod:String,StartDate:String,EndDate:String,PeriodId:Int,moduleType:String,tableDisplay:Boolean,region:String) = apiService.getLeaderBoarOnlyRegion(sharedPref.getAuthToken(),incentivefield,selectPeriod,StartDate,EndDate,PeriodId,moduleType,tableDisplay,region,"all").map {
        if (it.isSuccessful && it.body() != null) {



            val dataOb = it.body()

            //   sharedPref.saveData("leaderboard_data_REP",detailsToJson(dataOb!!))

            executiveDataObj = dataOb!!
            if(dataOb!!.response.dataArr.count() > 0){


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
            Log.d("3344444","API faid");
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........", it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }


    fun getLeaderBoardByRegionByStore(incentivefield:Int,selectPeriod:String,StartDate:String,EndDate:String,PeriodId:Int,moduleType:String,tableDisplay:Boolean,region:String,storeId:String) = apiService.getLeaderBoarByRegionByStore(sharedPref.getAuthToken(),incentivefield,selectPeriod,StartDate,EndDate,PeriodId,moduleType,tableDisplay,region,storeId).map {
        if (it.isSuccessful && it.body() != null) {



            val dataOb = it.body()

            //   sharedPref.saveData("leaderboard_data_REP",detailsToJson(dataOb!!))

            executiveDataObj = dataOb!!
            if(dataOb!!.response.dataArr.count() > 0){

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
            Log.d("3344444","API faid");
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........", it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }

    fun getLeaderBoardByRegionByStoreAlluser(incentivefield:Int,selectPeriod:String,StartDate:String,EndDate:String,PeriodId:Int,moduleType:String,tableDisplay:Boolean,region:String,storeId:String) = apiService.getLeaderBoarByRegionByStoreAlluser(sharedPref.getAuthToken(),incentivefield,selectPeriod,StartDate,EndDate,PeriodId,moduleType,tableDisplay,region,storeId,"all").map {
        if (it.isSuccessful && it.body() != null) {



            val dataOb = it.body()

            //   sharedPref.saveData("leaderboard_data_REP",detailsToJson(dataOb!!))

            executiveDataObj = dataOb!!
            if(dataOb!!.response.dataArr.count() > 0){

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
            Log.d("3344444","API faid");
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........", it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }



    fun getLeaderBoardAllRegion(incentivefield:Int,selectPeriod:String,StartDate:String,EndDate:String,PeriodId:Int,moduleType:String,tableDisplay:Boolean) = apiService.getLeaderBoarAllRegion(sharedPref.getAuthToken(),incentivefield,selectPeriod,StartDate,EndDate,PeriodId,moduleType,tableDisplay,"All").map {
        if (it.isSuccessful && it.body() != null) {



            val dataOb = it.body()

            //   sharedPref.saveData("leaderboard_data_REP",detailsToJson(dataOb!!))

            executiveDataObj = dataOb!!
            if(dataOb!!.response.dataArr.count() > 0){

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
            Log.d("3344444","API faid");
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........", it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }


    fun getLeaderBoardByRegionAllUser(incentivefield:Int,selectPeriod:String,StartDate:String,EndDate:String,PeriodId:Int,moduleType:String,tableDisplay:Boolean,region:String,) = apiService.getLeaderBoarByRegionAllUser(sharedPref.getAuthToken(),incentivefield,selectPeriod,StartDate,EndDate,PeriodId,moduleType,tableDisplay,region,"all").map {
        if (it.isSuccessful && it.body() != null) {



            val dataOb = it.body()

            //   sharedPref.saveData("leaderboard_data_REP",detailsToJson(dataOb!!))

            executiveDataObj = dataOb!!
            if(dataOb!!.response.dataArr.count() > 0){

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
            Log.d("3344444","API faid");
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........", it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }

    fun getLeaderBoardAllUser(incentivefield:Int,selectPeriod:String,StartDate:String,EndDate:String,PeriodId:Int,moduleType:String,tableDisplay:Boolean) = apiService.getLeaderBoarAllUser(sharedPref.getAuthToken(),incentivefield,selectPeriod,StartDate,EndDate,PeriodId,moduleType,tableDisplay,"all").map {
        if (it.isSuccessful && it.body() != null) {



            val dataOb = it.body()

            //   sharedPref.saveData("leaderboard_data_REP",detailsToJson(dataOb!!))

            executiveDataObj = dataOb!!
            if(dataOb!!.response.dataArr.count() > 0){

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
            Log.d("3344444","API faid");
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........", it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }





    fun getLeaderBoardByUser(incentivefield:Int,selectPeriod:String,StartDate:String,EndDate:String,PeriodId:Int,moduleType:String,tableDisplay:Boolean,region:String,store:String,sales:String) = apiService.getLeaderBoarByUserData(sharedPref.getAuthToken(),incentivefield,selectPeriod,StartDate,EndDate,PeriodId,moduleType,tableDisplay,region,sales,store).map {
        if (it.isSuccessful && it.body() != null) {



            val dataOb = it.body()

            //   sharedPref.saveData("leaderboard_data_REP",detailsToJson(dataOb!!))

            executiveDataObj = dataOb!!
            if(dataOb!!.response.dataArr.count() > 0){

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


    fun getLeaderByRegionBoardByUser(incentivefield:Int,selectPeriod:String,StartDate:String,EndDate:String,PeriodId:Int,moduleType:String,tableDisplay:Boolean,region:String,sales:String) = apiService.getLeaderBoarByRegionByUser(sharedPref.getAuthToken(),incentivefield,selectPeriod,StartDate,EndDate,PeriodId,moduleType,tableDisplay,region,sales).map {
        if (it.isSuccessful && it.body() != null) {



            val dataOb = it.body()

            //   sharedPref.saveData("leaderboard_data_REP",detailsToJson(dataOb!!))

            executiveDataObj = dataOb!!
            if(dataOb!!.response.dataArr.count() > 0){

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