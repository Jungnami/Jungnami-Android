package sopt_jungnami_android.jungnami

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import sopt_jungnami_android.jungnami.data.PartyDistrictLegistlatorListData

class PartyDistrictLegislatorListViewAdapter(private var legislatorItems : ArrayList<PartyDistrictLegistlatorListData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val legislatorView : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_legislator_rank_list, parent, false)
        return PartyDistrictLegislatorListViewHolder(legislatorView)
    }

    override fun getItemCount(): Int = legislatorItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    inner class PartyDistrictLegislatorListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val legislator_rvlayout: LinearLayout = itemView.findViewById(R.id.rv_item_legislator_background_ll) as LinearLayout
        val legislator_rank : TextView = itemView.findViewById(R.id.rv_item_legislator_rank_num_tv) as TextView
        val legislator_partycolor: CircleImageView = itemView.findViewById(R.id.rv_item_legislator_party_color_img_iv) as CircleImageView
        val legislator_profileIMG: ImageView = itemView.findViewById(R.id.rv_item_legislator_profile_img_iv) as ImageView
        val legislator_name: TextView = itemView.findViewById(R.id.rv_item_legislator_name_tv) as TextView
        val legislator_rankInAll: TextView = itemView.findViewById(R.id.rv_item_legislator_likable_district_rankinall_tv) as TextView
        val legislator_position: TextView = itemView.findViewById(R.id.rv_item_legislator_likable_position_tv) as TextView
        val legislator_votebtn: ImageView = itemView.findViewById(R.id.rv_item_legislator_vote_btn) as ImageView
    }
}