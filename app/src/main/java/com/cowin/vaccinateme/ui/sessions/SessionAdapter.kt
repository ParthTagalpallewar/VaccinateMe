package com.cowin.vaccinateme.ui.centers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cowin.vaccinateme.R
import com.cowin.vaccinateme.data.models.roomModels.RoomCenters
import com.cowin.vaccinateme.data.models.roomModels.RoomSessions
import com.cowin.vaccinateme.utils.NUM_ROWS_FOR_AD
import com.cowin.vaccinateme.utils.templateAds.NativeTemplateStyle
import com.cowin.vaccinateme.utils.templateAds.TemplateView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.item_sessions.view.*
import java.text.SimpleDateFormat

class SessionAdapter(val sessionsList: ArrayList<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val NATIVE_ADS_VIEW = 0
        val CENTER_VIEW = 1
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == CentersAdapter.NATIVE_ADS_VIEW) {

            AdViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_native_template_view,
                    null,
                    false
                )
            )
        } else {
            CenterViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_sessions, null, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == CentersAdapter.CENTER_VIEW) {

            val roomCenter = sessionsList[position] as RoomSessions

            (holder as SessionAdapter.CenterViewHolder).bindData(roomCenter)

        } else {
            loadAds(holder)
        }
    }

    inner class AdViewHolder : RecyclerView.ViewHolder {

        lateinit var templateView: TemplateView

        constructor(view: View) : super(view) {
            templateView = view.findViewById(R.id.my_template)
        }
    }

    override fun getItemCount(): Int  = sessionsList.size

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

    private fun loadAds(holder: RecyclerView.ViewHolder) {
        val adLoader =
            AdLoader.Builder(holder.itemView.context, "ca-app-pub-3940256099942544/2247696110")
                .forNativeAd { nativeAd -> // Show the ad.
                    val styles = NativeTemplateStyle.Builder().build()
                    val template: TemplateView = (holder as AdViewHolder).templateView
                    template.setStyles(styles)
                    template.setNativeAd(nativeAd)
                }.build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)

        if (position % NUM_ROWS_FOR_AD == 0) {
            return CentersAdapter.NATIVE_ADS_VIEW
        }

        return CentersAdapter.CENTER_VIEW


    }
}