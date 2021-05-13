package com.cowin.vaccinateme.ui.centers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cowin.vaccinateme.R
import com.cowin.vaccinateme.data.models.roomModels.RoomCenters
import com.cowin.vaccinateme.data.models.roomModels.RoomSessions
import kotlinx.android.synthetic.main.item_centers.view.*
import kotlinx.android.synthetic.main.item_centers.view.item_centersName
import kotlinx.android.synthetic.main.item_sessions.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SessionAdapter(val sessionsList:List<RoomSessions>) :RecyclerView.Adapter<SessionAdapter.CenterViewHolder>() {

    inner class CenterViewHolder(val itemView: View):RecyclerView.ViewHolder(itemView){

        fun bindData(center:RoomSessions){
            itemView.itemSession_tv_capacity.text = center.available_capacity
            itemView.itemSession_tv_vaccine.text = center.vaccine
            itemView.itemSession_tv_minAge.text = center.min_age_limit

            val singleDate = center.date.split("-")
            val date = SimpleDateFormat("mm-dd-yyyy").parse(center.date)
            itemView.itemSession_tv_year.text = singleDate[2]
            itemView.itemSession_tv_date.text = singleDate[0]
            itemView.itemSession_tv_month.text = SimpleDateFormat("MMM").format(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sessions,null,false)
        return CenterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CenterViewHolder, position: Int) {
        val center = sessionsList[position]
        holder.bindData(center)
    }

    override fun getItemCount(): Int  = sessionsList.size


}