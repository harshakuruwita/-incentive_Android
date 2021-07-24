package com.fitscorp.sl.apps.menu.adapter



import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.home.model.ExecutiveLeaderBordResponse
import com.fitscorp.sl.apps.menu.data.LeaderboardData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.leaderboard_cell.view.*
import kotlinx.android.synthetic.main.leaderboard_cell.view.main_bg
import kotlinx.android.synthetic.main.leaderboard_cell.view.txt_name
import kotlinx.android.synthetic.main.leaderboard_cell.view.txt_number
import kotlinx.android.synthetic.main.leaderboard_cell.view.txt_points
import kotlinx.android.synthetic.main.leaderboard_executive_cell.view.*
import java.text.DecimalFormat


class LeaderboardExecutiveAapter(
    val context: Context,
    val items: ArrayList<ExecutiveLeaderBordResponse>,
    val salesID: String,
    val StorePrimaryId: Int,
    val firstPlace_colour: String,
    val leaderBoard_colour: String,


    val selectedTab: Int,
    val storeName: String,
) : RecyclerView.Adapter<ViewHolderExecutive>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderExecutive {

        return ViewHolderExecutive(
            LayoutInflater.from(context).inflate(
                R.layout.leaderboard_executive_cell,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolderExecutive, position: Int) {





            val greenColorValue1 = Color.parseColor(leaderBoard_colour)

            val gd = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(greenColorValue1, greenColorValue1)
            )
            gd.gradientType = GradientDrawable.LINEAR_GRADIENT
            gd.cornerRadius = 100f

            holder.mainBg.setBackgroundDrawable(gd)
Log.d("Selected tab id",selectedTab.toString());
        if(selectedTab == 0 || selectedTab == 1){
            val df = DecimalFormat("#.##")

            holder.tvAnimalType?.text = items[position].name.toString()
            holder.txt_number?.text = items[position].position.toString()
            holder.txtpoint_bottom?.text = items[position].topData.name
            holder.txtpoints?.text =   items[position].allPoints.toString()
            holder.leaderPoint?.text =   items[position].topData.point.toString()
        }else if(selectedTab == 2){
            val df = DecimalFormat("#.##")
            holder.txtpoint_bottom?.visibility = View.VISIBLE;
            holder.leaderPoint?.visibility = View.VISIBLE;
            holder.tvAnimalType?.text = storeName
            holder.txt_number?.text = items[position].position.toString()
            holder.txtpoint_bottom?.text = items[position].topData.name
            holder.txtpoints?.text =   items[position].allPoints.toString()
            holder.leaderPoint?.text =   ""
        }



        else{
            if(items[position].topData.point > 0){
                val df = DecimalFormat("#.##")
                holder.txtpoint_bottom?.visibility = View.VISIBLE;
                holder.leaderPoint?.visibility = View.VISIBLE;
                holder.tvAnimalType?.text = items[position].name.toString()
                holder.txt_number?.text = items[position].position.toString()
                holder.txtpoint_bottom?.text = items[position].topData.name
                holder.txtpoints?.text =   items[position].allPoints.toString()
                holder.leaderPoint?.text =   items[position].topData.point.toString()
            }else{
                val df = DecimalFormat("#.##")
                holder.tvAnimalType?.text = items[position].name.toString()
                holder.txt_number?.text = items[position].position.toString()
                holder.txtpoint_bottom?.visibility = View.GONE;
                holder.txtpoints?.text =   items[position].allPoints.toString()
                holder.leaderPoint?.visibility = View.GONE;
            }
        }






    }

    override fun getItemCount(): Int {
        return items.size
    }

}


class ViewHolderExecutive(view: View) : RecyclerView.ViewHolder(view) {
    val txt_number = view.txt_number
    val tvAnimalType = view.txt_name
    val txtpoint_bottom=view.txt_name_bottom
    val txtpoints=view.txt_points
    val leaderPoint = view.txt_posistion_points
    val mainBg=view.main_bg

}