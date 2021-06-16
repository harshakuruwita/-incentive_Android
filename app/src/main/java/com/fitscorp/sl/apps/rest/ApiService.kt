package com.fitscorp.sl.apps.rest



import com.fitscorp.sl.apps.Sample.PasserSam
import com.fitscorp.sl.apps.home.HomeMainResponse
import com.fitscorp.sl.apps.home.StoreImagesResponse
import com.fitscorp.sl.apps.home.model.FCMModel
import com.fitscorp.sl.apps.home.model.UserAuthModel
import com.fitscorp.sl.apps.home.model.UserEditModel
import com.fitscorp.sl.apps.home.model.UserRestModel
import com.fitscorp.sl.apps.login.LoginServiceMainResponse
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.menu.data.Contact
import com.fitscorp.sl.apps.menu.data.Leaderboard

import com.fitscorp.sl.apps.menu.data.TimelineMainRespone
import com.fitscorp.sl.apps.profile.EditProfileMainResponse
import com.fitscorp.sl.apps.register.Register
import com.fitscorp.sl.apps.register.RegisterMainResponse

import com.fitscorp.sl.apps.register.StoreMainResponse
import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    @GET("patients_of_doctor")
    fun getPatientOfDoctor(@Header("Authorization") authorization: String): Flowable<Response<Any>>



    @POST("auth/mobile/token")
    @Headers("Content-Type: application/json")
    fun getUserToken(@Body post: UserAuthModel): Flowable<Response<LoginServiceMainResponse>>



    @POST("auth/reset_password")
    @Headers("Content-Type: application/json")
    fun resetUserPassword(@Body post: UserRestModel): Flowable<Response<LoginServiceMainResponse>>

    @POST("merchant/message/mobile_data")
    @Headers("Content-Type: application/json")
    fun sendDeviceToken(@Body post: FCMModel): Flowable<Response<LoginServiceMainResponse>>

    @PATCH("merchant/message/mobile_data")
    @Headers("Content-Type: application/json")
    fun sendDeviceTokenAPI(@Header("Authorization") authorization: String,@Body post: FCMModel): Flowable<Response<LoginServiceMainResponse>>


    @GET("user/incentive")
    fun getAppFilters(@Header("Authorization") authorization: String): Flowable<Response<HomeMainResponse>>


    @POST("merchant/contact")
    @Headers("Content-Type: application/json")
    fun sendMessage(@Header("Authorization") authorization: String,
                    @Body post: Contact): Flowable<Response<RegisterMainResponse>>



    @POST("merchant/mobile/user/profile")
    @Headers("Content-Type: application/json")
    fun editProfile(@Header("Authorization") authorization: String,
                    @Body post: UserEditModel): Flowable<Response<EditProfileMainResponse>>



    @GET("user/dashboard/store/table?")
    fun getTimeineDataSM(@Header("Authorization") authorization: String,
                       @Query("incentivefield") incentivefield: Int,
                       @Query("selectPeriod") selectPeriod: String,
                       @Query("StartDate") StartDate: String,
                       @Query("EndDate") EndDate: String,
                       @Query("PeriodId") PeriodId: Int,
                       @Query("moduleType") moduleType: String,
                       @Query("tableDisplay") tableDisplay: Boolean

    ): Flowable<Response<TimelineMainRespone>>


    @GET("user/dashboard/salesrep/kpi?")
    fun getTimeineData(@Header("Authorization") authorization: String,
                           @Query("incentivefield") incentivefield: Int,
                           @Query("selectPeriod") selectPeriod: String,
                           @Query("StartDate") StartDate: String,
                           @Query("EndDate") EndDate: String,
                           @Query("PeriodId") PeriodId: Int,
                       @Query("moduleType") moduleType: String,
                       @Query("tableDisplay") tableDisplay: Boolean

                       ): Flowable<Response<TimelineMainRespone>>

    @GET("user/mobile/slaesrep/leaderboard?")
    fun getLeaderBoardData(@Header("Authorization") authorization: String,
                       @Query("incentivefield") incentivefield: Int,
                       @Query("selectPeriod") selectPeriod: String,
                       @Query("StartDate") StartDate: String,
                       @Query("EndDate") EndDate: String,
                       @Query("PeriodId") PeriodId: Int,
                       @Query("moduleType") moduleType: String,
                       @Query("tableDisplay") tableDisplay: Boolean

    ): Flowable<Response<Leaderboard>>

    @GET("user/mobile/store/leaderboard?")
    fun getLeaderBoardSM(@Header("Authorization") authorization: String,
                           @Query("incentivefield") incentivefield: Int,
                           @Query("selectPeriod") selectPeriod: String,
                           @Query("StartDate") StartDate: String,
                           @Query("EndDate") EndDate: String,
                           @Query("PeriodId") PeriodId: Int,
                           @Query("moduleType") moduleType: String,
                           @Query("tableDisplay") tableDisplay: Boolean

    ): Flowable<Response<Leaderboard>>


//    incentivefield=106&
//    //selectPeriod=MONTHLY&
//    //StartDate=2019-06-01T00:00:00.000Z&
//    //EndDate=2019-06-30T11:59:59.000Z
    //https://dev-incentive-api-vc.azurewebsites.net/api/v1/

    // user/kpi/tbl/result?
    // incentivefield=106&
    // selectPeriod=MONTHLY&
    // StartDate=2019-07-01T00:00:00.000Z&
    // EndDate=2019-07-31T11:59:59.000Z&
    // moduleType=rep&
    // tableDisplay=true
//    @POST("merchant/mobile/user")
//    @Headers("Content-Type: application/json")
//    fun registerUser(@Header("Authorization") authorization: String,
//                    @Body post: Contact): Flowable<Response<RegisterMainResponse>>


    @POST("merchant/mobile/user")
    @Headers("Content-Type: application/json")
    fun registerUser(@Body post: Register): Flowable<Response<RegisterMainResponse>>


    //    {
//        "lastName" : "yes",
//        "email" : "as3@mailinator.com",
//        "firstName" : "harsh",
//        "storeId" : 42,
//        "organizationId" : 75,
//        "mobileNo" : 3222233333,
//        "salesId" : 333,
//        "currentStatus" : "pending",
//        "userRole" : "STORE_MANAGER",
//        "userType" : "ORGANIZATION",
//        "salesIdList" : []
//    }

    @POST("merchant/mobile/stores")
    @FormUrlEncoded
    fun getStoreData(@Header("Authorization") authorization: String,
                     @Field("organizationId") organizationId: String): Flowable<Response<StoreMainResponse>>

    @POST("merchant/mobile/assets")
    @FormUrlEncoded
    fun getStoreImages(@Header("Authorization") authorization: String,
                     @Field("merchantId") organizationId: String): Flowable<Response<StoreImagesResponse>>





    @GET("user/mobile/profile")
    fun getUserLogin(@Header("Authorization") authorization: String): Flowable<Response<LoginUserMainResponse>>

}