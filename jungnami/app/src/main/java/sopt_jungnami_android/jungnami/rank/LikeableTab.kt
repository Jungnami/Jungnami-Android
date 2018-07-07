package sopt_jungnami_android.jungnami.rank

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.fragment_likeable_tab.*
import org.jetbrains.anko.backgroundColor
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.RankItemData

class LikeableTab : Fragment() {
    lateinit var legislatorRankDataList : ArrayList<RankItemData>
    lateinit var rankRecyclerViewAdapter: RankRecyclerViewAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_likeable_tab, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getRankItemDataAtServer()
        setRecyclerViewAdapter()

        set1stVS2stRankView()
    }

    private fun setRecyclerViewAdapter(){
        rankRecyclerViewAdapter = RankRecyclerViewAdapter(context!!, legislatorRankDataList)
        likeable_tab_rank_list_rv.layoutManager = LinearLayoutManager(context)
        likeable_tab_rank_list_rv.adapter = rankRecyclerViewAdapter
    }

    //랭크 메인 vs 뷰 바꾸기
    private fun set1stVS2stRankView(){
        val rank1 : RankItemData = legislatorRankDataList[0]
        val rank2 : RankItemData = legislatorRankDataList[1]
        setVoteBarColor(rank1.party_name, likeable_tab_1st_vote_count_bar)
        setVoteBarColor(rank2.party_name, likeable_tab_2st_vote_count_bar)

        likeable_tab_1st_name_tv.text = rank1.name
        likeable_tab_1st_party_name_tv.text = rank1.party_name
        likeable_tab_1st_vote_count_tv.text = rank1.vote_count.toString()

        likeable_tab_2st_name_tv.text = rank2.name
        likeable_tab_2st_party_name_tv.text = rank2.party_name
        likeable_tab_2st_vote_count_tv.text = rank2.vote_count.toString()
    }
    //1st 2st
    private fun setVoteBarColor(party_name : String, viewItem: View){
        when (party_name) {
            "더불어민주당" -> viewItem.background.setColorFilter(Color.parseColor("#1783DC"), PorterDuff.Mode.SRC_IN)
            "자유한국당" -> viewItem.background.setColorFilter(Color.parseColor("#E1241A"), PorterDuff.Mode.SRC_IN)
            "바른미래당" -> viewItem.background.setColorFilter(Color.parseColor("#14CDCC"), PorterDuff.Mode.SRC_IN)
            "정의당" -> viewItem.background.setColorFilter(Color.parseColor("#FCDC00"), PorterDuff.Mode.SRC_IN)
            "민중당" -> viewItem.background.setColorFilter(Color.parseColor("#EC8C0D"), PorterDuff.Mode.SRC_IN)
        }
    }

    //서버에서 데이터 받기
    private fun getRankItemDataAtServer(){
        legislatorRankDataList = ArrayList()
        legislatorRankDataList.add(RankItemData(1,"non","김병관","더불어민주당",270000,1))
        legislatorRankDataList.add(RankItemData(2,"non","홍길동","자유한국당",320000,1))
        legislatorRankDataList.add(RankItemData(3,"non","익명1","바른미래당",310000,1))
        legislatorRankDataList.add(RankItemData(4,"non","익명2","정의당",2000,1))
        legislatorRankDataList.add(RankItemData(5,"non","익명3","민중당",1000,1))
    }

}
