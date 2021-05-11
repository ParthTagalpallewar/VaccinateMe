package com.cowin.vaccinateme.ui.centers

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
            itemView.item_session_number.text = center.sessionCounts.toString()
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