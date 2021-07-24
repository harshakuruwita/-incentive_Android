package com.fitscorp.sl.apps.login

import android.app.Activity
import android.content.Intent
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.fitscorp.sl.apps.App
import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.Resend_Code
import com.fitscorp.sl.apps.di.BaseActivity
import com.fitscorp.sl.apps.home.HomeMainResponse
import com.fitscorp.sl.apps.home.HomeVM
import com.fitscorp.sl.apps.home.MainActivity
import com.fitscorp.sl.apps.home.model.UserAuthModel
import com.fitscorp.sl.apps.register.RegisterActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class LoginActivity : BaseActivity() {


    @Inject
    lateinit var loginVM: LoginActivityVM
    @Inject
    lateinit var homeVM: HomeVM

    companion object {
        fun startActivity(context: Activity) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        App.getInstance().appComponent.inject(this)

        btn_signup.setOnClickListener {
            submitLogin()
        }
        txt_donthve_signupnow.setOnClickListener {
            RegisterActivity.startActivity(this)
        }
        txt_donthve_account1.setOnClickListener {
            RegisterActivity.startActivity(this)
        }

       txt_forgot_password.setOnClickListener {
           FrogotPassword.startActivity(this)
        }

        txt_donthve_resend_code.setOnClickListener {
            Resend_Code.startActivity(this)
        }

    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun submitLogin(){

        if(txt_email.text.isEmpty() && txt_email.text.isEmpty())
        {
            showAlert("email and password required!")

            return
        }
        else  if(!isEmailValid(txt_email.text.toString()))
        {
            showAlert("Invalid email!")

            return
        }
        else if(txt_password.text.isEmpty())
        {
            showAlert("Password is required!")

            return
        }

        var email=txt_email.text.toString()
        var pass=txt_password.text.toString()




        var p: UserAuthModel = UserAuthModel()
        p.Username = email
        p.Password = pass

        getLoginToken(p)

    }

    private fun getLoginToken(p: UserAuthModel) {

        subscription.add(loginVM.getUserToken(p).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.visibility = View.VISIBLE }
            .doOnTerminate { progressBar.visibility = View.GONE }
            .doOnError { progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {

                      getLoginUser()

                } else {
                    if(it.message == "400") {
                        showAlert("Wrong email or password!")

                    }else{
                        showAlert("internal server error.")

                    }
                }
            }, {
                Log.d("====0======", it.stackTrace.toString())
                progressBar.visibility = View.GONE

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

    private fun getLoginUser() {

        subscription.add(loginVM.getLoginUser().subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.visibility = View.VISIBLE }
            .doOnTerminate { progressBar.visibility = View.GONE }
            .doOnError { progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {
//
                   val data= loginVM.loginUserMainResponse
                    if(data!!.response.data.user.userRole.equals("SALES_REP")||data!!.response.data.user.userRole.equals("STORE_MANAGER")||data!!.response.data.user.userRole.equals("EXECUTIVE")||data!!.response.data.user.userRole.equals("HEAD_OFFICE")){

                        getAppFilters(data!!.response.data.user.userRole);


                    }else {
                        val resError=   it.message.toString()
                      //  getAppFilters(data!!.response.data.user.userRole);
                        showAlert("User doesn't have permission to login!")
                    }


                } else {

                 val message=   it.message.toString()

                    if(message == "Bad Request"){
                        showAlert("User doesn't have permission to login!")
                    }else{
                        showAlert(message)
                    }


                }
            }, {
                Log.d("====1======", it.stackTrace.toString())
                progressBar.visibility = View.GONE

            })

        )
//
    }

    private fun getAppFilters( userRole:String) {

        subscription.add(homeVM.getAppFilters().subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.visibility = View.VISIBLE }
            .doOnTerminate { progressBar.visibility = View.GONE }
            .doOnError { progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {
                    if(userRole.equals("HEAD_OFFICE")){
                        Log.d("9800","ABX")
                        getExecutiveFilters();
                    }else{

                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("fromActivity", "splash")
                        startActivity(intent)
                        finish()
                    }

                } else { showAlert("No incentive to show for this user!")}
            }, {

                progressBar.visibility = View.GONE

            })
        )
    }

    private fun getExecutiveFilters() {

        subscription.add(homeVM.getExecutiveFilter().subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.visibility = View.VISIBLE }
            .doOnTerminate { progressBar.visibility = View.GONE }
            .doOnError { progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("fromActivity", "splash")
                    startActivity(intent)
                    finish()
                } else { showAlert("Error updating executive region!")}
            }, {

                progressBar.visibility = View.GONE

            })
        )
    }


}