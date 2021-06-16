package com.fitscorp.sl.apps.menu


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fitscorp.sl.apps.App
import android.util.Log
import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.login.User
import com.fitscorp.sl.apps.menu.adapter.LeaderboardAapter
import com.fitscorp.sl.apps.menu.adapter.TimelineAdapter
import com.fitscorp.sl.apps.menu.data.Leaderboard
import com.fitscorp.sl.apps.menu.data.LeaderboardData
import com.fitscorp.sl.apps.menu.data.TimelineMainRespone

import com.fitscorp.sl.apps.menu.vm.ContactVM
import com.fitscorp.sl.apps.menu.vm.LeaderboardVM
import com.fitscorp.sl.apps.menu.vm.TimelineVM
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_contact_us.view.*
import kotlinx.android.synthetic.main.fragment_leaderboard.*
import kotlinx.android.synthetic.main.fragment_timeline.*
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */

private var param1: String? = null
private var param2: String? = null
private var listener: InfoFragment.OnFragmentInteractionListener? = null


class LeaderboardFragment : Fragment() {

    val subscription = CompositeDisposable()

    @Inject
    lateinit var leaderboardVM: LeaderboardVM
    @Inject
    lateinit var timelineVM: TimelineVM
    // TODO: Rename and change types of parameters
    var incentivefield: Int?=null
    lateinit var salesID:String
    lateinit var storeId:String

    lateinit var userRole:String
    lateinit var selectPeriod: String
    lateinit var StartDate: String
    lateinit var EndDate: String
    var PeriodId: Int?=null
    var storePrimaryId = 0
    var selecttype = 0
    var scrolableIndex = 0;
    lateinit var moduleType:String
    var tableDisplay:Boolean = true
    var isLoadFromCash:Boolean = false
    lateinit var contxt:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }




    }
    override fun onDestroy() {
        super.onDestroy()

        subscription.dispose()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        App.getInstance().appComponent.inject(this)

        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        if(userRole=="STORE_MANAGER") {
            individulbtnle.visibility=View.VISIBLE
            individulbtnle2.visibility=View.VISIBLE
            jumptostore.visibility=View.VISIBLE
        }else{
            jumptostore.visibility=View.VISIBLE
            individulbtnle.visibility=View.GONE
            individulbtnle2.visibility=View.GONE
        }

        individulbtnle2.setOnClickListener {
            individulbtnle2.setBackgroundResource(R.drawable.smbutton)
            individulbtnle.setBackgroundResource(R.drawable.srbutton)
            selecttype = 1;
            callTimelineFromCash_SM()
        }
        individulbtnle.setOnClickListener {
            individulbtnle.setBackgroundResource(R.drawable.smbutton)
            individulbtnle2.setBackgroundResource(R.drawable.srbutton)
            selecttype = 0;
            callTimelineFromCash_REP()
        }

        jumptostore.setOnClickListener {
        Log.d("SINNNNNNNNNNN",scrolableIndex.toString());
                leaderboard_recycler.smoothScrollToPosition(scrolableIndex);

        }



        if(loadLocalData()){
            if(isLoadFromCash){
                callTimelineFromCash_REP()
            }else{
                registerUser_STORE_MANAGER(view)
                registerUser_SALES(view)

                getTimelineData_MANAGER(view)
                getTimelineData_SALES(view)
            }
        }else{
            registerUser_STORE_MANAGER(view)
            registerUser_SALES(view)

            getTimelineData_MANAGER(view)
            getTimelineData_SALES(view)
        }
/*
       //if(loadLocalData()){
           if(isLoadFromCash){
               callTimelineFromCash_REP()
           }else{
              // if(userRole=="STORE_MANAGER"){
                   registerUser_STORE_MANAGER(view)
             //  }else{
                   registerUser_SALES(view)
              // }
           }
       /*}else{
          // if(userRole=="STORE_MANAGER"){
               registerUser_STORE_MANAGER(view)
          // }else{
               registerUser_SALES(view)
         //  }
       }*/
*/

    }

    fun loadLocalData(): Boolean {
        var isDataAvailable:Boolean = false
        val data = leaderboardVM.getData_REP()

        val gson = Gson()
        val type = object : TypeToken<LoginUserMainResponse>() {}.type

        if(gson.fromJson<LoginUserMainResponse>(data, type)==null){

        }else{
            isDataAvailable=true
        }

        return isDataAvailable
    }


    fun callTimelineFromCash_REP() {

        val data = leaderboardVM.getData_REP()
        Log.d("AB33",data)
        val gson = Gson()
        val type = object : TypeToken<Leaderboard>() {}.type
        val dataLogin = gson.fromJson<Leaderboard>(data, type)

        val dataObj=dataLogin!!.response.dataArr as ArrayList
        var  firstPlace_colour = "#f5c242"
        var leaderBoard_colour = "#f5c242"

if(dataObj.count() >0){
    var cSetting=dataLogin!!.response.incentive.get(0).ColorSettings


    var jsonObj: JSONObject? = null
    try {
        jsonObj = JSONObject(cSetting)
    } catch (e: JSONException) {
        e.printStackTrace()
    }


      firstPlace_colour=jsonObj!!.optString("firstPlace").toString()
      leaderBoard_colour=jsonObj!!.optString("leaderBoard").toString()
}


        if(dataObj!!.isNotEmpty()) {
            img_nodataimg.visibility=View.GONE
            val mlayoutManager = LinearLayoutManager(context)
            val timelineAapter = LeaderboardAapter(contxt, dataObj as ArrayList<LeaderboardData>,salesID,storePrimaryId,firstPlace_colour,leaderBoard_colour)
            var loopVal = 0;
            for (idArry in dataObj){

                if(salesID==idArry.userId || storePrimaryId== idArry.StorePrimaryId ){
                    scrolableIndex = loopVal
                }
                loopVal = loopVal + 1;

            }

            leaderboard_recycler.apply {
                layoutManager = mlayoutManager as RecyclerView.LayoutManager?
                adapter = timelineAapter
            }


        }else{
            img_nodataimg.visibility=View.VISIBLE


            val mlayoutManager = LinearLayoutManager(context)
            val timelineAapter = LeaderboardAapter(contxt, dataObj as ArrayList<LeaderboardData>,salesID,storePrimaryId,firstPlace_colour,leaderBoard_colour)

            var loopVal = 0;
            for (idArry in dataObj){

                if(salesID==idArry.userId || storePrimaryId== idArry.StorePrimaryId ){
                    scrolableIndex = loopVal
                }
                loopVal = loopVal + 1;

            }




            leaderboard_recycler.apply {
                layoutManager = mlayoutManager as RecyclerView.LayoutManager?
                adapter = timelineAapter
            }
        }
    }

    fun callTimelineFromCash_SM() {

        val data = leaderboardVM.getData_SM()

        val gson = Gson()
        val type = object : TypeToken<Leaderboard>() {}.type
        val dataLogin = gson.fromJson<Leaderboard>(data, type)

        val dataObj=dataLogin!!.response.dataArr as ArrayList

        var  firstPlace_colour = "#f5c242"
        var leaderBoard_colour = "#f5c242"

        if(dataObj.count() >0){
            var cSetting=dataLogin!!.response.incentive.get(0).ColorSettings


            var jsonObj: JSONObject? = null
            try {
                jsonObj = JSONObject(cSetting)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            Log.d("ZX^&&**",data)




            firstPlace_colour=jsonObj!!.optString("firstPlace").toString()
            leaderBoard_colour=jsonObj!!.optString("leaderBoard").toString()
        }

        if(dataObj!!.isNotEmpty()) {


            Log.d("ffdfdffdfdfd",data)

            img_nodataimg.visibility=View.GONE
            val mlayoutManager = LinearLayoutManager(context)
            val timelineAapter = LeaderboardAapter(contxt, dataObj as ArrayList<LeaderboardData>,salesID,storePrimaryId,firstPlace_colour,leaderBoard_colour)

var loopVal = 0;
            for (idArry in dataObj){

                if(salesID==idArry.userId || storePrimaryId== idArry.StorePrimaryId ){
                    scrolableIndex = loopVal
                }
                loopVal = loopVal + 1;

            }



            leaderboard_recycler.apply {
                layoutManager = mlayoutManager as RecyclerView.LayoutManager?
                adapter = timelineAapter
            }


        }else{
            img_nodataimg.visibility=View.VISIBLE


            val mlayoutManager = LinearLayoutManager(context)
            val timelineAapter = LeaderboardAapter(contxt, dataObj as ArrayList<LeaderboardData>,salesID,storePrimaryId,firstPlace_colour,leaderBoard_colour)

            var loopVal = 0;
            for (idArry in dataObj){

                if(salesID==idArry.userId || storePrimaryId== idArry.StorePrimaryId ){
                    scrolableIndex = loopVal
                }
                loopVal = loopVal + 1;

            }


            leaderboard_recycler.apply {
                layoutManager = mlayoutManager as RecyclerView.LayoutManager?
                adapter = timelineAapter
            }
        }

    }

    private fun registerUser_STORE_MANAGER(view:View) {

        subscription.add(leaderboardVM.getLeaderBoardSM(incentivefield!!,selectPeriod,StartDate,EndDate,PeriodId!!,moduleType,tableDisplay).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.progressBar.visibility = View.VISIBLE }
            .doOnTerminate { view.progressBar.visibility = View.GONE }
            .doOnError { view.progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {


                    val  dataObj=leaderboardVM.dataObj

                    val  firstPlace_colour=leaderboardVM.firstPlace_colour
                    val  leaderBoard_colour=leaderboardVM.leaderBoard_colour
                   if(dataObj!!.isNotEmpty()) {
                       img_nodataimg.visibility=View.GONE
                       val mlayoutManager = LinearLayoutManager(context)
                      val timelineAapter = LeaderboardAapter(contxt, dataObj as ArrayList<LeaderboardData>,salesID,storePrimaryId,firstPlace_colour,leaderBoard_colour)

                       var loopVal = 0;
                       for (idArry in dataObj){

                           if(salesID==idArry.userId || storePrimaryId== idArry.StorePrimaryId ){
                               scrolableIndex = loopVal
                           }
                           loopVal = loopVal + 1;

                       }




                       leaderboard_recycler.apply {
                           layoutManager = mlayoutManager as RecyclerView.LayoutManager?
                           adapter = timelineAapter
                       }


                   }else{
                       img_nodataimg.visibility=View.VISIBLE
                   }}else{
                    img_nodataimg.visibility=View.VISIBLE
                }
            }, {

                view.progressBar.visibility = View.GONE

            })

        )
    }

    private fun registerUser_SALES(view:View) {

        subscription.add(leaderboardVM.getLeaderBoard(incentivefield!!,selectPeriod,StartDate,EndDate,PeriodId!!,moduleType,tableDisplay).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.progressBar.visibility = View.VISIBLE }
            .doOnTerminate { view.progressBar.visibility = View.GONE }
            .doOnError { view.progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {


                    val  dataObj=leaderboardVM.dataObj

                    val  firstPlace_colour=leaderboardVM.firstPlace_colour
                    val  leaderBoard_colour=leaderboardVM.leaderBoard_colour
                    if(dataObj!!.isNotEmpty()) {
                        img_nodataimg.visibility=View.GONE
                        val mlayoutManager = LinearLayoutManager(context)
                        val timelineAapter = LeaderboardAapter(contxt, dataObj as ArrayList<LeaderboardData>,salesID,storePrimaryId,firstPlace_colour,leaderBoard_colour)

                        var loopVal = 0;
                        for (idArry in dataObj){

                            if(salesID==idArry.userId || storePrimaryId== idArry.StorePrimaryId ){
                                scrolableIndex = loopVal
                            }
                            loopVal = loopVal + 1;

                        }



                        leaderboard_recycler.apply {
                            layoutManager = mlayoutManager as RecyclerView.LayoutManager?
                            adapter = timelineAapter
                        }


                    }else{
                        img_nodataimg.visibility=View.VISIBLE
                    }}else{
                    img_nodataimg.visibility=View.VISIBLE
                }
            }, {

                view.progressBar.visibility = View.GONE

            })

        )
    }
//////fix cash issue////////

    private fun getTimelineData_MANAGER(view:View) {

        subscription.add(timelineVM.getTimeLineStoreSM(incentivefield!!,selectPeriod,StartDate,EndDate,PeriodId!!,moduleType,tableDisplay).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.progressBar.visibility = View.VISIBLE }
            .doOnTerminate { view.progressBar.visibility = View.GONE }
            .doOnError { view.progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {
                    val  dataObj=timelineVM.dataObj
                    if(dataObj!!.response.code==200){

/*

*/
                    }

                }
            }, {

                view.progressBar.visibility = View.GONE

            })

        )
    }

    private fun getTimelineData_SALES(view:View) {

        subscription.add(timelineVM.getTimeLineStore(incentivefield!!,selectPeriod,StartDate,EndDate,PeriodId!!,moduleType,tableDisplay).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.progressBar.visibility = View.VISIBLE }
            .doOnTerminate { view.progressBar.visibility = View.GONE }
            .doOnError { view.progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {
                    val  dataObj=timelineVM.dataObj
                    if(dataObj!!.response.code==200){



                    }

                }
            }, {

                view.progressBar.visibility = View.GONE

            })

        )
    }
    //////////


    companion object {
        @JvmStatic
        fun newInstance(c:Context, user: User, incentvefield: Int, selectPerio: String, StartDatee: String, EndDatee: String, PeriodIdd: Int, moduleTypeStr:String, tableDisplaybool:Boolean,isLodFromCash:Boolean) =
            LeaderboardFragment().apply {
                arguments = Bundle().apply {
                    /* putInt(ARG_INCENTID, incentivefield)
                     putString(ARG_SELECTPERIOD, selectPeriod)
                     putString(ARG_STARTDATE, StartDate)
                     putString(ARG_ENDDATE, EndDate)
                     putInt(ARG_PERIODID, PeriodId)
 */
                    salesID=user.salesId
                    userRole=user.userRole
                    incentivefield = incentvefield
                    selectPeriod = selectPerio
                    StartDate =  StartDatee
                    EndDate = EndDatee
                    PeriodId = PeriodIdd
                    moduleType = moduleTypeStr
                    tableDisplay = tableDisplaybool
                    isLoadFromCash=isLodFromCash
                    storePrimaryId=user.storeId

                    contxt=c
                }
            }
    }


}
