package com.fitscorp.sl.apps.rest



import com.fitscorp.sl.apps.Sample.PasserSam
import com.fitscorp.sl.apps.home.HomeMainResponse
import com.fitscorp.sl.apps.home.StoreImagesResponse
import com.fitscorp.sl.apps.home.model.*
import com.fitscorp.sl.apps.login.LoginServiceMainResponse
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.menu.data.Contact
import com.fitscorp.sl.apps.menu.data.ExecutiveTimelineMainRespone
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



    @GET("org/default/incentive")
    fun getDefaultIncentive(@Header("Authorization") authorization: String): Flowable<Response<DefaultIncentiveModel>>

    @GET("merchant/regions")
    fun getExecutiveFilters(@Header("Authorization") authorization: String): Flowable<Response<ExecutiveFilterResponse>>


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

    @GET("user/dashboard/mobile/store/dashboard?")
    fun getTimeineDataExecutiveAllRegion(@Header("Authorization") authorization: String,
                                         @Query("incentivefield") incentivefield: Int,
                                         @Query("selectPeriod") selectPeriod: String,
                                         @Query("StartDate") StartDate: String,
                                         @Query("EndDate") EndDate: String,
                                         @Query("PeriodId") PeriodId: Int,
                                         @Query("moduleType") moduleType: String,
                                         @Query("tableDisplay") tableDisplay: Boolean,
                                         @Query("RegionId") RegionId: String,
                                         @Query("storeId") storeId: String,


                                         ): Flowable<Response<ExecutiveTimelineMainRespone>>


    @GET("user/dashboard/mobile/store/dashboard?")
    fun getTimeineDataExecutiveAllSales(@Header("Authorization") authorization: String,
                                        @Query("incentivefield") incentivefield: Int,
                                        @Query("selectPeriod") selectPeriod: String,
                                        @Query("StartDate") StartDate: String,
                                        @Query("EndDate") EndDate: String,
                                        @Query("PeriodId") PeriodId: Int,
                                        @Query("moduleType") moduleType: String,
                                        @Query("tableDisplay") tableDisplay: Boolean,
                                        @Query("userId") userId: String,


                                        ): Flowable<Response<ExecutiveTimelineMainRespone>>

    @GET("user/dashboard/mobile/user/dashboard?")
    fun getTimeineDataExecutiveByUser(@Header("Authorization") authorization: String,
                                      @Query("incentivefield") incentivefield: Int,
                                      @Query("selectPeriod") selectPeriod: String,
                                      @Query("StartDate") StartDate: String,
                                      @Query("EndDate") EndDate: String,
                                      @Query("PeriodId") PeriodId: Int,
                                      @Query("moduleType") moduleType: String,
                                      @Query("tableDisplay") tableDisplay: Boolean,
                                      @Query("RegionId") RegionId: String,
                                      @Query("storeId") storeId: String,
                                      @Query("userId") userId: String,

                                      ): Flowable<Response<ExecutiveTimelineMainRespone>>

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

    @GET("user/dashboard/mobile/leaderboard?")
    fun getLeaderBoarByRegiondData(@Header("Authorization") authorization: String,
                                   @Query("incentivefield") incentivefield: Int,
                                   @Query("selectPeriod") selectPeriod: String,
                                   @Query("StartDate") StartDate: String,
                                   @Query("EndDate") EndDate: String,
                                   @Query("PeriodId") PeriodId: Int,
                                   @Query("moduleType") moduleType: String,
                                   @Query("tableDisplay") tableDisplay: Boolean,
                                   @Query("RegionId") RegionId: String,
                                   @Query("storeId") storeId: String,

                                   ): Flowable<Response<ExecutiveLeaderBordResponse>>


    @GET("user/dashboard/mobile/leaderboard?")
    fun getLeaderBoarOnlyRegion(@Header("Authorization") authorization: String,
                                @Query("incentivefield") incentivefield: Int,
                                @Query("selectPeriod") selectPeriod: String,
                                @Query("StartDate") StartDate: String,
                                @Query("EndDate") EndDate: String,
                                @Query("PeriodId") PeriodId: Int,
                                @Query("moduleType") moduleType: String,
                                @Query("tableDisplay") tableDisplay: Boolean,
                                @Query("RegionId") regionId: String,
                                @Query("storeId") storeId: String,

                                ): Flowable<Response<ExecutiveLeaderBordResponse>>

    @GET("user/dashboard/mobile/leaderboard?")
    fun getLeaderBoarAllRegion(@Header("Authorization") authorization: String,
                               @Query("incentivefield") incentivefield: Int,
                               @Query("selectPeriod") selectPeriod: String,
                               @Query("StartDate") StartDate: String,
                               @Query("EndDate") EndDate: String,
                               @Query("PeriodId") PeriodId: Int,
                               @Query("moduleType") moduleType: String,
                               @Query("tableDisplay") tableDisplay: Boolean,
                               @Query("RegionId") regionId: String,

                               ): Flowable<Response<ExecutiveLeaderBordResponse>>



    @GET("user/dashboard/mobile/leaderboard?")
    fun getLeaderBoarByRegionByStore(@Header("Authorization") authorization: String,
                                     @Query("incentivefield") incentivefield: Int,
                                     @Query("selectPeriod") selectPeriod: String,
                                     @Query("StartDate") StartDate: String,
                                     @Query("EndDate") EndDate: String,
                                     @Query("PeriodId") PeriodId: Int,
                                     @Query("moduleType") moduleType: String,
                                     @Query("tableDisplay") tableDisplay: Boolean,
                                     @Query("RegionId") regionId: String,
                                     @Query("storeId") storeId: String,

                                     ): Flowable<Response<ExecutiveLeaderBordResponse>>

    @GET("user/dashboard/mobile/leaderboard?")
    fun getLeaderBoarByRegionByStoreAlluser(@Header("Authorization") authorization: String,
                                            @Query("incentivefield") incentivefield: Int,
                                            @Query("selectPeriod") selectPeriod: String,
                                            @Query("StartDate") StartDate: String,
                                            @Query("EndDate") EndDate: String,
                                            @Query("PeriodId") PeriodId: Int,
                                            @Query("moduleType") moduleType: String,
                                            @Query("tableDisplay") tableDisplay: Boolean,
                                            @Query("RegionId") regionId: String,
                                            @Query("storeId") storeId: String,
                                            @Query("userId") userId: String,

                                            ): Flowable<Response<ExecutiveLeaderBordResponse>>

    @GET("user/dashboard/mobile/leaderboard?")
    fun getLeaderBoarByRegionAllUser(@Header("Authorization") authorization: String,
                                     @Query("incentivefield") incentivefield: Int,
                                     @Query("selectPeriod") selectPeriod: String,
                                     @Query("StartDate") StartDate: String,
                                     @Query("EndDate") EndDate: String,
                                     @Query("PeriodId") PeriodId: Int,
                                     @Query("moduleType") moduleType: String,
                                     @Query("tableDisplay") tableDisplay: Boolean,
                                     @Query("RegionId") regionId: String,
                                     @Query("userId") userId: String,

                                     ): Flowable<Response<ExecutiveLeaderBordResponse>>

    @GET("user/dashboard/mobile/leaderboard?")
    fun getLeaderBoarAllUser(@Header("Authorization") authorization: String,
                             @Query("incentivefield") incentivefield: Int,
                             @Query("selectPeriod") selectPeriod: String,
                             @Query("StartDate") StartDate: String,
                             @Query("EndDate") EndDate: String,
                             @Query("PeriodId") PeriodId: Int,
                             @Query("moduleType") moduleType: String,
                             @Query("tableDisplay") tableDisplay: Boolean,
                             @Query("userId") salesld: String,

                             ): Flowable<Response<ExecutiveLeaderBordResponse>>

    @GET("user/dashboard/mobile/leaderboard?")
    fun getLeaderBoarByUserData(@Header("Authorization") authorization: String,
                                @Query("incentivefield") incentivefield: Int,
                                @Query("selectPeriod") selectPeriod: String,
                                @Query("StartDate") StartDate: String,
                                @Query("EndDate") EndDate: String,
                                @Query("PeriodId") PeriodId: Int,
                                @Query("moduleType") moduleType: String,
                                @Query("tableDisplay") tableDisplay: Boolean,
                                @Query("RegionId") RegionId: String,
                                @Query("storeId") storeId: String,
                                @Query("userId") salesld: String,

                                ): Flowable<Response<ExecutiveLeaderBordResponse>>

    @GET("user/dashboard/mobile/leaderboard?")
    fun getLeaderBoarByRegionByUser(@Header("Authorization") authorization: String,
                                    @Query("incentivefield") incentivefield: Int,
                                    @Query("selectPeriod") selectPeriod: String,
                                    @Query("StartDate") StartDate: String,
                                    @Query("EndDate") EndDate: String,
                                    @Query("PeriodId") PeriodId: Int,
                                    @Query("moduleType") moduleType: String,
                                    @Query("tableDisplay") tableDisplay: Boolean,
                                    @Query("RegionId") RegionId: String,
                                    @Query("userId") salesld: String,

                                    ): Flowable<Response<ExecutiveLeaderBordResponse>>

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


    @POST("merchant/mobile/user")
    @Headers("Content-Type: application/json")
    fun registerUser(@Body post: Register): Flowable<Response<RegisterMainResponse>>


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

    @POST("auth/mobile/refresh-token")
    @Headers("Content-Type: application/json")
    fun reGenerateToken(@Body post: TokenReGenerateModel): Flowable<Response<LoginServiceMainResponse>>

}