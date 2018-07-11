package sopt_jungnami_android.jungnami.coinpage

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.Exchange

class CoinChargeRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<Exchange>) : RecyclerView.Adapter<CoinChargeRecyclerViewAdapter.Holder>(){
    lateinit var onItemClick :View.OnClickListener
    fun setOnItemClickListener(l : View.OnClickListener){
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_coinpage_act_coin_kind, parent, false)
        view.setOnClickListener(onItemClick)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.item_title.text = "정나미 ${dataList[position].coin}코인"
        holder.item_price.text = "${dataList[position].won}\\"
    }


    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val item_title : TextView = itemView.findViewById(R.id.coinpage_act_item_title_tv) as TextView
        val item_price : TextView = itemView.findViewById(R.id.coinpage_act_rv_item_cost_tv) as TextView
    }
}