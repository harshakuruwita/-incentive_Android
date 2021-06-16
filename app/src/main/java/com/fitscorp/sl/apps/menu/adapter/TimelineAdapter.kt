package com.fitscorp.sl.apps.menu.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.common.inflate
import com.fitscorp.sl.apps.menu.data.DataArr
import kotlinx.android.synthetic.main.timeline_main_cell.view.*
import android.widget.ProgressBar
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.timeline_main_cell.view.progress_bar
import kotlinx.android.synthetic.main.timeline_main_cell.view.progress_bar2
import kotlinx.android.synthetic.main.timeline_main_cell.view.txt_dsc
import kotlinx.android.synthetic.main.timeline_main_cell.view.txt_dsc_persentage
import kotlinx.android.synthetic.main.timeline_main_cell.view.txt_dsc_persentage3
import kotlinx.android.synthetic.main.timeline_main_cell.view.txt_title
import kotlinx.android.synthetic.main.timeline_sub_cell.view.*


const val MAIN_CELL = 1
const val SUB_CELL = 2

class TimelineAdapter(val context: Context, val list: ArrayList<DataArr>) :
    RecyclerView.Adapter<TimelineAdapter.BaseHolder>() {



    fun release() {
        notifyDataSetChanged()
    }
    var onitemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
   //     fun onPaymentProcess(obj: Timeline)
    //    fun onProcessAction(action: String,meta: String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        when (viewType) {
            MAIN_CELL -> {
                return MainProgressView(parent)
            }
            else -> {
                return SubProgressView(parent)
            }
        }
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemViewType(position: Int): Int {

       if (list[position].specialPlace==null){
            return MAIN_CELL
        }else{
            return SUB_CELL
        }



    }

    open inner class BaseHolder(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(item: Any) = with(itemView) {
        }
        open fun release() {
        }
    }

    inner class MainProgressView(parent: ViewGroup) :

        BaseHolder(parent.inflate(com.fitscorp.sl.apps.R.layout.timeline_main_cell)) {

        @SuppressLint("SetTextI18n")
        override fun bind(item: Any) = with(itemView) {

           val data= item as DataArr

             if (data.shortName == " "){
                txt_title.text=data.longName
            } else if(data.shortName.isNotEmpty() ){

                 txt_title.text=data.longName + "(" + data.shortName + ")"
             }


            txt_dsc.text=data.MobileName

            if(data.measureType == "%"){
                txt_dsc_persentage.text=data.value1 +" " +  data.measureType
                txt_dsc_persentage3.text=data.value2.toString() + " "+ data.measureType
            } else if(data.measureType == "POINT"){
                txt_dsc_persentage.text= "Point" +" " + data.value1
                txt_dsc_persentage3.text= "Point" + " "+  data.value2.toString()
            }

            else{
                txt_dsc_persentage.text= data.measureType +" " + data.value1
                txt_dsc_persentage3.text= data.measureType + " "+  data.value2.toString()
            }



            txt_descbellow.text=data.TargetMobileName


            val colo1:String=data.color2
            val colo2:String=data.color1
            txt_dsc.setTextColor(Color.parseColor(colo2))
            txt_dsc_persentage.setTextColor(Color.parseColor(colo2))



            progress_bar.progressColor = Color.parseColor(colo2)


           //
            // progress_bar.radius=20
            if(data.total.toFloat()<2){
                progress_bar.progress= 4.0F
            }else{
                progress_bar.progress= data.total.toFloat()
            }

            if(data.target.toFloat()<2){
                progress_bar2.progress= 4.0F
            }else{
                progress_bar2.progress= data.target.toFloat()
            }




            txt_descbellow.setTextColor(Color.parseColor(colo1))
            txt_dsc_persentage3.setTextColor(Color.parseColor(colo1))
            progress_bar2.progressColor = Color.parseColor(colo1)





            return@with
        }
    }
    inner class SubProgressView(parent: ViewGroup) :

        BaseHolder(parent.inflate(R.layout.timeline_sub_cell)) {

        override fun bind(item: Any) = with(itemView) {

            val data= item as DataArr

            val colo1:String=data.color2
            val colo2:String=data.color1

            txt_dsc4.setTextColor(Color.parseColor(colo2))
            txt_dsc_persentage4.setTextColor(Color.parseColor(colo2))



            txt_descbellow4.setTextColor(Color.parseColor(colo1))
            txt_dsc_persentage34.setTextColor(Color.parseColor(colo1))



            txt_title4.text=data.longName
            txt_dsc4.text = data.MobileName
            txt_descbellow4.text = data.TargetMobileName
            txt_dsc_persentage4.text=data.value1
            txt_dsc_persentage34.text=data.value2.toString()
//
//            txt_position.text=data.longName
//            txt_dsc3.text=data.longName
            txt_main_persentage.text="#"+data.specialPlace
//target
           
          //  progress_bar24.radius=10
           // progress_bar24.progress= 50.0F
            progress_bar24.progressColor = Color.parseColor(colo1)

            if(data.target.toFloat()<2){
                progress_bar24.progress= 4.0F
            }else{
                progress_bar24.progress= data.target.toFloat()
            }


            if(data.total.toFloat()<2){
                progress_bar4.progress= 4.0F
            }else{
                progress_bar4.progress= data.total.toFloat()
            }

          //  progress_bar4.radius=10
           // progress_bar4.progress= 70.0F
            progress_bar4.progressColor = Color.parseColor(colo2)

            return@with
        }
    }





}
