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
import com.fitscorp.sl.apps.menu.data.LeaderboardData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.leaderboard_cell.view.*
import kotlinx.android.synthetic.main.leaderboard_cell.view.main_bg
import kotlinx.android.synthetic.main.leaderboard_cell.view.txt_name
import kotlinx.android.synthetic.main.leaderboard_cell.view.txt_number
import kotlinx.android.synthetic.main.leaderboard_cell.view.txt_points
import kotlinx.android.synthetic.main.leaderboard_executive_cell.view.*
import java.text.DecimalFormat


class LeaderboardAapter(
    val context: Context,
    val items: ArrayList<LeaderboardData>,
    val salesID: String,
    val StorePrimaryId: Int,
    val firstPlace_colour: String,
    val leaderBoard_colour: String
) : RecyclerView.Adapter<ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.leaderboard_cell,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("XXXXXAAAASSSSS", StorePrimaryId.toString())
        Log.d("XXXXXAAALLLLLLL", items[position].StorePrimaryId.toString())

        if(salesID==items[position].userId || StorePrimaryId==items[position].StorePrimaryId ){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {



                val greenColorValue1 = Color.parseColor(firstPlace_colour)

                val gd = GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    intArrayOf(greenColorValue1, greenColorValue1)
                )
                gd.gradientType = GradientDrawable.LINEAR_GRADIENT
                gd.cornerRadius = 100f

                holder.mainBg.setBackgroundDrawable(gd)


                //  holder.mainBg.setBackgroundResource(R.drawable.leaderboard_yellow_bg);
            } else {
                holder.mainBg.setBackgroundResource(R.drawable.leaderboard_yellow_bg);
            }
        }else{


            val greenColorValue1 = Color.parseColor(leaderBoard_colour)

            val gd = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(greenColorValue1, greenColorValue1)
            )
            gd.gradientType = GradientDrawable.LINEAR_GRADIENT
            gd.cornerRadius = 100f

            holder.mainBg.setBackgroundDrawable(gd)

            // holder.mainBg.setBackgroundResource(R.drawable.leaderboard_button_bg)
        }

        val df = DecimalFormat("#.##")
        holder.tvAnimalType?.text = items[position].userId
        holder.txt_number?.text = items[position].position.toString()
        holder.txtpoints?.text =   df.format(items[position].point)

    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val txt_number = view.txt_number
    val tvAnimalType = view.txt_name
    val txtpoint_bottom=view.txt_name_bottom
    val txtpoints=view.txt_points

    val mainBg=view.main_bg

}
