package com.fitscorp.sl.apps.di

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager

import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import io.reactivex.disposables.CompositeDisposable


open class BaseActivity : AppCompatActivity() {
    val REQUEST_EXTERNAL_STORAGE_WRITE_RECORD_AUDIO = 20

    open val subscription = CompositeDisposable()
    var toast: Toast? = null


    fun processAction(action: String,meta: String) {

        Toast.makeText(this, "Cliked", Toast.LENGTH_SHORT).show()
        if (action == "addtobill") {
            // onClickAddToBill(meta)
        }


    }



    fun showMessage(msg: Int) {
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast!!.show()
    }

    fun showMessage(msg: String) {
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast!!.show()
    }




    fun showAlert(message: String, title: String?, onClickOk: DialogInterface.OnClickListener) {
        val alertDialogBuilder = AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog)
        alertDialogBuilder
                .setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("OK", onClickOk)
        val alert = alertDialogBuilder.create()
        alert!!.show()
    }

    fun showAlertOneButton(title: String, message: String, buttonText: String, listener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(buttonText, listener).show()
    }

    fun showAlertOneButton(@StringRes title: Int, @StringRes message: Int, @StringRes buttonText: Int, listener: DialogInterface.OnClickListener) {
        showAlertOneButton(getString(title), getString(message), getString(buttonText), listener)
    }

    fun showAlertTwoButton(title: Int, message: Int, positiveButtonText: Int, negativeButtonText: Int, positive: DialogInterface.OnClickListener, negative: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, positive)
                .setNegativeButton(negativeButtonText, negative)
                .show()
    }

    fun requestPermissions(): Boolean {
        if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO
                    ),
                    REQUEST_EXTERNAL_STORAGE_WRITE_RECORD_AUDIO
            )
            Log.d("permissions", "requestStorageWrite")
            return false
        }
        return true
    }


    override fun onDestroy() {
        super.onDestroy()
        if (toast != null) {
            toast!!.cancel()
        }
    }

}