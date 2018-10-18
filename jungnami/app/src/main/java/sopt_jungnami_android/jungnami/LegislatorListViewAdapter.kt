package sopt_jungnami_android.jungnami

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import sopt_jungnami_android.jungnami.data.PartyDistrictLegistlatorListData
import sopt_jungnami_android.jungnami.legislator.LegislatorPageActivity

class LegislatorListViewAdapter(var ctx: Context,
                                             var legislatorItems: ArrayList<PartyDistrictLegistlatorListData>,
                                             var likeableFlage: Int) : RecyclerView.Adapter<LegislatorListViewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_legislator_rank_list, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = legislatorItems.size

    fun addItem(dataList : ArrayList<PartyDistrictLegistlatorListData>){
        this.legislatorItems.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if ((position) % 2 == 0) {
            holder.legislator_rvlayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
        } else {
            holder.legislator_rvlayout.setBackgroundColor(Color.parseColor("#F9F9FB"))
        }
        when (legislatorItems[position].party_name) {
            "더불어민주당" -> {
                holder.legislator_partycolor.setImageResource(R.color.Blue)
            }
            "자유한국당" -> {
                holder.legislator_partycolor.setImageResource(R.color.Red)
            }
            "바른미래당" -> {
                holder.legislator_partycolor.setImageResource(R.color.Mint)
            }
            "정의당" -> {
                holder.legislator_partycolor.setImageResource(R.color.Yellow)
            }
            "민중당" -> {
                holder.legislator_partycolor.setImageResource(R.color.Orange)
            }
            "대한애국당" -> {
                holder.legislator_partycolor.setImageResource(R.color.Navy)
            }
            "민주평화당" -> {
                holder.legislator_partycolor.setImageResource(R.color.Green)
            }
            "무소속" -> {
                holder.legislator_partycolor.setImageResource(R.color.Gray)
            }
        }

        Glide.with(ctx).load(legislatorItems[position].imgurl).into(holder.legislator_profileIMG)

        holder.legislator_name.text = legislatorItems[position].name

        holder.legislator_rank.text = legislatorItems[position].rank
        holder.legislator_rankInAll.text = legislatorItems[position].rankInAll
        holder.legislator_position.text = legislatorItems[position].position
        // 호감, 비호감 탭 시 이미지 바꾸기. when문 사용
        if(likeableFlage == 1){
            holder.legislator_votebtn.setImageResource(R.drawable.main_good_btn_blue)
        }else{
            holder.legislator_votebtn.setImageResource(R.drawable.main_bad_btn_red)
        }

        holder.legislator_votebtn.setOnClickListener {
            val dialog : Dialog = LegislatorListVoteAgreeDialog(ctx,likeableFlage, legislatorItems[position].id)
            dialog.show()
        }
        holder.legislator_btn.setOnClickListener {
            ctx.startActivity<LegislatorPageActivity>("l_id" to legislatorItems[position].id)
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val legislator_rvlayout: RelativeLayout = itemView!!.findViewById(R.id.rv_item_legislator_background_ll) as RelativeLayout
        val legislator_rank: TextView = itemView!!.findViewById(R.id.rv_item_legislator_rank_num_tv) as TextView
        val legislator_partycolor: CircleImageView = itemView!!.findViewById(R.id.rv_item_legislator_party_color_img_iv) as CircleImageView
        val legislator_profileIMG: CircleImageView = itemView!!.findViewById(R.id.rv_item_legislator_profile_img_iv) as CircleImageView
        val legislator_name: TextView = itemView!!.findViewById(R.id.rv_item_legislator_name_tv)
        val legislator_rankInAll: TextView = itemView!!.findViewById(R.id.rv_item_legislator_likable_district_rankinall_tv) as TextView
        val legislator_position: TextView = itemView!!.findViewById(R.id.rv_item_legislator_likable_position_tv) as TextView
        val legislator_votebtn: ImageView = itemView!!.findViewById(R.id.rv_item_legislator_likable_vote_btn) as ImageView
        val legislator_btn : RelativeLayout = itemView!!.findViewById(R.id.rv_item_legislator_btn) as RelativeLayout
    }
}

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        val view: View = LayoutInflater.
//                from(context).inflate(R.layout.rv_item_legislator_rank_list, parent, false)
//        view.setOnClickListener(onItemClick)
//        return Holder(view)
//    }
//    override fun getItemCount(): Int {
//        return legislatorItems.size
//    }
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//        var partyDistrictLegislatorListViewHolder: PartyDistrictLegislatorListViewAdapter.Holder = holder as PartyDistrictLegislatorListViewAdapter.Holder
//
//        if ((position) % 2 == 0) {
//            partyDistrictLegislatorListViewHolder.legislator_rvlayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
//        } else {
//            partyDistrictLegislatorListViewHolder.legislator_rvlayout.setBackgroundColor(Color.parseColor("#F9F9FB"))
//        }
//
//        when (legislatorItems[position].party_name) {
//            "더불어민주당" -> {
//                partyDistrictLegislatorListViewHolder.legislator_partycolor.setImageResource(R.color.Blue)
//            }
//            "자유한국당" -> {
//                partyDistrictLegislatorListViewHolder.legislator_partycolor.setImageResource(R.color.Red)
//            }
//            "바른미래당" -> {
//                partyDistrictLegislatorListViewHolder.legislator_partycolor.setImageResource(R.color.Mint)
//            }
//            "정의당" -> {
//                partyDistrictLegislatorListViewHolder.legislator_partycolor.setImageResource(R.color.Yellow)
//            }
//            "민중당" -> {
//                partyDistrictLegislatorListViewHolder.legislator_partycolor.setImageResource(R.color.Orange)
//            }
//            "대한애국당" -> {
//                partyDistrictLegislatorListViewHolder.legislator_partycolor.setImageResource(R.color.Navy)
//            }
//            "민주평화당" -> {
//                partyDistrictLegislatorListViewHolder.legislator_partycolor.setImageResource(R.color.Green)
//            }
//            "무소속" -> {
//                partyDistrictLegislatorListViewHolder.legislator_partycolor.setImageResource(R.color.Gray)
//            }
//        }
//
//        Glide.with(context!!).load(legislatorItems[position].imgurl).into(partyDistrictLegislatorListViewHolder.legislator_profileIMG)
//
//        partyDistrictLegislatorListViewHolder.legislator_name.text = legislatorItems[position].name
//
//        partyDistrictLegislatorListViewHolder.legislator_rank.text = legislatorItems[position].rank
//        partyDistrictLegislatorListViewHolder.legislator_rankInAll.text = legislatorItems[position].rankInAll
//        partyDistrictLegislatorListViewHolder.legislator_position.text = legislatorItems[position].position
//        // 호감, 비호감 탭 시 이미지 바꾸기. when문 사용
//        if(likeableFlage == 1){
//            partyDistrictLegislatorListViewHolder.legislator_votebtn.setImageResource(R.drawable.main_good_btn_blue)
//        }else{
//            partyDistrictLegislatorListViewHolder.legislator_votebtn.setImageResource(R.drawable.main_bad_btn_red)
//        }
//
//        holder.legislator_votebtn.setOnClickListener {
//            val dialog : Dialog = LegislatorListVoteAgreeDialog(context!!,likeableFlage, legislatorItems[position].id)
//            dialog.show()
//        }
//    }