package com.fitscorp.sl.apps.menu


import android.content.Context
import android.app.AlertDialog
import android.content.Intent
import android.hardware.input.InputManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.fitscorp.sl.apps.App

import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.login.LoginActivity
import com.fitscorp.sl.apps.menu.data.Contact
import com.fitscorp.sl.apps.menu.vm.ContactVM
import com.fitscorp.sl.apps.register.RegisterActivityVM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_contact_us.*
import kotlinx.android.synthetic.main.fragment_contact_us.progressBar
import kotlinx.android.synthetic.main.fragment_contact_us.view.*
import javax.inject.Inject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ContactUsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    val subscription = CompositeDisposable()

    @Inject
    lateinit var registerActivityVM: ContactVM

    override fun onDestroy() {
        super.onDestroy()

        subscription.dispose()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.getInstance().appComponent.inject(this)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }







    }





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view=inflater.inflate(R.layout.fragment_contact_us, container, false)
        var  txt_title = view.findViewById(R.id.txt_title) as EditText
        val  txt_message_text_count = view.findViewById(R.id.txt_message_text_count) as TextView

        var  txt_message_content = view.findViewById(R.id.txt_message_content) as EditText
        val  txt_message_content_text_count = view.findViewById(R.id.txt_message_content_text_count) as TextView





        txt_title.setText("Incentive app issue");
        txt_message_text_count.setText("20/40");



        txt_title.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (count == 1) {
                    txt_title.clearFocus()

                }
                var countValue = txt_title?.getText().length.toString();
              //  var countValue = txt_title!!.length().toString();

                val c = countValue.plus("").plus("/40")
                txt_message_text_count.setText(c)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int
            ) {// TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {}
        })


        txt_message_content.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
               // var countValue = count.toString()
                var countValue = txt_message_content?.getText().length.toString();
                val c = countValue.plus(" ").plus("/300")
                txt_message_content_text_count.setText(c)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int
            ) {// TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {}
        })





            return view
    }

    private fun showAlert(message: String) {
        val dialogBuilder = AlertDialog.Builder(this.context)


        dialogBuilder.setMessage(message)
        dialogBuilder.setNegativeButton("Ok") { dialog, which ->

        }
        val alert = dialogBuilder.create()

        alert.setTitle("Error")

        alert.show()
    }

    private fun showAlert_two(message: String) {
        val dialogBuilder = AlertDialog.Builder(this.context)


        dialogBuilder.setMessage(message)
        dialogBuilder.setNegativeButton("Ok") { dialog, which ->

        }
        val alert = dialogBuilder.create()

        alert.setTitle("Success")

        alert.show()
    }

    public fun snedMessage() {

        if(txt_title.text.isEmpty())
        {
            showAlert("Title is required!")
           // Toast.makeText(context,"Title is required!",Toast.LENGTH_LONG).show()
            return
        }

        if(txt_message_content.text.isEmpty())
        {
            showAlert("Message is required!")
           // Toast.makeText(context,"Message is required!",Toast.LENGTH_LONG).show()
            return
        }

        var data: Contact = Contact()
        data.subject = view!!.txt_title.text.toString()
        data.message = view!!.txt_message_content.text.toString()
        data.isFromDashboard = true;



        subscription.add(registerActivityVM.sendMessage(data).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressBar.visibility = View.VISIBLE }
            .doOnTerminate { progressBar.visibility = View.GONE }
            .doOnError { progressBar.visibility = View.GONE }
            .subscribe({
                if (it.isSuccess) {

                   txt_title.setText("Incentive app issue")
                   txt_message_content.setText("")
                    showAlert_two("Message successfully sent.")
                   // Toast.makeText(context,"Message sent to server",Toast.LENGTH_LONG).show()
                }
            }, {
                progressBar.visibility = View.GONE

            })

        )
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }



    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactUsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}
