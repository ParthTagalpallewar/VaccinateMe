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

class SessionAdapter(val sessionsList:List<RoomSessions>) :RecyclerView.Adapter<SessionAdapter.CenterViewHolder>() {

    inner class CenterViewHolder(val itemView: View):RecyclerView.ViewHolder(itemView){

        fun bindData(center:RoomSessions){
            itemView.itemSession_tv_capacity.text = center.available_capacity
            itemView.itemSession_tv_vaccine.text = center.vaccine
            itemView.itemSession_tv_minAge.text = center.min_age_limit

            val date = center.date
            val singleDate = date.split("-")
            itemView.itemSession_tv_year.text = singleDate[2]
            itemView.itemSession_tv_date.text = singleDate[0]
            itemView.itemSession_tv_month.text = singleDate[1]
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