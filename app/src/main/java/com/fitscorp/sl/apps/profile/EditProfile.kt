package com.fitscorp.sl.apps.profile

import android.app.Activity
import android.content.Intent
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.fitscorp.sl.apps.App
import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.di.BaseActivity
import com.fitscorp.sl.apps.home.model.UserAuthModel
import com.fitscorp.sl.apps.home.model.UserEditModel
import com.fitscorp.sl.apps.login.LoginActivityVM
import com.fitscorp.sl.apps.login.LoginUserMainResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.skyhope.materialtagview.interfaces.TagItemListener
import com.skyhope.materialtagview.model.TagModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.btn_backImgBtn
import kotlinx.android.synthetic.main.activity_edit_profile.progressBar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

import javax.inject.Inject


class EditProfile : BaseActivity() {
    var salelistid=ArrayList<String>()

    @Inject
    lateinit var editProfileVM: EditProfileVM
    companion object {
        fun startActivity(context: Activity,data:String) {
            val intent = Intent(context, EditProfile::class.java)
            intent.putExtra("userdata",data)
            context.startActivity(intent)


        }
    }

    private fun readExtra():String{
        lateinit var dataUser:String

        val bundle=intent.extras

        dataUser= bundle?.getSerializable("userdata") as String


        return dataUser
    }

    private fun readExtraview():String{
        lateinit var dataUserview:String

        val bundle=intent.extras

        dataUserview= bundle?.getSerializable("userdata") as String


        return dataUserview
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        App.getInstance().appComponent.inject(this)


      //  val greenColorValue1 = Color.parseColor(grenColorValue1)
     //   val greenColorValue2 = Color.parseColor(grenColorValue2)

        //  val top_main_menu = findViewById<View>(R.id.top_main_menu)
//
//        val gd = GradientDrawable(
//            GradientDrawable.Orientation.LEFT_RIGHT,
//            intArrayOf(greenColorValue2, greenColorValue1)
//        )
//        gd.gradientType = GradientDrawable.LINEAR_GRADIENT
//        gd.cornerRadius = 0f
//
//        top_main_menu.setBackgroundDrawable(gd)

//        tag_view_edit.setHint("Secondary Sales ID (Press space to add)")
//        //tagView.addTagSeparator(TagSeparator.AT_SEPARATOR);
//
//        tag_view_edit.addTagLimit(5)
////        val tagList = arrayOf("")
////        tag_view_edit.setTagList(*tagList)
//        tag_view_edit.initTagListener(object : TagItemListener {
//            override fun onGetRemovedItem(model: TagModel?) {
//                if(salelistid.isNotEmpty()) {
//                    salelistid.removeAt(salelistid.size - 1)
//                }
//            }
//
//            override fun onGetAddedItem(tagModel: TagModel?) {
//                Log.d("8878",tagModel?.tagText)
//
//                if(checkingUnicode(tagModel!!.tagText) || tagModel!!.tagText == ""){
//                    showAlert("Invalid sales ID! Enter only alphanumeric value without spaces!")
//                }else{
//                    salelistid.add(tagModel!!.tagText)
//                }
//
//
//            }
//        })


        App.getInstance().appComponent.inject(this)
        val strData=readExtraview()
        val gson = Gson()
        val type = object : TypeToken<LoginUserMainResponse>() {}.type
        val dataLogin=gson.fromJson<LoginUserMainResponse>(strData, type)

        val user=dataLogin.response.data.user

        txt_firstName.setText(user.firstName)
        txt_lastName.setText(user.lastName)
        txt_mobile.setText(user.mobileNo)


        if(dataLogin.response.data.salesIds.isNotEmpty()){
            var salelist=dataLogin.response.data.salesIds
            register_feild_text_1.setText(salelist[0].toString())

            if(salelist.count() > 1){
                register_feild_text_2.setText(salelist[1].toString())
            }
            if(salelist.count() > 2){
                register_feild_text_3.setText(salelist[2].toString())
            }



        }else{

        }


        btn_edit.setOnClickListener {
            submitEdit()
        }

        btn_backImgBtn.setOnClickListener {

            finish()
        }



    }

    fun checkingUnicode(text: String): Boolean {


        val regex = Pattern.compile("[$&+,:;=\\\\?#|/'<>^*()%\\n!]");

        if (regex.matcher(text).find()) {
            return true
        }

        return false

    }


    private fun submitEdit(){

        val salesid_secondry_1= register_feild_text_1.text.toString()
        val salesid_secondry_2= register_feild_text_2.text.toString()
        val salesid_secondry_3= register_feild_text_3.text.toString()

        var firstName = txt_firstName.text.toString()
        var lastName = txt_lastName.text.toString()
        var mobileNumber = txt_mobile.text.toString()

        if(txt_firstName.text.isEmpty() && txt_lastName.text.isEmpty() && txt_mobile.text.isEmpty())
        {
            showAlert("First name or Last name or mobile number is required!")
           // Toast.makeText(this,"First name or Last name is required", Toast.LENGTH_LONG).show()
            return
        }else if( salesid_secondry_1.contains(" ") || salesid_secondry_2.contains(" ") || salesid_secondry_3.contains(" ")){
            showAlert("Invalid secondary sales ID! No spaces allowed!")
            //Toast.makeText(this@RegisterActivity,"Sales ID is required.",Toast.LENGTH_LONG).show()
            return
        }


        else if(checkingUnicode(salesid_secondry_1) || checkingUnicode(salesid_secondry_2) || checkingUnicode(salesid_secondry_3)){
            showAlert("Invalid secondary sales ID! Enter only alphanumeric value or email without spaces!")
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
        }else if( mobileNumber.contains(" ") ){


                showAlert("Invalid mobile number! No spaces allowed!")
                //Toast.makeText(this@RegisterActivity,"Sales ID is required.",Toast.LENGTH_LONG).show()
                return
        }else if(checkingUnicode(mobileNumber) ){


                showAlert("Invalid mobile number! Enter only numeric value without spaces!")
                //Toast.makeText(this@RegisterActivity,"Sales ID is required.",Toast.LENGTH_LONG).show()
                return


        }

        salelistid.clear()
        if(salesid_secondry_1.isNotEmpty()){
            salelistid.add(salesid_secondry_1)
        }
        if(salesid_secondry_2.isNotEmpty()){
            salelistid.add(salesid_secondry_2)
        }
        if(salesid_secondry_3.isNotEmpty()){
            salelistid.add(salesid_secondry_3)
        }









        var p: UserEditModel = UserEditModel()
        p.firstName = firstName
        p.lastName = lastName
        p.mobileNo = mobileNumber
        p.salesIdList = salelistid


        editProfile(p)

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

    private fun editProfile2() {

        subscription.add(editProfileVM.getLoginUser().subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.visibility = View.VISIBLE }
            .doOnTerminate { progressBar.visibility = View.GONE }
            .doOnError { progressBar.visibility = View.GONE }
            .subscribe({
                Toast.makeText(this,"Profile updated.",Toast.LENGTH_LONG).show()
                finish()

            }, {
                Log.d("====0======", it.stackTrace.toString())
                progressBar.visibility = View.GONE
                showMessage(R.string.service_loading_fail)
            })

        )
    }
    private fun editProfile(p: UserEditModel) {

        subscription.add(editProfileVM.editProfile(p).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.visibility = View.VISIBLE }
            .doOnTerminate { progressBar.visibility = View.GONE }
            .doOnError { progressBar.visibility = View.GONE }
            .subscribe({

                if (it.message.toString() == "200") {

                    editProfile2()
                 //   editProfileVM.getLoginUser()
                    // finish()

                } else if (it.message.toString() == "400") {

                    showAlert("Invalid update!")
                    //   Toast.makeText(this,"Invalid update!",Toast.LENGTH_LONG).show()
                }else {
                    showAlert("Secondary Sales ID already exists!")
                    //   Toast.makeText(this,"Secondary Sales ID already exists!",Toast.LENGTH_LONG).show()
                }

            }, {
                Log.d("====0======", it.stackTrace.toString())
                progressBar.visibility = View.GONE
               // showMessage(R.string.service_loading_fail)
            })

        )
    }


}
