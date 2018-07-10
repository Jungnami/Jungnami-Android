package sopt_jungnami_android.jungnami.rank

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_likeable_tab.*
import kotlinx.android.synthetic.main.fragment_unlikeable_tab.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import sopt_jungnami_android.jungnami.LegislatorPageActivity
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.RankItemData


class UnlikeableTab : Fragment() , View.OnClickListener{
    override fun onClick(v: View?) {

        //클릭 시 처리 로직
        val index : Int = unlikeable_tab_rank_list_rv.getChildAdapterPosition(v)
//        val l_id : Int = legislatorRankDataList[index].l_id

        //startActivity<LegislatorPageActivity>()
    }

    lateinit var legislatorRankDataList : ArrayList<RankItemData>
    lateinit var unlikeableRankRecyclerViewAdapter: UnlikeableRankRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unlikeable_tab, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListener()

        getRankItemDataAtServer()
        setRecyclerViewAdapter()
        set1stVS2stRankView()
    }
    fun testFun(){
        toast("ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ")
    }

    private fun setClickListener(){
        unlikeable_tab_1st_btn.setOnClickListener {
            startActivity<LegislatorPageActivity>("l_id" to 11)
        }
        unlikeable_tab_2st_btn.setOnClickListener {
            startActivity<LegislatorPageActivity>("l_id" to 22)
        }
    }

    private fun setRecyclerViewAdapter(){
        unlikeableRankRecyclerViewAdapter = UnlikeableRankRecyclerViewAdapter(activity!!, legislatorRankDataList)
        unlikeableRankRecyclerViewAdapter.setOnItemClickListener(this)
        unlikeable_tab_rank_list_rv.layoutManager = LinearLayoutManager(context)
        unlikeable_tab_rank_list_rv.adapter = unlikeableRankRecyclerViewAdapter
    }
    private fun set1stVS2stRankView(){
        val rank1 : RankItemData = legislatorRankDataList[0]
        val rank2 : RankItemData = legislatorRankDataList[1]
        setVoteBarColor(rank1.party_name, unlikeable_tab_1st_vote_count_bar)
        setVoteBarColor(rank2.party_name, unlikeable_tab_2st_vote_count_bar)

        unlikeable_tab_1st_name_tv.text = rank1.name
        unlikeable_tab_1st_party_name_tv.text = rank1.party_name
        var vote_count : String = String.format("%,d",rank1.vote_count)
        unlikeable_tab_1st_vote_count_tv.text = "${vote_count}표"

        unlikeable_tab_2st_name_tv.text = rank2.name
        unlikeable_tab_2st_party_name_tv.text = rank2.party_name
        vote_count = String.format("%,d",rank2.vote_count)
        unlikeable_tab_2st_vote_count_tv.text = "${vote_count}표"

    }
    private fun setVoteBarColor(party_name : String, viewItem: View){
        when (party_name) {
            "더불어민주당" -> viewItem.background.setColorFilter(Color.parseColor("#1783DC"), PorterDuff.Mode.SRC_IN)
            "자유한국당" -> viewItem.background.setColorFilter(Color.parseColor("#E1241A"), PorterDuff.Mode.SRC_IN)
            "바른미래당" -> viewItem.background.setColorFilter(Color.parseColor("#14CDCC"), PorterDuff.Mode.SRC_IN)
            "정의당" -> viewItem.background.setColorFilter(Color.parseColor("#FCDC00"), PorterDuff.Mode.SRC_IN)
            "민중당" -> viewItem.background.setColorFilter(Color.parseColor("#EC8C0D"), PorterDuff.Mode.SRC_IN)
        }
    }

    private fun getRankItemDataAtServer(){
        legislatorRankDataList = ArrayList()
        legislatorRankDataList.add(RankItemData(1,"non","김병관","자유한국당",270000,1))
        legislatorRankDataList.add(RankItemData(2,"non","홍길동","숭구리당",320000,1))
        legislatorRankDataList.add(RankItemData(3,"non","익명1","바른미래당",310000,1))
        legislatorRankDataList.add(RankItemData(4,"non","익명2","정의당",2000,1))
        legislatorRankDataList.add(RankItemData(5,"non","익명3","민중당",1000,1))
    }

}
