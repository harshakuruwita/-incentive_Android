package com.fitscorp.sl.apps.menu

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fitscorp.sl.apps.App

import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.login.User
import com.fitscorp.sl.apps.menu.adapter.LeaderboardAapter
import com.fitscorp.sl.apps.menu.adapter.TimelineAdapter
import com.fitscorp.sl.apps.menu.data.LeaderboardData
import com.fitscorp.sl.apps.menu.data.TimelineMainRespone
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
import javax.inject.Inject

private  var ARG_INCENTID = "incentivefield"
private var ARG_SELECTPERIOD = "selectPeriod"
private var ARG_STARTDATE = "StartDate"
private var ARG_ENDDATE = "EndDate"
private var ARG_PERIODID = "PeriodId"



class TimelineFragment : Fragment() {

    val subscription = CompositeDisposable()


    @Inject
    lateinit var timelineVM: TimelineVM
    @Inject
    lateinit var leaderboardVM: LeaderboardVM

    lateinit var contxt:Context


    // TODO: Rename and change types of parameters
     var incentivefield: Int?=null
    var smBtn:Int=0
    lateinit var selectPeriod: String
    lateinit var StartDate: String
    lateinit var EndDate: String
     var PeriodId: Int?=null
    lateinit var moduleType:String
     var tableDisplay:Boolean = false
    var isLoadFromCash:Boolean = false

    lateinit var salesID:String
    lateinit var userRole:String


    private var listener: OnFragmentInteractionListener? = null

    override fun onDestroy() {
        super.onDestroy()
        subscription.dispose()
    }


   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       Log.d("0099","ewwwww")
    App.getInstance().appComponent.inject(this)

    return inflater.inflate(R.layout.fragment_timeline, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
Log.d("0099","ewwwww")
        if(userRole=="STORE_MANAGER") {
            individulbtn.visibility=View.VISIBLE
            individulbtn2.visibility=View.VISIBLE
        }else{
            individulbtn.visibility=View.GONE
            individulbtn2.visibility=View.GONE
        }

        if(isLoadFromCash){
            callTimelineFromCash()
        }else{

            getTimelineData_MANAGER(view)
            getTimelineData_SALES(view)
            registerUser_STORE_MANAGER(view)
            registerUser_SALES(view)

        }
        individulbtn2.setOnClickListener {

            if(smBtn == 1){

            }else{
                smBtn = 1
                individulbtn2.setBackgroundResource(R.drawable.smbutton)
                individulbtn.setBackgroundResource(R.drawable.srbutton)
                callTimelineFromCashSM()
            }




        }
       individulbtn.setOnClickListener {

           if(smBtn == 0){

           }else{
               smBtn = 0
               individulbtn.setBackgroundResource(R.drawable.smbutton)
               individulbtn2.setBackgroundResource(R.drawable.srbutton)
               callTimelineFromCashREP()
           }

       }
    }





    fun callTimelineFromCash() {

        val data = timelineVM.getData_REP()
        val gson = Gson()
        val type = object : TypeToken<TimelineMainRespone>() {}.type
        val dataLogin = gson.fromJson<TimelineMainRespone>(data, type)

        val dataList=dataLogin.response.dataArr as ArrayList

        if(dataList.isEmpty()){


            val mlayoutManager = LinearLayoutManager(context)

            timeline_recycle.apply {
                layoutManager = mlayoutManager as RecyclerView.LayoutManager?
                adapter = null
            }


            img_nodata.visibility=View.VISIBLE
        }else{
            Log.d("QW1234","Calltiline")
            img_nodata.visibility=View.INVISIBLE
            val mlayoutManager = LinearLayoutManager(context)
            val timelineAapter = TimelineAdapter(contxt,dataList)
            //  otherPaymentOptionsAdapter.onitemClickListener = contxt
            timeline_recycle.apply {
                layoutManager = mlayoutManager as RecyclerView.LayoutManager?
                adapter = timelineAapter
            }}

    }


    fun callTimelineFromCashSM() {

        val data = timelineVM.getData_SM()
        val gson = Gson()
        val type = object : TypeToken<TimelineMainRespone>() {}.type
        val dataLogin = gson.fromJson<TimelineMainRespone>(data, type)

        val dataList=dataLogin.response.dataArr as ArrayList

        if(dataList.isEmpty()){

            val mlayoutManager = LinearLayoutManager(context)

            timeline_recycle.apply {
                layoutManager = mlayoutManager as RecyclerView.LayoutManager?
                adapter = null
            }


            img_nodata.visibility=View.VISIBLE
        }else{
            img_nodata.visibility=View.INVISIBLE
            val mlayoutManager = LinearLayoutManager(context)
            val timelineAapter = TimelineAdapter(contxt,dataList)
            //  otherPaymentOptionsAdapter.onitemClickListener = contxt
            timeline_recycle.apply {
                layoutManager = mlayoutManager as RecyclerView.LayoutManager?
                adapter = timelineAapter
            }}

    }


    fun callTimelineFromCashREP() {

        val data = timelineVM.getData_REP()
        val gson = Gson()
        val type = object : TypeToken<TimelineMainRespone>() {}.type
        val dataLogin = gson.fromJson<TimelineMainRespone>(data, type)

        val dataList=dataLogin.response.dataArr as ArrayList

        if(dataList.isEmpty()){


            val mlayoutManager = LinearLayoutManager(context)

            timeline_recycle.apply {
                layoutManager = mlayoutManager as RecyclerView.LayoutManager?
                adapter = null
            }


            img_nodata.visibility=View.VISIBLE
        }else{
            img_nodata.visibility=View.INVISIBLE
            val mlayoutManager = LinearLayoutManager(context)
            val timelineAapter = TimelineAdapter(contxt,dataList)
            //  otherPaymentOptionsAdapter.onitemClickListener = contxt
            timeline_recycle.apply {
                layoutManager = mlayoutManager as RecyclerView.LayoutManager?
                adapter = timelineAapter
            }}

    }


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
                        val dataList=dataObj.response.dataArr as ArrayList

                        if(dataList.isEmpty()){
                            img_nodata.visibility=View.VISIBLE
                        }else{
                        val mlayoutManager = LinearLayoutManager(context)
                        val timelineAapter = TimelineAdapter(contxt,dataList)
                        //  otherPaymentOptionsAdapter.onitemClickListener = contxt
                        timeline_recycle.apply {
                            layoutManager = mlayoutManager as RecyclerView.LayoutManager?
                            adapter = timelineAapter
                        }}
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


                        val dataList=dataObj.response.dataArr as ArrayList

                        if(dataList.isEmpty()){
                            img_nodata.visibility=View.VISIBLE
                        }else{
                            img_nodata.visibility=View.INVISIBLE
                            val mlayoutManager = LinearLayoutManager(context)
                            val timelineAapter = TimelineAdapter(contxt,dataList)
                            //  otherPaymentOptionsAdapter.onitemClickListener = contxt
                            timeline_recycle.apply {
                                layoutManager = mlayoutManager as RecyclerView.LayoutManager?
                                adapter = timelineAapter
                            }}

                    }

                }
            }, {

                view.progressBar.visibility = View.GONE

            })

        )
    }

////Code for fix pre cash isse///

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


                   }
            }, {

                view.progressBar.visibility = View.GONE

            })

        )
    }
    /////

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }



    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TimelineFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(c:Context, user: User, incentvefield: Int, selectPerio: String, StartDatee: String, EndDatee: String, PeriodIdd: Int, moduleTypeStr:String, tableDisplaybool:Boolean, isLodFromCash:Boolean) =
            TimelineFragment().apply {
                arguments = Bundle().apply {
                   /* putInt(ARG_INCENTID, incentivefield)
                    putString(ARG_SELECTPERIOD, selectPeriod)
                    putString(ARG_STARTDATE, StartDate)
                    putString(ARG_ENDDATE, EndDate)
                    putInt(ARG_PERIODID, PeriodId)
*/

                    Log.d("LOG MANAGER",user.userRole)

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

                    contxt=c
                }
            }
    }
}
