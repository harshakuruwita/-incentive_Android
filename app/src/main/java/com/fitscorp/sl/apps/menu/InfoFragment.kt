package com.fitscorp.sl.apps.menu

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.fitscorp.sl.apps.R
import kotlinx.android.synthetic.main.fragment_info.*
import org.json.JSONObject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"



class InfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_info, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("documentType", param2);
        Log.d("documentData", param3);


        if(param2 == "pdf"){
            param3 =   param3!!.replace("\\", "");
            param3 =   param3!!.replace("\"", "");
            val urlString = "http://drive.google.com/viewerng/viewer?embedded=true&url=" + param3;

            webview_info!!.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
            webview_info!!.loadUrl(urlString)
//            webview_info.setWebViewClient(object : WebViewClient() {
//                override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
//                    if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
//                        view.context.startActivity(
//                            Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                        )
//                        return true
//                    } else {
//                        return false
//                    }
//                }
//            })
            webview_info.setScrollbarFadingEnabled(true);
            webview_info.setHorizontalScrollBarEnabled(false);
            webview_info.getSettings().setJavaScriptEnabled(true);
            webview_info.setBackgroundColor(Color.WHITE);
        }else if(param2 == "url"){
            param3 =   param3!!.replace("\\", "");
            param3 =   param3!!.replace("\"", "");
            val urlString = param3;



            webview_info!!.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
            webview_info!!.loadUrl(urlString)

            webview_info.setBackgroundColor(Color.WHITE);
        }else if(param2 == "embed"){

            param3 =   param3!!.replace("\\", "");
            param3 =   param3!!.replace("\"", "");




            webview_info!!.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
            webview_info!!.loadData(param3, "text/html", "utf-8");
            webview_info!!.getSettings().setJavaScriptEnabled(true)
            webview_info!!.getSettings().setLoadWithOverviewMode(true);
            webview_info!!.getSettings().setUseWideViewPort(true);
            webview_info!!.setWebChromeClient(WebChromeClient())
            webview_info.setWebViewClient(object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                    if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                        view.context.startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        )
                        return true
                    } else {
                        return false
                    }
                }
            })
        }



        if(param1 == "" || param1 ==  null){
        nodata_infopage!!.visibility=View.VISIBLE
        }





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
        fun newInstance(param1: String, param2: String, param3: String) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                }
            }
    }
}

class Responsepareser(json: String) : JSONObject(json) {
    val type: String? = this.optString("type")
    val data = this.optJSONArray("data")
        ?.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } } // returns an array of JSONObject
        ?.map { Foo(it.toString()) } // transforms each JSONObject of the array into Foo
}

class Foo(json: String) : JSONObject(json) {
    val id = this.optInt("id")
    val title: String? = this.optString("title")
}