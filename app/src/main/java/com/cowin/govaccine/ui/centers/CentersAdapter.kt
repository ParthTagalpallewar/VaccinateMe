package com.cowin.govaccine.ui.centers

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cowin.govaccine.R
import com.cowin.govaccine.data.models.roomModels.RoomCenters
import com.cowin.govaccine.utils.NUM_ROWS_FOR_AD
import com.cowin.govaccine.utils.templateAds.NativeTemplateStyle
import com.cowin.govaccine.utils.templateAds.TemplateView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.item_centers.view.*

class CentersAdapter(
    val centersList: List<Any>,
    val onCenterClickListener: CenterClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val NATIVE_ADS_VIEW = 0
        val CENTER_VIEW = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        return if (viewType == NATIVE_ADS_VIEW) {

            AdViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_native_template_view,
                    null,
                    false
                )
            )
        } else {
            CenterViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_centers, null, false)
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == CENTER_VIEW) {

            val roomCenter = centersList[position] as RoomCenters

            (holder as CenterViewHolder).bindData(roomCenter)

        } else {
            loadAds(holder, holder.itemView.context.getString(R.string.ad_unit_id_native))
        }
    }

    private fun loadAds(holder: RecyclerView.ViewHolder, adUnitKey: String) {
        val adLoader =
            AdLoader.Builder(holder.itemView.context, adUnitKey)
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
            return NATIVE_ADS_VIEW
        }

        return CENTER_VIEW


    }

    inner class AdViewHolder : RecyclerView.ViewHolder {

        lateinit var templateView: TemplateView

        constructor(view: View) : super(view) {
            templateView = view.findViewById(R.id.my_template)
        }
    }

    inner class CenterViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {

               itemView.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onCenterClickListener.onCenterCLickedListener((centersList[adapterPosition] as RoomCenters).center_id)
                    }
                }
        }


        fun bindData(center: RoomCenters) {
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

    interface CenterClickListener {
        fun onCenterCLickedListener(centerId: String)
    }

    override fun getItemCount(): Int {
        return centersList.size
    }


}
