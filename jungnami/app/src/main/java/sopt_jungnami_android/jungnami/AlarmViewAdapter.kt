package sopt_jungnami_android.jungnami

import android.media.Image
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import sopt_jungnami_android.jungnami.data.AlarmData

class AlarmViewAdapter (private var alarmItems : ArrayList<AlarmData>) : RecyclerView.Adapter<AlarmViewAdapter.AlarmViewHolder>() {
    private lateinit var OnItemClick : View.OnClickListener

    fun SetOnItemClickListener(l :  View.OnClickListener) {
        OnItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val alarmView : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_alarm, parent, false)
        alarmView.setOnClickListener(OnItemClick)
        return AlarmViewHolder(alarmView)
    }

    override fun getItemCount(): Int = alarmItems.size

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.alarmProfileimg.setImageResource(alarmItems[position].profileimg)
        holder.alarmMenuimg.setImageResource(alarmItems[position].menuimg)
        if (alarmItems[position].menuimg == R.drawable.alarm_follow) {
            when(alarmItems[position].followimg) {
                //0 -> null
                1 -> holder.alarmFollowimg.setImageResource(R.drawable.alarm_following_gray)
            }
        }
        holder.alarmName.text = alarmItems[position].name
        holder.alarmState.text = alarmItems[position].state
        holder.alarmDate.text = alarmItems[position].date
    }

    inner class AlarmViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var alarmProfileimg : ImageView = itemView!!.findViewById(R.id.rv_alarm_item_user_img_btn) as ImageView
        var alarmMenuimg : ImageView = itemView!!.findViewById(R.id.rv_alarm_item_menu_iv) as ImageView
        var alarmName : TextView = itemView!!.findViewById(R.id.rv_alarm_item_user_name_tv) as TextView
        var alarmState : TextView = itemView!!.findViewById(R.id.rv_alarm_item_user_state_tv) as TextView
        var alarmDate : TextView = itemView!!.findViewById(R.id.rv_alarm_item_user_time_tv) as TextView
        var alarmFollowimg : ImageView = itemView!!.findViewById(R.id.rv_alarm_item_follow_btn) as ImageView
    }
}