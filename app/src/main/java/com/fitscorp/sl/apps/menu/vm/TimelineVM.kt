package com.fitscorp.sl.apps.menu.vm

import android.content.SharedPreferences
import android.util.Log
import androidx.room.TypeConverter
import com.fitscorp.sl.apps.common.*
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.menu.data.ExecutiveTimelineMainRespone
import com.fitscorp.sl.apps.menu.data.Leaderboard

import com.fitscorp.sl.apps.menu.data.TimelineMainRespone
import com.fitscorp.sl.apps.rest.ApiService
import com.google.gson.Gson
import javax.inject.Inject

class TimelineVM @Inject constructor(val apiService: ApiService,
val sharedPref: SharedPreferences
) {

    var dataObj : TimelineMainRespone?=null
    var executiveDataObj : ExecutiveTimelineMainRespone?=null
    fun getTimeLineStoreSM(incentivefield:Int,selectPeriod:String,StartDate:String,EndDate:String,PeriodId:Int,moduleType:String,tableDisplay:Boolean) = apiService.getTimeineDataSM(sharedPref.getAuthToken(),incentivefield,selectPeriod,StartDate,EndDate,PeriodId,moduleType,tableDisplay).map {
        if (it.isSuccessful && it.body() != null) {

            val dataOb = it.body()

            sharedPref.saveData("timeline_data_SM",detailsToJson(dataOb!!))

            dataObj=dataOb

            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........", it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }


    fun getTimeLineStore(incentivefield:Int,selectPeriod:String,StartDate:String,EndDate:String,PeriodId:Int,moduleType:String,tableDisplay:Boolean) = apiService.getTimeineData(sharedPref.getAuthToken(),incentivefield,selectPeriod,StartDate,EndDate,PeriodId,moduleType,tableDisplay).map {
        if (it.isSuccessful && it.body() != null) {

            val dataOb = it.body()

            sharedPref.saveData("timeline_data_REP",detailsToJson(dataOb!!))

            dataObj=dataOb

            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........", it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }



    fun getTimeLineExecitiveAllRegion(incentivefield:Int,selectPeriod:String,StartDate:String,EndDate:String,PeriodId:Int,moduleType:String,tableDisplay:Boolean,regionId:String,storeId:String) = apiService.getTimeineDataExecutiveAllRegion(sharedPref.getAuthToken(),incentivefield,selectPeriod,StartDate,EndDate,PeriodId,moduleType,tableDisplay,regionId,storeId).map {
        if (it.isSuccessful && it.body() != null) {

            val dataOb = it.body()

            sharedPref.saveData("timeline_data_Executive",detailsToJsonExecutive(dataOb!!))

            executiveDataObj=dataOb

            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........", it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }


    fun getTimeLineExecitiveSalesOnly(incentivefield:Int,selectPeriod:String,StartDate:String,EndDate:String,PeriodId:Int,moduleType:String,tableDisplay:Boolean) = apiService.getTimeineDataExecutiveAllSales(sharedPref.getAuthToken(),incentivefield,selectPeriod,StartDate,EndDate,PeriodId,moduleType,tableDisplay,"all").map {
        if (it.isSuccessful && it.body() != null) {

            val dataOb = it.body()

            sharedPref.saveData("timeline_data_Executive",detailsToJsonExecutive(dataOb!!))

            executiveDataObj=dataOb

            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........", it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }

    fun getTimeLineExecitiveByUser(incentivefield:Int,selectPeriod:String,StartDate:String,EndDate:String,PeriodId:Int,moduleType:String,tableDisplay:Boolean,regionId:String,storeId:String,userId:String) = apiService.getTimeineDataExecutiveByUser(sharedPref.getAuthToken(),incentivefield,selectPeriod,StartDate,EndDate,PeriodId,moduleType,tableDisplay,regionId,storeId,userId).map {
        if (it.isSuccessful && it.body() != null) {

            val dataOb = it.body()

            sharedPref.saveData("timeline_data_Executive",detailsToJsonExecutive(dataOb!!))

            executiveDataObj=dataOb

            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........", it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }

    fun getTimeLineExecitiveRegionByUser(incentivefield:Int,selectPeriod:String,StartDate:String,EndDate:String,PeriodId:Int,moduleType:String,tableDisplay:Boolean,regionId:String,userId:String) = apiService.getTimeineDataExecutiveByUser(sharedPref.getAuthToken(),incentivefield,selectPeriod,StartDate,EndDate,PeriodId,moduleType,tableDisplay,regionId,"all",userId).map {
        if (it.isSuccessful && it.body() != null) {

            val dataOb = it.body()

            sharedPref.saveData("timeline_data_Executive",detailsToJsonExecutive(dataOb!!))

            executiveDataObj=dataOb

            State(true, MSG_SUCCESS)
        } else {
            State(false, MSG_FAILED_REQUEST)
        }
    }.onErrorReturn {
        Log.d("Erro.........", it.stackTrace.toString())
        State(false, MSG_FAILED_REQUEST)
    }

    @TypeConverter
    fun detailsToJson(value: TimelineMainRespone): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun detailsToJsonExecutive(value: ExecutiveTimelineMainRespone): String {
        return Gson().toJson(value)
    }

    fun getData_REP():String{
        return  sharedPref.getCashedDataByName("timeline_data_REP")
    }

    fun getData_SM():String{
        return  sharedPref.getCashedDataByName("timeline_data_SM")
    }
}
