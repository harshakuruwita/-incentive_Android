package com.fitscorp.sl.apps.register


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.fitscorp.sl.apps.App
import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.common.MERCHANT_ID
import com.fitscorp.sl.apps.common.MERCHANT_URL
import com.fitscorp.sl.apps.di.BaseActivity
import com.fitscorp.sl.apps.login.LoginActivity
import com.skyhope.materialtagview.TagView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern
import javax.inject.Inject


class RegisterActivity : BaseActivity() {

     var tagView: TagView? = null
    var salelist=ArrayList<String>()
    var salelistepty=ArrayList<String>()
    var userRole:String?=null
    var storeId:Int?=null


    @Inject
    lateinit var registerActivityVM: RegisterActivityVM

    companion object {
        fun startActivity(context: Activity) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.fitscorp.sl.apps.R.layout.activity_register)

        App.getInstance().appComponent.inject(this)


        Log.d("Register View","In OnCreate")
        //(layout_saleid_secndry.findViewById(R.id.tag_container)as TextView).setHint("fffffff")


        (layout_email.findViewById(R.id.register_feild_lable) as TextView).text = "Email"
        (layout_firstname.findViewById(R.id.register_feild_lable) as TextView).text = "First Name"
        (layout_lastname.findViewById(R.id.register_feild_lable) as TextView).text = "Last Name"
        (layout_mobilenumber.findViewById(R.id.register_feild_lable) as TextView).text = "Mobile Number"
        (layout_mobilenumber.findViewById(R.id.register_feild_lable) as TextView).inputType =
            InputType.TYPE_CLASS_PHONE
        (layout_saleid.findViewById(R.id.register_feild_lable) as TextView).text = "Sales ID"

        (layout_saleid_tag.findViewById(R.id.register_feild_lable) as TextView).text = "Secondary Sales ID"

        

        spinner_view.visibility=View.GONE

        val colors = arrayOf("SalesRep","Store Manager")

        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,colors)

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        edt_title_user_spinner.adapter = adapter;
        edt_title_user_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){

                if(parent.getItemAtPosition(position)=="Store Manager"){
                    getStoreData(MERCHANT_ID)
                    userRole="STORE_MANAGER"

                }else{
                    getStoreData(MERCHANT_ID)
                    userRole="SALES_REP"
                    spinner_view.visibility=View.GONE

                }
            }
            override fun onNothingSelected(parent: AdapterView<*>){
            }


        }

        btn_register.setOnClickListener {
            registerUser()
        }

//       tag_view_test.setHint("Secondary Sales ID (Press space to add)")
//        //tagView.addTagSeparator(TagSeparator.AT_SEPARATOR);
//
//        tag_view_test.addTagLimit(20)
//        val tagList = arrayOf("")
//        tag_view_test.setTagList(salelistepty)
//        tag_view_test.initTagListener(object :TagItemListener{
//            override fun onGetRemovedItem(model: TagModel?) {
//
//
//                if(salelist.isNotEmpty()) {
//                    salelist.removeAt(salelist.size - 1)
//                }
//
//
//                      }
//
//            override fun onGetAddedItem(tagModel: TagModel?) {
//                Log.d("8878",tagModel?.tagText)
//
//
//
//
//                if(checkingUnicode(tagModel!!.tagText) || tagModel!!.tagText == ""){
//                    showAlert("Invalid sales ID! Enter only alphanumeric value without spaces!")
//                }else{
//                    salelist.add(tagModel!!.tagText)
//                }
//
//            }
//        })


    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    private fun registerUser() {


       val email= (layout_email.findViewById(R.id.register_feild_text) as TextView).text.toString()
       val fname= (layout_firstname.findViewById(R.id.register_feild_text) as TextView).text.toString()
       val lname= (layout_lastname.findViewById(R.id.register_feild_text) as TextView).text.toString()
       val mobilenumber= (layout_mobilenumber.findViewById(R.id.register_feild_text) as TextView).text.toString()
      // val storeId= (layout_storeid.findViewById(R.id.register_feild_text) as TextView).text.toString()
       val salesid=(layout_saleid.findViewById(R.id.register_feild_text) as TextView).text.toString()

        val salesid_secondry_1=(layout_saleid_tag.findViewById(R.id.register_feild_text_1) as TextView).text.toString()
        val salesid_secondry_2=(layout_saleid_tag.findViewById(R.id.register_feild_text_2) as TextView).text.toString()
        val salesid_secondry_3=(layout_saleid_tag.findViewById(R.id.register_feild_text_3) as TextView).text.toString()
       val userType="ORGANIZATION"
       val currentStatus="pending"
       val organizationId= MERCHANT_ID
      //  val domainVAL = "https://smugkidcorp.pcincentives.co.nz"

        val domainVAL = MERCHANT_URL


     //  var salelist=ArrayList<String>()
     //  salelist.add("")

        val salesIdList=salelist.toString()



        if( email.isEmpty() ){

            if( fname.isEmpty() || lname.isEmpty() || mobilenumber.isEmpty() || salesid.isEmpty()){
                showAlert("Please fill the required data!")

                return
            }else{
                showAlert("Email is required!")

                return
            }

        }

        else if(!isEmailValid(email)){

            showAlert("Invalid email address!")

            return



        } else if( fname.isEmpty() ){

            if( lname.isEmpty() || mobilenumber.isEmpty() || salesid.isEmpty()){
                showAlert("Please fill the required data!")
              //  Toast.makeText(this@RegisterActivity,"Please fill the required data!",Toast.LENGTH_LONG).show()
                return
            }else{
                showAlert("First name is required !")
              //  Toast.makeText(this@RegisterActivity,"First name is required.",Toast.LENGTH_LONG).show()
                return
            }

        }else if( lname.isEmpty()){

            if( fname.isEmpty() || mobilenumber.isEmpty() || salesid.isEmpty()){
                showAlert("Please fill the required data!")
                //Toast.makeText(this@RegisterActivity,"Please fill the required data!",Toast.LENGTH_LONG).show()
                return
            }else{
                showAlert("Last name is required!")
               // Toast.makeText(this@RegisterActivity,"Last name is required.",Toast.LENGTH_LONG).show()
                return
            }

        }else if( mobilenumber.isEmpty() ){

            if( fname.isEmpty() || lname.isEmpty() || salesid.isEmpty()){
                showAlert("Please fill the required data!")
              //  Toast.makeText(this@RegisterActivity,"Please fill the required data!",Toast.LENGTH_LONG).show()
                return
            }else{
                showAlert("Mobile number is required!")
              //  Toast.makeText(this@RegisterActivity,"Mobile number is required.",Toast.LENGTH_LONG).show()
                return
            }


        }else if( salesid.isEmpty() ){

            if( fname.isEmpty() || lname.isEmpty() || mobilenumber.isEmpty()){
                showAlert("Please fill the required data!")
             //   Toast.makeText(this@RegisterActivity,"Please fill the required data!",Toast.LENGTH_LONG).show()
                return
            }else{
                showAlert("Sales ID is required!")
                //Toast.makeText(this@RegisterActivity,"Sales ID is required.",Toast.LENGTH_LONG).show()
                return
            }

        }else if( salesid.contains(" ") ){

            if( fname.isEmpty() || lname.isEmpty() || mobilenumber.isEmpty()){
                showAlert("Please fill the required data!")
                //   Toast.makeText(this@RegisterActivity,"Please fill the required data!",Toast.LENGTH_LONG).show()
                return
            }else{
                showAlert("Invalid sales ID! No spaces allowed!")
                //Toast.makeText(this@RegisterActivity,"Sales ID is required.",Toast.LENGTH_LONG).show()
                return
            }

        }else if( mobilenumber.contains(" ") ){

            if( fname.isEmpty() || lname.isEmpty() || mobilenumber.isEmpty()){
                showAlert("Please fill the required data!")
                //   Toast.makeText(this@RegisterActivity,"Please fill the required data!",Toast.LENGTH_LONG).show()
                return
            }else{
                showAlert("Invalid mobile number! No spaces allowed!")
                //Toast.makeText(this@RegisterActivity,"Sales ID is required.",Toast.LENGTH_LONG).show()
                return
            }

        }else if(checkingUnicode(mobilenumber) ){

            if( fname.isEmpty() || lname.isEmpty() || salesid.isEmpty()){
                showAlert("Please fill the required data!")
                //   Toast.makeText(this@RegisterActivity,"Please fill the required data!",Toast.LENGTH_LONG).show()
                return
            }else{
                showAlert("Invalid mobile number! Enter only numeric value without spaces!")
                //Toast.makeText(this@RegisterActivity,"Sales ID is required.",Toast.LENGTH_LONG).show()
                return
            }

        }





        else if(checkingUnicode(salesid) ){

            if( fname.isEmpty() || lname.isEmpty() || mobilenumber.isEmpty()){
                showAlert("Please fill the required data!")
                //   Toast.makeText(this@RegisterActivity,"Please fill the required data!",Toast.LENGTH_LONG).show()
                return
            }else{
                showAlert("Invalid sales ID! Enter only alphanumeric value without spaces!")
                //Toast.makeText(this@RegisterActivity,"Sales ID is required.",Toast.LENGTH_LONG).show()
                return
            }

        }else if(checkingUnicode(salesid_secondry_1) || checkingUnicode(salesid_secondry_2) || checkingUnicode(salesid_secondry_3)){
            showAlert("Invalid secondary sales ID! Enter only alphanumeric value or email without spaces!")
            //Toast.makeText(this@RegisterActivity,"Sales ID is required.",Toast.LENGTH_LONG).show()
            return
        }else if( salesid_secondry_1.contains(" ") || salesid_secondry_2.contains(" ") || salesid_secondry_3.contains(" ")){
            showAlert("Invalid secondary sales ID! No spaces allowed!")
            //Toast.makeText(this@RegisterActivity,"Sales ID is required.",Toast.LENGTH_LONG).show()
            return
        }else if( salesid == salesid_secondry_1 || salesid == salesid_secondry_2 || salesid == salesid_secondry_3){
            showAlert("Secondary sales ID already exists!")
            //Toast.makeText(this@RegisterActivity,"Sales ID is required.",Toast.LENGTH_LONG).show()
            return
        }else if( salesid_secondry_1.count() > 0 && (salesid_secondry_1 == salesid_secondry_2 || salesid_secondry_1 == salesid_secondry_3) ){
            showAlert("Secondary sales ID already exists!")
            //Toast.makeText(this@RegisterActivity,"Sales ID is required.",Toast.LENGTH_LONG).show()
            return
        }else if( salesid_secondry_2.count() > 0 && (salesid_secondry_2 == salesid_secondry_1 || salesid_secondry_2 == salesid_secondry_3) ){
            showAlert("Secondary sales ID already exists!")
            //Toast.makeText(this@RegisterActivity,"Sales ID is required.",Toast.LENGTH_LONG).show()
            return
        }else if( salesid_secondry_3.count() > 0 && (salesid_secondry_3 == salesid_secondry_1 || salesid_secondry_3 == salesid_secondry_2) ){
            showAlert("Secondary sales ID already exists!")
            //Toast.makeText(this@RegisterActivity,"Sales ID is required.",Toast.LENGTH_LONG).show()
            return
        }

        salelist.clear()
        if(salesid_secondry_1.isNotEmpty()){
            salelist.add(salesid_secondry_1)
        }
        if(salesid_secondry_2.isNotEmpty()){
            salelist.add(salesid_secondry_2)
        }
        if(salesid_secondry_3.isNotEmpty()){
            salelist.add(salesid_secondry_3)
        }


        var user: Register = Register()
        user.email=email
        user.firstName=fname
        user.lastName=lname
        user.mobileNo=mobilenumber
        user.salesId=salesid
        user.salesIdList=salelist
        user.userType=userType
        user.currentStatus=currentStatus
        user.organizationId=organizationId.toInt()
        user.userRole=userRole
        user.storeId=storeId
        user.domain = domainVAL
        Log.d("HH",user.storeId.toString())

        subscription.add(registerActivityVM.registerUser(user).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.visibility = View.VISIBLE }
            .doOnTerminate { progressBar.visibility = View.GONE }
            .doOnError { progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {



                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Success")
                    builder.setMessage("Your account is created successfully, please check your email for verification link to activate your account.")

                    // Set a positive button and its click listener on alert dialog
                    builder.setPositiveButton("Ok"){dialog, which ->
                        LoginActivity.startActivity(this@RegisterActivity)
                    }



                    // Finally, make the alert dialog using builder
                    val dialog: AlertDialog = builder.create()

                    // Display the alert dialog on app interface
                    dialog.show()







                } else {
                    showAlert(it.message)


                }
            }, {
                Log.d("====0======", it.stackTrace.toString())
                progressBar.visibility = View.GONE
               // showMessage(R.string.service_loading_fail)
            })

        )
    }

    private fun showAlert(message: String) {
        val dialogBuilder = AlertDialog.Builder(this)


        dialogBuilder.setMessage(message)
        dialogBuilder.setNegativeButton("Ok") { dialog, which ->

        }
        val alert = dialogBuilder.create()

        alert.setTitle("Error")

        alert.show()
    }

    fun checkingUnicode(text: String): Boolean {


        val regex = Pattern.compile("[$&+,:;=\\\\?#|/'<>^*()%\\n!]");

        if (regex.matcher(text).find()) {
            return true
        }

return false
//        val p = Pattern.compile("[\\p{Alpha}]*[\\p{Punct}][\\p{Alpha}]*")
//        val m = p.matcher(text)
//        val b = m.matches()
//
//
//        return b
    }






    private fun getStoreData(organizationId: String) {

        subscription.add(registerActivityVM.getStoreData(organizationId).subscribeOn(
            Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.visibility = View.VISIBLE }
            .doOnTerminate { progressBar.visibility = View.GONE }
            .doOnError { progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {

                 val storeDataObj=registerActivityVM.storeDataObj

                    spinner_view.visibility=View.VISIBLE

                    val subDataList= storeDataObj!!.response.data
                    val spinnerAdapter3 = CompanyAdapter3(this@RegisterActivity, subDataList)
                     edt_title_user_spinner2?.adapter = spinnerAdapter3


                    edt_title_user_spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            val storeName= subDataList.get(position).storeName

                            storeId =subDataList.get(position).id


                           // (layout_storeid.findViewById(R.id.register_feild_text) as EditText).text = storeName
                          //  val spinnerAdapter3 = MainActivity.CompanyAdapter3(this@MainActivity, dataList)
                          //  spinner_year?.adapter = spinnerAdapter3
                        }
                        override fun onNothingSelected(parent: AdapterView<*>) {
                        }
                    }


                } else {
                //    showMessage(R.string.service_loading_fail)
                }
            }, {
                Log.d("====0======", it.stackTrace.toString())
                progressBar.visibility = View.GONE
              //  showMessage(R.string.service_loading_fail)
            })

        )
    }
    class CompanyAdapter3(val context: Context, var peridesList: List<StoreMain>) : BaseAdapter() {


        val mInflater: LayoutInflater = LayoutInflater.from(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val vh: ItemRowHolder
            if (convertView == null) {
                view = mInflater.inflate(R.layout.spinner_store_cell_dropdown, parent, false)
                vh = ItemRowHolder(view)
                view?.tag = vh
            } else {
                view = convertView
                vh = view.tag as ItemRowHolder
            }

            // setting adapter item height programatically.

            val params = view.layoutParams
          //  params.height = 120
            //  view.layoutParams = params

            vh.label.text = peridesList.get(position).storeName
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
}
