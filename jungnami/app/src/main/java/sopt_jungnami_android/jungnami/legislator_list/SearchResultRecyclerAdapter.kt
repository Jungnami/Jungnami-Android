package sopt_jungnami_android.jungnami.legislator_list

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
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.RankingSearchLegislatorData

class SearchResultRecyclerAdapter (private var legislatorResultItems : ArrayList<RankingSearchLegislatorData>, private var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mainView : View = LayoutInflater.from(parent!!.context).inflate(R.layout.rv_item_search_result, parent, false)
        return SearchResultRecyclerAdapter.SearchResultRecyclerViewHolder(mainView)
    }

    override fun getItemCount(): Int {
        return legislatorResultItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var searchResultRecyclerViewHolder : SearchResultRecyclerViewHolder = holder as SearchResultRecyclerViewHolder

        if (position % 2 == 0) {

            searchResultRecyclerViewHolder.legislatorBackgroud.setBackgroundColor(Color.parseColor("#FFFFFF"))

        }else {
            searchResultRecyclerViewHolder.legislatorBackgroud.setBackgroundColor(Color.parseColor("#F9F9FB"))
        }

        when (legislatorResultItems[position].party) {
            "더불어민주당" -> {
                searchResultRecyclerViewHolder.legislatorPartyBackgroud.setImageResource(R.color.Blue)
            }
            "자유한국당" -> {
                searchResultRecyclerViewHolder.legislatorPartyBackgroud.setImageResource(R.color.Red)
            }
            "바른미래당" -> {
                searchResultRecyclerViewHolder.legislatorPartyBackgroud.setImageResource(R.color.Mint)
            }
            "정의당" -> {
                searchResultRecyclerViewHolder.legislatorPartyBackgroud.setImageResource(R.color.Yellow)
            }
            "민중당"-> {
                searchResultRecyclerViewHolder.legislatorPartyBackgroud.setImageResource(R.color.Orange)
            }
            "대한애국당" -> {
                searchResultRecyclerViewHolder.legislatorPartyBackgroud.setImageResource(R.color.Navy)
            }
            "민주평화당" -> {
                searchResultRecyclerViewHolder.legislatorPartyBackgroud.setImageResource(R.color.Green)
            }
            "무소속" -> {
                searchResultRecyclerViewHolder.legislatorPartyBackgroud.setImageResource(R.color.Gray)
            }

        }

        Glide.with(context)
                .load(legislatorResultItems[position].imgurl)
                .into(holder.legislatorImage)

        searchResultRecyclerViewHolder.legislatorIndex.text = (position+1).toString()
        searchResultRecyclerViewHolder.legislatorName.text = legislatorResultItems[position].name
        searchResultRecyclerViewHolder.legislatorRanking.text = legislatorResultItems[position].rank
        searchResultRecyclerViewHolder.legislatorPosition.text = legislatorResultItems[position].name

//        Glide.with(context)
//                .load(legislatorResultItems[position].imgurl)
//                .into(holder.legislatorImage)
//        searchResultRecyclerViewHolder.legislatorBackgroud.setBackgroundColor(Color.parseColor("#F9F9FB"))
//        searchResultRecyclerViewHolder.legislatorIndex.text = (position+1).toString()
//        searchResultRecyclerViewHolder.legislatorName.text = legislatorResultItems[position].name
//        searchResultRecyclerViewHolder.legislatorRanking.text = legislatorResultItems[position].rank
//        searchResultRecyclerViewHolder.legislatorPosition.text = legislatorResultItems[position].name



//        Glide.with(context)
//                .load(legislatorResultItems[position].imgurl)
//                .into(holder.legislatorImage)

    }

    class SearchResultRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var legislatorIndex : TextView = itemView!!.findViewById(R.id.search_result_rv_item_num)
        var legislatorImage : ImageView = itemView!!.findViewById(R.id.search_result_rv_item_legislator_iv)
        var legislatorName : TextView = itemView!!.findViewById(R.id.search_result_rv_item_legislator_name_tv)
        var legislatorRanking : TextView = itemView!!.findViewById(R.id.search_result_rv_item_legislator_rank_tv)
        var legislatorPosition : TextView = itemView!!.findViewById(R.id.search_result_rv_item_legislator_belong_tv)
        var legislatorBackgroud : RelativeLayout = itemView!!.findViewById(R.id.search_result_rv_item_background_rl)
        var legislatorPartyBackgroud : ImageView = itemView!!.findViewById(R.id.search_result_rv_item_legislator_color_iv)

    }

}
