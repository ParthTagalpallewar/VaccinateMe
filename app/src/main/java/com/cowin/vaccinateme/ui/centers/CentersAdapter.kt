package com.cowin.vaccinateme.ui.centers

import android.app.PendingIntent.getActivity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cowin.vaccinateme.R
import com.cowin.vaccinateme.data.models.roomModels.RoomCenters
import kotlinx.android.synthetic.main.item_centers.view.*

class CentersAdapter(val centersList:List<RoomCenters>,val onCenterClickListener: CenterClickListener) :RecyclerView.Adapter<CentersAdapter.CenterViewHolder>() {

    inner class CenterViewHolder(val itemView: View):RecyclerView.ViewHolder(itemView){

        init {
            itemView.setOnClickListener{
                if(adapterPosition != RecyclerView.NO_POSITION){
                    onCenterClickListener.onCenterCLickedListener(centersList[adapterPosition].center_id)
                }
            }
        }


        fun bindData(center:RoomCenters){
            itemView.item_centersName.text = center.name
            itemView.item_session_number.text = "${center.sessionCounts} Sessions"
            itemView.item_availability.text = "${center.totalAvailability} slots available"

            //TODO How to get the R.colors.teal_200 with no context?
            if (center.totalAvailability > 0) {
                itemView.item_availability.setTextColor(Color.parseColor("#FF03DAC5"))
            } else {
                itemView.item_availability.setTextColor(Color.parseColor("#de6e71"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_centers,null,false)
        return CenterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CenterViewHolder, position: Int) {
        val center = centersList[position]
        holder.bindData(center)
    }

    override fun getItemCount(): Int  = centersList.size

    interface CenterClickListener{
        fun onCenterCLickedListener(centerId:String)
    }
}