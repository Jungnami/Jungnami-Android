package sopt_jungnami_android.jungnami.rank

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.toast
import sopt_jungnami_android.jungnami.MainActivity
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.RankItemData

class UnlikeableRankRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<RankItemData>) : RecyclerView.Adapter<UnlikeableRankRecyclerViewAdapter.Holder>() {
    lateinit var onItemClick : View.OnClickListener

    fun setOnItemClickListener(l : View.OnClickListener){
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_unlikeable_tab_rank, parent, false)
        view.setOnClickListener(onItemClick)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size
    fun addItem(dataList : ArrayList<RankItemData>){
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val rank_number = dataList[position].ranking
        when (rank_number){
            "1" -> holder.rank_number_cover_img.setImageResource(R.drawable.main_bomb_red)
            "2" -> holder.rank_number_cover_img.setImageResource(R.drawable.main_bomb_orange)
            "3" -> holder.rank_number_cover_img.setImageResource(R.drawable.main_bomb_yellow)
            else -> {
                holder.rank_number_cover_img.visibility = View.GONE
                holder.rank_number.setTextColor(Color.parseColor("#36C5F1"))
            }
        }
        holder.rank_number.text = rank_number

        if (dataList[position].profileimg != "0"){
            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.legislator_noneprofile_woman_image)
            requestOptions.error(R.drawable.legislator_noneprofile_woman_image)
            requestOptions.override(150)
            Glide.with(ctx)
                    .setDefaultRequestOptions(requestOptions)
                    .load(dataList[position].profileimg)
                    .thumbnail(0.1f)
                    .into(holder.picture)
        }

        val dp = ctx.resources.displayMetrics.density
        holder.vote_bar.layoutParams.width = (200 * dataList[position].width * dp).toInt()
        holder.name.text = dataList[position].l_name
        holder.party_name.text = " _${dataList[position].party_name}"
        val vote_count : String = String.format("%,d", dataList[position].score)
        holder.vote_count.text = "${vote_count}표"

        holder.vote_btn.setOnClickListener {
            val dialog : Dialog = VoteAgreeDialog(ctx,0, dataList[position].l_id)
            dialog.show()
        }

//        when (dataList[position].is_voted){
//            0 -> holder.is_voted_image.setImageResource(R.drawable.legislatorpage_good_btn_gray)
//            1 -> holder.is_voted_image.setImageResource(R.drawable.legislatorpage_good_btn_blue)
//        }
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val rank_number_cover_img : ImageView = itemView.findViewById(R.id.unlikeable_tab_rv_item_ranking_img_iv) as ImageView
        val rank_number : TextView = itemView.findViewById(R.id.unlikeable_tab_rv_item_ranking_number_tv) as TextView
        val picture : ImageView = itemView.findViewById(R.id.unlikeable_tab_rv_item_picture_iv) as ImageView
        val vote_bar : RelativeLayout = itemView.findViewById(R.id.unlikeable_tab_rv_item_vote_count_bar) as RelativeLayout
        val name : TextView = itemView.findViewById(R.id.unlikeable_tab_rv_item_name_tv) as TextView
        val party_name : TextView = itemView.findViewById(R.id.unlikeable_tab_rv_item_party_tv) as  TextView
        val vote_count : TextView = itemView.findViewById(R.id.unlikeable_tab_rv_item_vote_count_tv) as TextView
        val vote_btn : RelativeLayout = itemView.findViewById(R.id.unlikeable_tab_rv_item_isvote_btn) as RelativeLayout
    }
}