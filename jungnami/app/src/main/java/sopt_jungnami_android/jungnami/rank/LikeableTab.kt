package sopt_jungnami_android.jungnami.rank

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_likeable_tab.*
import org.jetbrains.anko.backgroundColor
import sopt_jungnami_android.jungnami.R

class LikeableTab : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_likeable_tab, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //다른 background resourse 유지하고 색상만 바꾸는 방법
        likeable_tab_1st_vote_count_bar.background.setColorFilter(Color.parseColor("#AB47BC"), PorterDuff.Mode.SRC_IN)
        likeable_tab_2st_vote_count_bar.background.setColorFilter(Color.parseColor("#1783DC"),PorterDuff.Mode.SRC_IN)
    }

}
