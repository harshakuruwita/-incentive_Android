package com.fitscorp.sl.apps.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.fitscorp.sl.apps.App
import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.common.getRefreshToken
import com.fitscorp.sl.apps.di.BaseActivity
import com.fitscorp.sl.apps.home.model.*
import com.fitscorp.sl.apps.login.LoginActivityVM
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.fitscorp.sl.apps.menu.*
import com.fitscorp.sl.apps.profile.ProfileActivity
import com.fitscorp.sl.apps.profile.ProfileVM
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.fragment_contact_us.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject


class MainActivity : BaseActivity() {

    var incentivefield:Int=0
    var selectPeriod:String?=null
    var startDate:String? = null
    var endDate:String ?= null
    var periodId:Int=0
    var moduleType = "rep"
    var tableDisplay = false
    var isLoadFromCashed = false

    var isRegionSpinerTouch = false

    var periodArray : List<Periods>?=null
    var timePeriodArr : List<TimePeriodArr>?=null

    var childArray : List<TimePeriodArr>?=null
    var babbyArrsy : List<DropDownList>?=null
    var isSalsesIDTabSelected = false;

    var selectedTab:Int=0
    var loadtab:Int=0
    var executiveTab:Int = 0
    var defaultIncentiveId:Int = 0
    var storeSelectedType :Int = 0
    var gd :GradientDrawable?=null
    var dateTimeArrray:List<DropDownList>?=null
    var periodsList : List<Periods>?=null

    var allRegionArr: List<RegionData>?=null
    var selectedRegion:RegionData?=null
    var storeArr: List<StoreData>?=null
    var selectedstore: StoreData?=null
    var salesArr: List<SalesData>?=null
    var selectedsales: SalesData?=null
    var isStoreEnable = false;
    var isSalesEnable = false;

    @Inject
    lateinit var homeVM: HomeVM
    @Inject
    lateinit var loginVM: LoginActivityVM
    @Inject
    lateinit var profileVM: ProfileVM

    lateinit var webURL:String
    lateinit var documentType:String
    lateinit var documentData:String


    companion object {
        fun startActivity(context: Activity) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)

        }

        fun finishActivity(context: Activity) {
            val intent = Intent(context, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        }



    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        webURL = ""
        documentType = ""
        documentData = ""
        App.getInstance().appComponent.inject(this)
        store_meterial_card_sales.setCardBackgroundColor(Color.parseColor("#00B2A9"));
        val intent = intent
        val message = intent.getStringExtra("message")
        if(!message.isNullOrEmpty()) {
            AlertDialog.Builder(this)
                .setTitle("Notification")
                .setMessage(message)
                .setPositiveButton("Ok", { dialog, which -> }).show()
        }

        setViewColors()
        setupBottomMenu()
        readDropDownData();
        readRegionFilter();
        // getAppFilters();
        getDefaultIncentive();



        FirebaseApp.initializeApp(this)

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("SSSSSS", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg = getString(R.string.abc_action_bar_home_description, token)
                Log.d("AAAAA", token)
                var p: FCMModel = FCMModel()
                p.deviceType = "android"
                p.token = token


                sendDevicetoken(p)

            })



        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                childArray=periodArray?.get(position)!!.timePeriodArr
                val dataList=periodArray?.get(position)!!.timePeriodArr
                val spinnerAdapter2 = CompanyAdapter2(this@MainActivity, dataList)
                spinnermonth_type?.adapter = spinnerAdapter2

                spinnerselect_year?.adapter = null



                incentivefield = periodArray?.get(position)!!.incentiveId
                isLoadFromCashed = false


                webURL=periodArray?.get(position)!!.url
                documentType=periodArray?.get(position)!!.documentType
                documentData=periodArray?.get(position)!!.documentData
                if(selectedTab==0){

                    if(childArray!!.isEmpty()){
                        Log.d("XXXXX", "sssssss")
                        navigateToFragment(NoDataFragment.newInstance("", ""))
                    }

                }

                else if(selectedTab==2){
                    webURL=periodArray?.get(position)!!.url
                    documentType=periodArray?.get(position)!!.documentType
                    documentData=periodArray?.get(position)!!.documentData
                    callInfo()
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        spinnermonth_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {


                if((childArray!!.size-1) >=position){



                    babbyArrsy= childArray?.get(position)!!.dropDownList
                    val babbyList= childArray?.get(position)!!.dropDownList
                    val spinnerAdapter3 = CompanyAdapter3(this@MainActivity, babbyList)


                    if((babbyArrsy!!.size-1) >=0){

                        spinnerselect_year?.adapter = spinnerAdapter3
                    }else{

                    }

                    selectPeriod=childArray?.get(position)!!.name
                    isLoadFromCashed=false
                    if(selectedTab==0){
                        callTimeline()
                    }
                    if(selectedTab==1){
                        callLeaderboard()
                    }
                }else{

                }


            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }





        spinnerselect_year.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                startDate = babbyArrsy?.get(position)!!.startDate
                endDate = babbyArrsy?.get(position)!!.endDate
                periodId = babbyArrsy?.get(position)!!.id
                isLoadFromCashed=false
                if(selectedTab==0){
                    callTimeline()
                }
                if(selectedTab==1){
                    callLeaderboard()

                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }



        spinner_executive_region.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                storeSelectedType = 1;
                selectedRegion = allRegionArr!![position];
                updateExecutiveStore(selectedRegion!!, 0);
                executive_headder.visibility = View.VISIBLE
                executive_headder_name.text = selectedRegion!!.regionName;
                selectedstore = null;
                selectedsales = null;
                spinner_executive_store.visibility = View.GONE
                all_store_text.visibility = View.VISIBLE
                executive_search.visibility = View.GONE
                all_store_sales.visibility = View.VISIBLE
                if (selectedTab == 0) {
                    Log.d("334450", "call time line");
                    callTimeline()
                }
                if (selectedTab == 1) {
                    Log.d("334450", "call leader board");
                    callLeaderboard()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        spinner_executive_store.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

//            if(storeArr != null){
//                selectedstore = storeArr!![position];
//            }else{
//                selectedstore = selectedRegion!!.storeData[0];
//            }

                selectedstore = selectedRegion!!.storeData[position];

                storeSelectedType = 0;
                updateExecutiveSalesPersion(selectedstore!!, 0);
                executive_headder.visibility = View.VISIBLE
                executive_headder_name.text = selectedstore!!.storeName;
                all_store_text.visibility = View.GONE
                executive_search.visibility = View.GONE

                selectedsales = null;
                if(selectedTab==0){
                    callTimeline()
                }
                if(selectedTab==1){
                    callLeaderboard()
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }




        spinner_executive_region.visibility = View.GONE
        spinner_executive_store.visibility = View.GONE

        executive_search.visibility = View.GONE
        executive_headder.visibility = View.GONE

    }

    override fun onResume() {

        super.onResume()
        checkToken()


    }


    private fun showAlerts(message: String) {
        val dialogBuilder = AlertDialog.Builder(this)


        dialogBuilder.setMessage(message)
        dialogBuilder.setNegativeButton("Ok") { dialog, which ->

        }
        val alert = dialogBuilder.create()

        alert.setTitle("Error")

        alert.show()
    }



    public fun showAlert(message: String) {
        val dialogBuilder = AlertDialog.Builder(this)


        dialogBuilder.setMessage(message)
        dialogBuilder.setNegativeButton("Ok") { dialog, which ->

        }
        val alert = dialogBuilder.create()

        alert.setTitle("Error")

        alert.show()
    }
    //''



    private fun sendDevicetoken(p: FCMModel) {

        subscription.add(homeVM.sendDeviceToken(p).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mainprogressBar.visibility = View.GONE }
            .doOnTerminate { mainprogressBar.visibility = View.GONE }
            .doOnError { mainprogressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {

                    //  showMessage("vvvv")

                } else {
                    // showMessage(R.string.service_loading_fail)
                }
            }, {
                Log.d("====0======", it.stackTrace.toString())
                progressBar.visibility = View.GONE
                // showMessage(R.string.service_loading_fail)
            })

        )
    }

    private fun setViewColors() {


        val colorData = homeVM.getData()

        if (colorData.isNotEmpty()) {

            var jsonObj: JSONObject? = null
            try {
                jsonObj = JSONObject(colorData)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            val grenColorValue1 = jsonObj!!.optString("color1").toString()
            val grenColorValue2 = jsonObj.optString("color2").toString()
            val grenColorValue3 = jsonObj.optString("color3").toString()

            val logoSmall = jsonObj.optString("logoSmall").toString()
            var jsonArry: JSONArray? = null
            try {
                jsonArry = JSONArray(logoSmall)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            for (i in 0 until jsonArry!!.length()) {
                val item = jsonArry.getJSONObject(i)
                val url = item!!.optString("url").toString()
                Glide.with(this).load(url).into(img_logo)
            }

            txt_page_layout!!.setBackgroundColor(Color.parseColor(grenColorValue3))


            val greenColorValue1 = Color.parseColor(grenColorValue1)
            val greenColorValue2 = Color.parseColor(grenColorValue2)


            val gd = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(greenColorValue2, greenColorValue1)
            )
            gd.gradientType = GradientDrawable.LINEAR_GRADIENT
            gd.cornerRadius = 0f

            top_main_menu.setBackgroundDrawable(gd)
            lay_filter_second.setBackgroundDrawable(gd)

            bottom_menu_view.setBackgroundColor(Color.parseColor(grenColorValue1))
            // bottom_menu_view.setBackgroundDrawable(gd)

            txt_page_layout.setBackgroundColor(Color.parseColor(grenColorValue1))
            img_home.setBackgroundResource(R.drawable.user)
        }



    }

    private fun checkToken() {

        subscription.add(
            homeVM.checkToken().subscribeOn(
                Schedulers.io()
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {


                        val data = homeVM.loginUserMainResponse

                        if (data!!.response.code == 200) {

                            val saveData = homeVM.getLoginData()
                            val gson = Gson()
                            val type = object : TypeToken<LoginUserMainResponse>() {}.type
                            val dataLogin = gson.fromJson<LoginUserMainResponse>(saveData, type)
                            val userType = dataLogin.response.data.user.userRole
                            if (userType != data!!.response.data.user.userRole) {
                                val builder = AlertDialog.Builder(this)
                                builder.setTitle("")
                                builder.setMessage("The user role has been changed. please logout and login with username and password")
                                builder.setCancelable(false)

                                builder.setPositiveButton("Logout") { dialog, which ->
                                    profileVM.callLogout()
                                    SplashActivity.startActivity(this)
                                    finish()
                                }
                                builder.show()
                            }


                        } else {
                            if (data!!.response.message.equals("INVALID_REFRESH_TOKEN_EXPIRED")) {

                                refreshToken();
                            }
                        }


                    } else {


                    }
                }, {

                    progressBar.visibility = View.GONE

                })

        )
//
    }



    private fun refreshToken() {

        var p:TokenReGenerateModel = TokenReGenerateModel()
        p.refresh_token = "refresh_token"
        p.refresh_token = homeVM.sharedPref.getRefreshToken();

        subscription.add(
            homeVM.reGenerateToken(p).subscribeOn(
                Schedulers.io()
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccess) {


                    } else {
                        Toast.makeText(this, "Invalid session", Toast.LENGTH_LONG).show()


                    }
                }, {

                    progressBar.visibility = View.GONE

                })

        )

    }

    class CompanyAdapter(val context: Context, var peridesList: List<Periods>) : BaseAdapter(), SpinnerAdapter {


        val mInflater: LayoutInflater = LayoutInflater.from(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val vh: ItemRowHolder
            if (convertView == null) {
                view = mInflater.inflate(R.layout.spinner_main_cell_dropdown, parent, false)
                vh = ItemRowHolder(view)
                view?.tag = vh
            } else {
                view = convertView
                vh = view.tag as ItemRowHolder
            }

            // setting adapter item height programatically.

            //   val params = view.layoutParams
            //   params.height = 60
            //  view.layoutParams = params

            vh.label.text = peridesList.get(position).incentiveName
            return view
        }

        override fun getItem(position: Int): Any? {

            return null

        }

        override fun getItemId(position: Int): Long {

            return 0

        }

        override fun getCount(): Int {
            return peridesList.size
        }

        private class ItemRowHolder(row: View?) {

            val label: TextView

            init {
                this.label = row?.findViewById(R.id.dropdown) as TextView
            }
        }


    }






    class CompanyAdapter2(val context: Context, var timePerieodsList: List<TimePeriodArr>) : BaseAdapter() {


        val mInflater: LayoutInflater = LayoutInflater.from(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val vh: ItemRowHolder
            if (convertView == null) {
                view = mInflater.inflate(R.layout.spinner_main_cell_dropdown, parent, false)
                vh = ItemRowHolder(view)
                view?.tag = vh
            } else {
                view = convertView
                vh = view.tag as ItemRowHolder
            }

//            val params = view.layoutParams
//            params.height = 60
//              view.layoutParams = params

            vh.label.text = timePerieodsList.get(position).name
//            vh.label.setOnClickListener {
//
//                timePeriodArr=timePerieodsList[position].dropDownList
//
//               // val spinnerAdapter3 = CompanyAdapter3(context, timePerieodsList[position].dropDownList)
//                //updateDropDowns(mainList,0)
//            }
            return view
        }

        override fun getItem(position: Int): Any? {

            return null

        }

        override fun getItemId(position: Int): Long {

            return 0

        }

        override fun getCount(): Int {
            return timePerieodsList.size
        }

        private class ItemRowHolder(row: View?) {

            val label: TextView

            init {
                this.label = row?.findViewById(R.id.dropdown) as TextView
            }
        }
    }

    class CompanyAdapter3(val context: Context, var babbyList: List<DropDownList>) : BaseAdapter() {


        val mInflater: LayoutInflater = LayoutInflater.from(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val vh: ItemRowHolder
            if (convertView == null) {
                view = mInflater.inflate(R.layout.spinner_main_cell_dropdown, parent, false)
                vh = ItemRowHolder(view)
                view?.tag = vh
            } else {
                view = convertView
                vh = view.tag as ItemRowHolder
            }


            vh.label.text = babbyList.get(position).name

            return view
        }

        override fun getItem(position: Int): Any? {

            return null

        }

        override fun getItemId(position: Int): Long {

            return 0

        }

        override fun getCount(): Int {
            return babbyList.size
        }

        private class ItemRowHolder(row: View?) {

            val label: TextView

            init {
                this.label = row?.findViewById(R.id.dropdown) as TextView
            }
        }
    }


    class executiveRegionAdapter(val context: Context, var regionList: List<RegionData>) : BaseAdapter(), SpinnerAdapter {


        val mInflater: LayoutInflater = LayoutInflater.from(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val vh: ItemRowHolder

            if (convertView == null) {
                view = mInflater.inflate(R.layout.spinner_main_cell_dropdown, parent, false)
                vh = ItemRowHolder(view)
                view?.tag = vh
            } else {
                view = convertView
                vh = view.tag as ItemRowHolder
            }
            vh.label.text = regionList.get(position).name
            return view
        }
        override fun getItem(position: Int): Any? {

            return null

        }

        override fun getItemId(position: Int): Long {

            return 0

        }

        override fun getCount(): Int {
            return regionList.size
        }

        private class ItemRowHolder(row: View?) {

            val label: TextView

            init {
                this.label = row?.findViewById(R.id.dropdown) as TextView
            }
        }


    }

    class executiveStoreAdapter(val context: Context, var storeList: List<StoreData>) : BaseAdapter(), SpinnerAdapter {


        val mInflater: LayoutInflater = LayoutInflater.from(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val vh: ItemRowHolder
            if (convertView == null) {
                view = mInflater.inflate(R.layout.spinner_main_cell_dropdown, parent, false)
                vh = ItemRowHolder(view)
                view?.tag = vh
            } else {
                view = convertView
                vh = view.tag as ItemRowHolder
            }
            vh.label.text = storeList.get(position).storeName

            return view
        }

        override fun getItem(position: Int): Any? {

            return null

        }

        override fun getItemId(position: Int): Long {

            return 0

        }

        override fun getCount(): Int {
            return storeList.size
        }

        private class ItemRowHolder(row: View?) {

            val label: TextView

            init {
                this.label = row?.findViewById(R.id.dropdown) as TextView
            }
        }


    }



    class executiveSalesAdapter(val context: Context, var salesList: List<SalesData>) : BaseAdapter(), SpinnerAdapter {


        val mInflater: LayoutInflater = LayoutInflater.from(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val vh: ItemRowHolder
            if (convertView == null) {
                view = mInflater.inflate(R.layout.spinner_main_cell_dropdown, parent, false)
                vh = ItemRowHolder(view)
                view?.tag = vh
            } else {
                view = convertView
                vh = view.tag as ItemRowHolder
            }
            vh.label.text = salesList.get(position).firstName
            return view
        }

        override fun getItem(position: Int): Any? {

            return null

        }

        override fun getItemId(position: Int): Long {

            return 0

        }

        override fun getCount(): Int {
            return salesList.size
        }

        private class ItemRowHolder(row: View?) {

            val label: TextView

            init {
                this.label = row?.findViewById(R.id.dropdown) as TextView
            }
        }


    }



    private fun setupBottomMenu() {

        img_home.setOnClickListener {

            if(selectedTab ==0 || selectedTab== 1 || selectedTab ==2){
                val data = homeVM.getLoginData()
                ProfileActivity.startActivity(this@MainActivity, data)
            }
            if(selectedTab ==3){
                //  sendMessage(null)

                var frafmen=supportFragmentManager.findFragmentById(R.id.content_frame) as ContactUsFragment
                frafmen.snedMessage()

            }


        }


        lay_timeline.setOnClickListener {
            if(loadtab == 0){

            }else{

                isLoadFromCashed=true
                callTimeline()
                loadtab = 0
            }


        }
        layy_timeline.setOnClickListener {
            if(loadtab == 0){

            }else{

                isLoadFromCashed=true
                callTimeline()
                loadtab = 0
            }
        }
        img_timeline.setOnClickListener {
            if(loadtab == 0){

            }else{

                isLoadFromCashed=true
                callTimeline()
                loadtab = 0
            }
        }
        txt_timeline.setOnClickListener {
            if(loadtab == 0){

            }else{

                isLoadFromCashed=true
                callTimeline()
                loadtab = 0
            }
        }


        // loginVM.loginUserMainResponse.response.data.org
        lay_leaderboard.setOnClickListener {

            if(loadtab == 1){

            }else{

                isLoadFromCashed=true
                callLeaderboard()
                loadtab = 1
            }



        }
        layy_leaderboard.setOnClickListener {
            if(loadtab == 1){

            }else{

                isLoadFromCashed=true
                callLeaderboard()
                loadtab = 1
            }
        }
        img_leaderboard.setOnClickListener {
            if(loadtab == 1){

            }else{

                isLoadFromCashed=true
                callLeaderboard()
                loadtab = 1
            }
        }
        txt_leaderboard.setOnClickListener {
            if(loadtab == 1){

            }else{

                isLoadFromCashed=true
                callLeaderboard()
                loadtab = 1
            }
        }



        lay_info_us.setOnClickListener {

            if(loadtab == 2){

            }else{


                callInfo()
                loadtab = 2
            }


        }
        layy_info_us.setOnClickListener {
            if(loadtab == 2){

            }else{


                callInfo()
                loadtab = 2
            }
        }
        img_info_us.setOnClickListener {
            if(loadtab == 2){

            }else{


                callInfo()
                loadtab = 2
            }
        }
        txt_info_us.setOnClickListener {
            if(loadtab == 2){

            }else{


                callInfo()
                loadtab = 2
            }
        }

        lay_contact_us.setOnClickListener {
            if(loadtab == 3){

            }else{

                callContact()
                loadtab = 3
            }

        }
        layy_contact_us.setOnClickListener {
            if(loadtab == 3){

            }else{

                callContact()
                loadtab = 3
            }
        }
        img_contact_us.setOnClickListener {
            if(loadtab == 3){

            }else{

                callContact()
                loadtab = 3
            }
        }
        txt_contact_us.setOnClickListener {
            if(loadtab == 3){

            }else{

                callContact()
                loadtab = 3
            }
        }

        region_card.setOnClickListener {
            executiveTab = 1;
            spinner_executive_region.visibility = View.VISIBLE
            all_region_text.visibility = View.GONE
            spinner_executive_region.performClick();
            executive_headder.visibility = View.VISIBLE

            store_meterial_card_store.setCardBackgroundColor(Color.parseColor("#00B2A9"));
            store_meterial_card_sales.setCardBackgroundColor(Color.parseColor("#00B2A9"));
            isStoreEnable=true;
            isSalsesIDTabSelected = false;
            selectedRegion = allRegionArr!![0];
            executive_headder_name.text = selectedRegion!!.regionName;

            if(selectedTab == 1 ){

                listView.visibility = View.GONE
                isSalsesIDTabSelected = true;
                callLeaderboard()
            }else if(selectedTab == 0 ){

                listView.visibility = View.GONE
                isSalsesIDTabSelected = true;
                callTimeline();
            }

            storeSelectedType == 1;
            selectedstore = selectedRegion!!.storeData[0]
        }

        store_meterial_card_store.setOnClickListener {
            executiveTab = 2;
            Log.d("7866","top")
            if(selectedRegion!=null) {
                if(selectedstore == null){
                    selectedstore = selectedRegion!!.storeData[0];
                }

                all_store_text.visibility = View.GONE
                spinner_executive_store.visibility = View.VISIBLE
                spinner_executive_store.performClick();
                isSalesEnable = true;
                executive_headder_name.text = selectedstore!!.storeName;
                if(selectedTab == 1 ){


                    callLeaderboard()
                }else if(selectedTab == 0 ) {
                    callTimeline();
                }
                isSalsesIDTabSelected = true;


            }
            isSalsesIDTabSelected = false;
        }

        store_meterial_card_sales.setOnClickListener {

            //  if(selectedRegion!=null) {
            executiveTab = 3;

            executive_headder.visibility = View.GONE

            executive_search.visibility = View.VISIBLE


            lateinit var list: ArrayList<String>
            lateinit var adapter: ArrayAdapter<*>
            list = ArrayList()


            if(selectedstore != null) {

                for (arryItem in selectedstore!!.salesData) {
                    list.add(arryItem.firstName)
                }
            }else{

                if(selectedRegion == null){
                    for(region in allRegionArr!!){
                        for (store in region!!.storeData) {
                            for (sales in store.salesData) {
                                list.add(sales.firstName)
                            }
                        }
                    }
                }else{
                    for (store in selectedRegion!!.storeData) {
                        for (sales in store.salesData) {
                            list.add(sales.firstName)
                        }
                    }
                }

            }



            if(selectedTab == 1 && selectedstore == null){
                listView.visibility = View.GONE
                isSalsesIDTabSelected = true;
                callLeaderboard()
            }else if(selectedTab == 1 && selectedstore != null){
                listView.visibility = View.GONE
                isSalsesIDTabSelected = true;
                callLeaderboard()
            }



            else if(selectedTab == 0 && selectedstore == null){
                listView.visibility = View.GONE
                isSalsesIDTabSelected = true;
                callTimeline();
            }



            adapter = ArrayAdapter<String>(this, R.layout.serchlist_adapter, list)
            listView.adapter = adapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {


                    if (list.contains(query)) {
                        adapter.filter.filter(query)
                    } else {
                        //  Toast.makeText(this@MainActivity, "No Match found", Toast.LENGTH_LONG).show()
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {

                    if (newText.length > 0) {
                        listView.visibility = View.VISIBLE
                    } else {
                        listView.visibility = View.GONE
                    }
                    adapter.filter.filter(newText)
                    return false
                }
            });



            listView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val salesName: String = parent!!.getAdapter().getItem(position) as String

                    if (selectedstore != null) {

                        for (arryItem in selectedstore!!.salesData) {
                            if (salesName == arryItem.firstName) {
                                selectedsales = arryItem;
                                if (selectedTab == 0) {
                                    callTimeline()
                                }
                                if (selectedTab == 1) {
                                    callLeaderboard()
                                }
                            }else{
                                if (selectedTab == 0) {
                                    callTimeline()
                                }
                                if (selectedTab == 1) {
                                    callLeaderboard()
                                }
                            }
                        }
                    } else {

                        if (selectedRegion == null) {

                            for (region in allRegionArr!!) {
                                for (store in region!!.storeData) {
                                    for (sales in store.salesData) {
                                        if (salesName == sales.firstName) {
                                            selectedsales = sales;

                                            if (selectedTab == 0) {
                                                Log.d("3344","1244");
                                                callTimeline()
                                            }
                                            if (selectedTab == 1) {
                                                Log.d("3344","1245");
                                                callLeaderboard()
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            Log.d("3345","1235");
                            for (store in selectedRegion!!.storeData) {
                                for (sales in store.salesData) {
                                    if (salesName == sales.firstName) {
                                        selectedsales = sales;
                                        if (selectedTab == 0) {
                                            callTimeline()
                                        }
                                        if (selectedTab == 1) {
                                            callLeaderboard()
                                        }
                                    }else{
                                        if (selectedTab == 0) {
                                            callTimeline()
                                        }
                                        if (selectedTab == 1) {
                                            callLeaderboard()
                                        }
                                    }
                                }
                            }
                        }


//                        for (store in selectedRegion!!.storeData) {
//                            for (sales in store.salesData) {
//                                if (salesName == sales.firstName) {
//                                    selectedsales = sales;
//                                    if (selectedTab == 0) {
//                                        callTimeline()
//                                    }
//                                    if (selectedTab == 1) {
//                                        callLeaderboard()
//                                    }
//                                }
//                            }
//                        }
                    }







                    executive_search.visibility = View.GONE
                    executive_headder.visibility = View.VISIBLE
                    executive_headder_name.text = selectedsales!!.firstName

                }
            })

            // }

        }



        homebutton_card.setOnClickListener {
            executiveTab = 0;
            all_region_text.visibility=View.VISIBLE
            spinner_executive_region.visibility=View.GONE

            store_meterial_card_store.setCardBackgroundColor(Color.parseColor("#000000"));
            isStoreEnable = false;

            all_store_text.visibility = View.VISIBLE
            spinner_executive_store.visibility = View.GONE
            executive_search.visibility = View.GONE

            isSalesEnable = false;
            // store_meterial_card_sales.setCardBackgroundColor(Color.parseColor("#000000"));
            executive_headder.visibility = View.GONE
            all_store_sales.visibility = View.VISIBLE
            selectedRegion=null
            storeArr=null
            selectedstore=null
            salesArr=null
            selectedsales=null
            if(selectedTab==0){
                callTimeline()
            }
            if(selectedTab==1){
                callLeaderboard()
            }
            //   selectedRegion = allRegionArr!![0];
            //   selectedstore = selectedRegion!!.storeData[0]
            storeSelectedType = 0;
        }

        txt_leaderboard.setTextColor(Color.parseColor("#ffffff"))
        txt_info_us.setTextColor(Color.parseColor("#ffffff"))
        txt_contact_us.setTextColor(Color.parseColor("#ffffff"))
        txt_timeline.setTextColor(Color.parseColor("#000000"))


        img_timeline.setImageResource(R.drawable.dashbord_unselect)
        img_leaderboard.setImageResource(R.drawable.leaderbord_selectn)
        img_info_us.setImageResource(R.drawable.info_selectn)
        img_contact_us.setImageResource(R.drawable.contactus_selectn)


    }



    fun callTimeline(){

        val data = homeVM.getLoginData()
        val gson = Gson()
        val type = object : TypeToken<LoginUserMainResponse>() {}.type
        val dataLogin=gson.fromJson<LoginUserMainResponse>(data, type)

        selectedTab=0

        txt_page_heading.text="Dashboard"
        img_home.setBackgroundResource(R.drawable.user)
        top_main_menu.visibility=View.VISIBLE
        lay_filter_second.visibility=View.VISIBLE

        if(dataLogin.response.data.user.userRole=="HEAD_OFFICE") {
            lay_filter_executive.visibility = View.VISIBLE

            if(startDate!=null&&selectedRegion==null&&selectedstore==null&&selectedsales==null) {


                if (isSalsesIDTabSelected) {
                    navigateToFragment(
                        TimelineFragment.newInstanceAllSales(
                            this@MainActivity,
                            dataLogin.response.data.user,
                            incentivefield,
                            selectPeriod!!,
                            startDate!!,
                            endDate!!,
                            periodId,
                            moduleType!!,
                            tableDisplay!!,
                            isLoadFromCashed,
                        )
                    )

                }else{
                    navigateToFragment(
                        TimelineFragment.newInstance(
                            this@MainActivity,
                            dataLogin.response.data.user,
                            incentivefield,
                            selectPeriod!!,
                            startDate!!,
                            endDate!!,
                            periodId,
                            moduleType!!,
                            tableDisplay!!,
                            isLoadFromCashed,


                            )
                    )
                }




            }else if(selectedRegion!=null&&selectedstore==null&&selectedsales==null) {
                Log.d("3344", selectedRegion!!.regionId)
                navigateToFragment(
                    TimelineFragment.newInstanceRegion(
                        this@MainActivity,
                        dataLogin.response.data.user,
                        incentivefield,
                        selectPeriod!!,
                        startDate!!,
                        endDate!!,
                        periodId,
                        moduleType!!,
                        tableDisplay!!,
                        isLoadFromCashed,
                        selectedRegion!!,

                        )
                )
            }
            else if(selectedRegion!=null&&selectedstore!=null&&selectedsales==null) {




                navigateToFragment(
                    TimelineFragment.newInstanceStore(
                        this@MainActivity,
                        dataLogin.response.data.user,
                        incentivefield,
                        selectPeriod!!,
                        startDate!!,
                        endDate!!,
                        periodId,
                        moduleType!!,
                        tableDisplay!!,
                        isLoadFromCashed,
                        selectedRegion!!,
                        selectedstore!!
                    )
                )
            } else if(selectedRegion!=null&&selectedstore!=null&&selectedsales!=null) {
                navigateToFragment(
                    TimelineFragment.newInstanceSales(
                        this@MainActivity,
                        dataLogin.response.data.user,
                        incentivefield,
                        selectPeriod!!,
                        startDate!!,
                        endDate!!,
                        periodId,
                        moduleType!!,
                        tableDisplay!!,
                        isLoadFromCashed,
                        selectedRegion!!,
                        selectedstore!!, selectedsales!!
                    )
                )
            }else if(selectedRegion!=null&&selectedstore==null&&selectedsales!=null) {
                navigateToFragment(
                    TimelineFragment.newInstanceSalesByRegion(
                        this@MainActivity,
                        dataLogin.response.data.user,
                        incentivefield,
                        selectPeriod!!,
                        startDate!!,
                        endDate!!,
                        periodId,
                        moduleType!!,
                        tableDisplay!!,
                        isLoadFromCashed,
                        selectedRegion!!,
                        selectedsales!!
                    )
                )
            }else if(selectedRegion==null&&selectedstore==null&&selectedsales!=null) {
                navigateToFragment(
                    TimelineFragment.newInstancespecifiUser(
                        this@MainActivity,
                        dataLogin.response.data.user,
                        incentivefield,
                        selectPeriod!!,
                        startDate!!,
                        endDate!!,
                        periodId,
                        moduleType!!,
                        tableDisplay!!,
                        isLoadFromCashed,

                        selectedsales!!
                    )
                )
            }
        }else{
            lay_filter_executive.visibility = View.GONE
            if(startDate!=null) {
                navigateToFragment(
                    TimelineFragment.newInstance(
                        this@MainActivity,
                        dataLogin.response.data.user,
                        incentivefield,
                        selectPeriod!!,
                        startDate!!,
                        endDate!!,
                        periodId,
                        moduleType!!,
                        tableDisplay!!,
                        isLoadFromCashed,
                    )
                )
            } else{
                navigateToFragment(NoDataFragment.newInstance("", ""))

            }
        }

        txt_leaderboard.setTextColor(Color.parseColor("#ffffff"))
        txt_info_us.setTextColor(Color.parseColor("#ffffff"))
        txt_contact_us.setTextColor(Color.parseColor("#ffffff"))
        txt_timeline.setTextColor(Color.parseColor("#000000"))


        img_timeline.setImageResource(R.drawable.dashbord_unselect)
        img_leaderboard.setImageResource(R.drawable.leaderbord_selectn)
        img_info_us.setImageResource(R.drawable.info_selectn)
        img_contact_us.setImageResource(R.drawable.contactus_selectn)

    }

    fun callContact(){

        selectedTab=3
        loadtab = 2
        executive_headder.visibility = View.GONE

        executive_search.visibility = View.GONE
        img_home.setBackgroundResource(R.drawable.sendbutton)
        txt_page_heading.text="Contact Us"
        top_main_menu.visibility=View.GONE
        lay_filter_second.visibility=View.GONE
        navigateToFragment(ContactUsFragment.newInstance("", ""))

        txt_leaderboard.setTextColor(Color.parseColor("#ffffff"))
        txt_info_us.setTextColor(Color.parseColor("#ffffff"))
        txt_contact_us.setTextColor(Color.parseColor("#000000"))
        txt_timeline.setTextColor(Color.parseColor("#ffffff"))

        img_timeline.setImageResource(R.drawable.dashbord_select)
        img_leaderboard.setImageResource(R.drawable.leaderbord_selectn)
        img_info_us.setImageResource(R.drawable.info_selectn)
        img_contact_us.setImageResource(R.drawable.contactus_unselect)
    }



    fun callLeaderboard(){

        val data = homeVM.getLoginData()
        val gson = Gson()
        val type = object : TypeToken<LoginUserMainResponse>() {}.type
        val dataLogin=gson.fromJson<LoginUserMainResponse>(data, type)

        selectedTab=1
        img_home.setBackgroundResource(R.drawable.user)
        txt_page_heading.text="Leaderboard"
        top_main_menu.visibility=View.VISIBLE
        lay_filter_second.visibility=View.VISIBLE


        if(dataLogin.response.data.user.userRole=="HEAD_OFFICE") {
            lay_filter_executive.visibility=View.VISIBLE

Log.d("2233","come here")
            if (startDate != null && selectedRegion == null && selectedstore == null && selectedsales == null) {

                if (executiveTab == 0) {
                    navigateToFragment(
                        LeaderboardFragment.byAllRegion(
                            this@MainActivity,
                            dataLogin.response.data.user,
                            incentivefield,
                            selectPeriod!!,
                            startDate!!,
                            endDate!!,
                            periodId,
                            moduleType!!,
                            false,
                            isLoadFromCashed
                        )
                    )
                }else if(executiveTab == 3){
                    navigateToFragment(
                        LeaderboardFragment.byAllUser(
                            this@MainActivity,
                            dataLogin.response.data.user,
                            incentivefield,
                            selectPeriod!!,
                            startDate!!,
                            endDate!!,
                            periodId,
                            moduleType!!,
                            false,
                            isLoadFromCashed
                        )
                    )
                }


            } else if (selectedRegion != null && selectedstore == null && selectedsales == null) {

                Log.d("3398", "Region by")
                if (executiveTab == 1) {
                    navigateToFragment(
                        LeaderboardFragment.newInstanceByRegionAllUser(
                            this@MainActivity,
                            dataLogin.response.data.user,
                            incentivefield,
                            selectPeriod!!,
                            startDate!!,
                            endDate!!,
                            periodId,
                            moduleType!!,
                            false,
                            isLoadFromCashed,
                            selectedRegion!!
                        )
                    )
                }
                else if (executiveTab == 3){
                    Log.d("3398", "Region by all user")
                    navigateToFragment(
                        LeaderboardFragment.newInstanceByRegionAllStoreAllUser(
                            this@MainActivity,
                            dataLogin.response.data.user,
                            incentivefield,
                            selectPeriod!!,
                            startDate!!,
                            endDate!!,
                            periodId,
                            moduleType!!,
                            false,
                            isLoadFromCashed,
                            selectedRegion!!
                        )
                    )
                }

            }
            else if(selectedRegion!=null&&selectedstore!=null&&selectedsales==null) {


                if (executiveTab == 2) {
                    navigateToFragment(
                        LeaderboardFragment.newInstanceByRegionByStoreAllUser(
                            this@MainActivity,
                            dataLogin.response.data.user,
                            incentivefield,
                            selectPeriod!!,
                            startDate!!,
                            endDate!!,
                            periodId,
                            moduleType!!,
                            false,
                            isLoadFromCashed,
                            selectedRegion!!,
                            selectedstore!!

                        )
                    )


                }else  if (executiveTab == 3) {

                    navigateToFragment(
                        LeaderboardFragment.newInstanceByRegionByStoreWithAllUser(
                            this@MainActivity,
                            dataLogin.response.data.user,
                            incentivefield,
                            selectPeriod!!,
                            startDate!!,
                            endDate!!,
                            periodId,
                            moduleType!!,
                            false,
                            isLoadFromCashed,
                            selectedRegion!!,
                            selectedstore!!

                        )
                    )
                }




            } else if(selectedRegion!=null&&selectedstore!=null&&selectedsales!=null) {
                Log.d("1267", "region by user and store");
                navigateToFragment(
                    LeaderboardFragment.byRegionbyStorebyUser(
                        this@MainActivity,
                        dataLogin.response.data.user,
                        incentivefield,
                        selectPeriod!!,
                        startDate!!,
                        endDate!!,
                        periodId,
                        moduleType!!,
                        false,
                        isLoadFromCashed,
                        selectedRegion!!,
                        selectedstore!!,
                        selectedsales!!
                    )
                )
            }else if(selectedRegion!=null&&selectedstore==null&&selectedsales!=null) {
                Log.d("1267", "region by user");
                navigateToFragment(
                    LeaderboardFragment.byRegionbyAllbyUser(
                        this@MainActivity,
                        dataLogin.response.data.user,
                        incentivefield,
                        selectPeriod!!,
                        startDate!!,
                        endDate!!,
                        periodId,
                        moduleType!!,
                        false,
                        isLoadFromCashed,
                        selectedRegion!!,
                        selectedsales!!
                    )
                )
            }else if(selectedRegion==null&&selectedstore==null&&selectedsales!=null) {
                Log.d("1267", "region by user");
                navigateToFragment(
                    LeaderboardFragment.byspecificuserUser(
                        this@MainActivity,
                        dataLogin.response.data.user,
                        incentivefield,
                        selectPeriod!!,
                        startDate!!,
                        endDate!!,
                        periodId,
                        moduleType!!,
                        false,
                       false,
                        selectedsales!!
                    )
                )
            }



        }else{
            lay_filter_executive.visibility=View.GONE
            if(startDate!=null){
                navigateToFragment(
                    LeaderboardFragment.newInstance(
                        this@MainActivity,
                        dataLogin.response.data.user,
                        incentivefield,
                        selectPeriod!!,
                        startDate!!,
                        endDate!!,
                        periodId,
                        moduleType!!,
                        false,
                        isLoadFromCashed
                    )
                )
            }else{
                navigateToFragment(NoDataFragment.newInstance("", ""))

            }
        }









        txt_leaderboard.setTextColor(Color.parseColor("#000000"))
        txt_info_us.setTextColor(Color.parseColor("#ffffff"))
        txt_contact_us.setTextColor(Color.parseColor("#ffffff"))
        txt_timeline.setTextColor(Color.parseColor("#ffffff"))

        img_timeline.setImageResource(R.drawable.dashbord_select)
        img_leaderboard.setImageResource(R.drawable.leaderbord_unselect)
        img_info_us.setImageResource(R.drawable.info_selectn)
        img_contact_us.setImageResource(R.drawable.contactus_selectn)

    }
    fun callInfo(){

        selectedTab=2
        executive_headder.visibility = View.GONE

        executive_search.visibility = View.GONE
        txt_page_heading.text="Info"
        img_home.setBackgroundResource(R.drawable.user)
        top_main_menu.visibility=View.VISIBLE
        lay_filter_second.visibility=View.GONE
        lay_filter_executive.visibility=View.GONE

        navigateToFragment(InfoFragment.newInstance(webURL, documentType, documentData))

        txt_leaderboard.setTextColor(Color.parseColor("#ffffff"))
        txt_info_us.setTextColor(Color.parseColor("#000000"))
        txt_contact_us.setTextColor(Color.parseColor("#ffffff"))
        txt_timeline.setTextColor(Color.parseColor("#ffffff"))

        img_timeline.setImageResource(R.drawable.dashbord_select)
        img_leaderboard.setImageResource(R.drawable.leaderbord_selectn)
        img_info_us.setImageResource(R.drawable.info_unselect)
        img_contact_us.setImageResource(R.drawable.contactus_selectn)

    }
    private fun navigateToFragment(fragmentToNavigate: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content_frame, fragmentToNavigate)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun updateDropDowns(peridesList: List<Periods>, position: Int){

        periodArray=peridesList

        val spinnerAdapter1 = CompanyAdapter(this, peridesList)
        spinner?.adapter = spinnerAdapter1
        spinner?.setSelection(position,true);

        if(peridesList[0].timePeriodArr.isNotEmpty() ){
            timePeriodArr=peridesList[0].timePeriodArr
            dateTimeArrray= peridesList[0].timePeriodArr[0].dropDownList


            incentivefield=periodArray?.get(0)!!.incentiveId

            selectPeriod=timePeriodArr?.get(0)!!.name

            startDate = dateTimeArrray?.get(0)!!.startDate
            endDate = dateTimeArrray?.get(0)!!.endDate
            periodId = dateTimeArrray?.get(0)!!.id

            val fromActivity:String = intent.getStringExtra("fromActivity")

            if(fromActivity=="contact"){
                callContact()

            }else{
                callTimeline()
            }
        }
    }



    private fun updateExecutiveRegion(regionList: List<RegionData>, position: Int){

        allRegionArr = regionList
        val regionAdapter = executiveRegionAdapter(this, regionList)
        spinner_executive_region?.adapter = regionAdapter

    }

    private fun updateExecutiveStore(selectedRegionList: RegionData, position: Int){

        storeArr = selectedRegionList.storeData
        val storeAdapter = executiveStoreAdapter(this, storeArr!!)
        spinner_executive_store?.adapter = storeAdapter

    }

    private fun updateExecutiveSalesPersion(electedStoreData: StoreData, position: Int){

        salesArr = electedStoreData.salesData
        val salesAdapter = executiveSalesAdapter(this, salesArr!!)

    }



    private fun readDropDownData(){
        AsyncTask.execute { // Insert Data
            val data = homeVM.getFilterDropdown();
            val gson = Gson();
            val type = object : TypeToken<HomeMainResponse>() {}.type
            val savedResponse = gson.fromJson<HomeMainResponse>(data, type)

            if(savedResponse != null){
                if(savedResponse.response.code == 200){
                    val mainList = savedResponse.response.periods
                    periodsList = mainList;
                    webURL = mainList[0].url
                    documentType = mainList[0].documentType
                    documentData = mainList[0].documentData
                    updateDropDowns(mainList, defaultIncentiveId)
                    mainprogressBar.visibility = View.GONE
                }
                getAppFilters()

            }else{
                getAppFilters()
            }
        }
    }


    private fun readRegionFilter(){
        AsyncTask.execute { // Insert Data
            val data = homeVM.getExecutiveRegionData();
            val gson = Gson();
            val type = object : TypeToken<ExecutiveFilterResponse>() {}.type
            val savedResponse = gson.fromJson<ExecutiveFilterResponse>(data, type)

            if(savedResponse != null){
                if(savedResponse.response.code == 200){
                    val mainList = savedResponse.response.regionData

                    updateExecutiveRegion(mainList, 0);
                    mainprogressBar.visibility = View.GONE
                }
            }else{

            }
        }
    }



    private fun getAppFilters() {

        subscription.add(homeVM.getAppFilters().subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mainprogressBar.visibility = View.GONE }
            .doOnTerminate { mainprogressBar.visibility = View.GONE }
            .doOnError { mainprogressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {

                    val res: HomeMainResponse = homeVM.dataObj!!
                    if (res.response.code == 200) {

                        val mainList = res.response.periods
                        periodsList = mainList;
                        webURL = mainList[0].url
                        documentType = mainList[0].documentType
                        documentData = mainList[0].documentData
                        updateDropDowns(mainList, defaultIncentiveId)

                    } else {
                        val dialogBuilder = AlertDialog.Builder(this)
                        dialogBuilder.setMessage("No incentives to display")
                        val alert = dialogBuilder.create()

                        alert.setTitle("Sorry")

                        alert.show()
                    }


                } else {


                }
            }, {

                mainprogressBar.visibility = View.GONE

            })

        )
    }



    private fun getDefaultIncentive() {

        subscription.add(homeVM.getDefaultIncentive().subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mainprogressBar.visibility = View.GONE }
            .doOnTerminate { mainprogressBar.visibility = View.GONE }
            .doOnError { mainprogressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {
                    val defaultIncentiveModel: DefaultIncentiveModel =
                        homeVM.defaultIncentiveModel!!

                    if (defaultIncentiveModel!!.response.code == 200) {
                        var incentivedata = defaultIncentiveModel!!.response.data.Detail;

                        val splitArray: List<String> = incentivedata.split(":")
                        val secondSplitArray: List<String> = splitArray[1].split(":")
                        val thirdSplitArray: List<String> = secondSplitArray[0].split(",")
                        Log.d("55667", thirdSplitArray[0]);

                        var i = 0;
                        for(region in periodsList!!){
                            if(region.incentiveId == thirdSplitArray[0].toInt()){
                                defaultIncentiveId = i;
                            }
                            i = i+1;
                        }
                    }


                } else {


                }
            }, {

                mainprogressBar.visibility = View.GONE

            })

        )
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {


        if (keyCode == KeyEvent.KEYCODE_DEL) {
            Log.d("ddddd", "dddd")
        }else{
            if(loadtab == 0 || loadtab == 1 || loadtab == 2 || loadtab == 3){
                val startMain = Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
            }
        }


        return super.onKeyDown(keyCode, event)

    }

}


