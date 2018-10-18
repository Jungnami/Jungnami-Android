package sopt_jungnami_android.jungnami.rank

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_likeable_tab.*
import kotlinx.android.synthetic.main.fragment_unlikeable_tab.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetRankingResponse
import sopt_jungnami_android.jungnami.MainActivity
import sopt_jungnami_android.jungnami.legislator.LegislatorPageActivity
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.RankItemData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController


class UnlikeableTab : Fragment() , View.OnClickListener{
    override fun onClick(v: View?) {
        //클릭 시 처리 로직
        val index : Int = unlikeable_tab_rank_list_rv.getChildAdapterPosition(v)
        val l_id : Int = legislatorRankDataList[index].l_id
        startActivity<LegislatorPageActivity>("l_id" to l_id)
    }
    private var mainimg_1st: ImageView? = null
    private var mainimg_2st: ImageView? = null

    lateinit var networkService: NetworkService

    lateinit var legislatorRankDataList : ArrayList<RankItemData>
    lateinit var unlikeableRankRecyclerViewAdapter: UnlikeableRankRecyclerViewAdapter

    var currentItemsCount : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        legislatorRankDataList = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_unlikeable_tab, container, false)
        mainimg_1st = view.findViewById(R.id.unlikeable_tab_1st_picture_iv) as ImageView
        mainimg_2st = view.findViewById(R.id.unlikeable_tab_2st_picture_iv) as ImageView
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListener()
        unlikeable_tab_root_rl_to_refreshing.visibility = View.INVISIBLE
        unlikeable_tab_refresh_srl.isRefreshing = true
        getRankItemDataAtServer()


    }

    private fun initSettingView() {
        set1stVS2stRankView()
        unlikeable_tab_refresh_srl.isRefreshing = false
        unlikeable_tab_root_rl_to_refreshing.visibility = View.VISIBLE
        setRecyclerViewAdapter()
        setRankVoteCountProgressbarAnimation()
        moreLoadListData()
    }
    private fun  moreLoadListData(){
        unlikeable_tab_nested_scroll.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (v!!.getChildAt(v!!.childCount -1) != null){
                if ((scrollY >= (v.getChildAt(v!!.childCount -1).measuredHeight) - v.measuredHeight) && scrollY > oldScrollY){
                    unlikeable_tab_refresh_srl.isRefreshing = true
                    currentItemsCount = legislatorRankDataList.size
                    getMoreRankItemDataAtServer()
                }
            }
        }
    }
    //서버에서 데이터 받기
    fun getRankItemDataAtServer() {
        networkService = ApplicationController.instance.networkService
        doAsync {
            unlikeable_tab_refresh_srl.isRefreshing = true
        }

        val getUnlikeableRankingResponse = networkService.getRanking(SharedPreferenceController.getAuthorization(context = context!!),0, currentItemsCount)
        getUnlikeableRankingResponse.enqueue(object : Callback<GetRankingResponse> {
            override fun onFailure(call: Call<GetRankingResponse>?, t: Throwable?) {
                toast("응답 실패")
            }

            override fun onResponse(call: Call<GetRankingResponse>?, response: Response<GetRankingResponse>?) {
                if (response!!.isSuccessful) {
                    legislatorRankDataList = response.body()!!.data
                    if (legislatorRankDataList.size > 1) {
                        initSettingView()
                    } else {
                        toast("데이터 수 부족")
                    }
                }
            }
        })
    }
    fun getMoreRankItemDataAtServer() {
        networkService = ApplicationController.instance.networkService
        doAsync {
            unlikeable_tab_refresh_srl.isRefreshing = true
        }
        val getUnlikeableRankingResponse = networkService.getRanking(SharedPreferenceController.getAuthorization(context = context!!),0, currentItemsCount)
        getUnlikeableRankingResponse.enqueue(object : Callback<GetRankingResponse> {
            override fun onFailure(call: Call<GetRankingResponse>?, t: Throwable?) {
                toast("응답 실패")
            }

            override fun onResponse(call: Call<GetRankingResponse>?, response: Response<GetRankingResponse>?) {
                if (response!!.isSuccessful) {
                    if (response.body()!!.data.size > 0){
                        legislatorRankDataList.addAll(response.body()!!.data)
                        unlikeableRankRecyclerViewAdapter.addItem(response.body()!!.data)
                        currentItemsCount = legislatorRankDataList.size
                        unlikeable_tab_refresh_srl.isRefreshing = false
                    }

                }
            }
        })
    }
    private fun setClickListener(){
        unlikeable_tab_refresh_srl.setOnRefreshListener {
            currentItemsCount = 0
            getRankItemDataAtServer()
        }

        unlikeable_tab_1st_btn.setOnClickListener {
            startActivity<LegislatorPageActivity>("l_id" to legislatorRankDataList[0].l_id)
        }
        unlikeable_tab_2st_btn.setOnClickListener {
            startActivity<LegislatorPageActivity>("l_id" to legislatorRankDataList[1].l_id)
        }
    }
    private fun setRankVoteCountProgressbarAnimation(){
        val animOf1st: Animation = AnimationUtils.loadAnimation(context!!, R.anim.rank_1st_progress_anim)
        val animOf2st: Animation = AnimationUtils.loadAnimation(context!!, R.anim.rank_2st_progress_anim)
        animOf1st.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
                unlikeable_tab_1st_vote_count_tv.visibility = View.VISIBLE
            }
            override fun onAnimationStart(animation: Animation?) {
                unlikeable_tab_1st_vote_count_tv.visibility = View.INVISIBLE
                unlikeable_tab_1st_vote_count_bar.visibility = View.VISIBLE
            }
        })
        animOf2st.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
                unlikeable_tab_2st_vote_count_tv.visibility = View.VISIBLE
            }
            override fun onAnimationStart(animation: Animation?) {
                unlikeable_tab_2st_vote_count_tv.visibility = View.INVISIBLE
                unlikeable_tab_2st_vote_count_bar.visibility = View.VISIBLE
            }
        })
        unlikeable_tab_1st_vote_count_bar.startAnimation(animOf1st)
        unlikeable_tab_2st_vote_count_bar.startAnimation(animOf2st)
    }
    private fun setRecyclerViewAdapter(){
        unlikeableRankRecyclerViewAdapter = UnlikeableRankRecyclerViewAdapter(activity!!, legislatorRankDataList.take(15) as ArrayList<RankItemData>)
        unlikeableRankRecyclerViewAdapter.setOnItemClickListener(this)
        unlikeable_tab_rank_list_rv.layoutManager = LinearLayoutManager(context)
        unlikeable_tab_rank_list_rv.adapter = unlikeableRankRecyclerViewAdapter
    }
    private fun set1stVS2stRankView(){
        val rank1 : RankItemData = legislatorRankDataList[0]
        val rank2 : RankItemData = legislatorRankDataList[1]
        if (rank1.ranking == rank2.ranking){
            unlikeable_tab_2st_title.text = "1위"
        }
        setVoteBarColor(rank1.party_name, unlikeable_tab_1st_vote_count_bar)
        setVoteBarColor(rank2.party_name, unlikeable_tab_2st_vote_count_bar)

        //1등 셋팅
        val requestOptions = RequestOptions()
        if (rank1.mainimg != "0") {
            requestOptions.centerCrop()
            Glide.with(context!!)
                    .setDefaultRequestOptions(requestOptions)
                    .load(legislatorRankDataList[0].mainimg)
                    .into(mainimg_1st!!)
        }
        unlikeable_tab_1st_name_tv.text = rank1.l_name
        unlikeable_tab_1st_party_name_tv.text = rank1.party_name
        var vote_count: String = String.format("%,d", rank1.score)
        unlikeable_tab_1st_vote_count_tv.text = "${vote_count}표"

        //2등 셋팅
        if (rank2.mainimg != "0") {
            requestOptions.centerCrop()
            Glide.with(context!!)
                    .setDefaultRequestOptions(requestOptions)
                    .load(legislatorRankDataList[1].mainimg)
                    .into(mainimg_2st!!)
        }


        unlikeable_tab_2st_name_tv.text = rank2.l_name
        unlikeable_tab_2st_party_name_tv.text = rank2.party_name
        vote_count = String.format("%,d", rank2.score)
        unlikeable_tab_2st_vote_count_tv.text = "${vote_count}표"

        val dp = context!!.resources.displayMetrics.density
        val params1: RelativeLayout.LayoutParams = unlikeable_tab_1st_vote_count_bar.layoutParams as RelativeLayout.LayoutParams
        params1.width = (130 * dp).toInt()
        unlikeable_tab_1st_vote_count_bar.layoutParams = params1
        val params2: RelativeLayout.LayoutParams = unlikeable_tab_2st_vote_count_bar.layoutParams as RelativeLayout.LayoutParams
        params2.width = (130 * legislatorRankDataList[1].width * dp).toInt()
        unlikeable_tab_2st_vote_count_bar.layoutParams = params2

    }
    private fun setVoteBarColor(party_name: String, viewItem: View) {
        when (party_name) {
            "더불어민주당" -> viewItem.background.setColorFilter(Color.parseColor("#1783DC"), PorterDuff.Mode.SRC_IN)
            "자유한국당" -> viewItem.background.setColorFilter(Color.parseColor("#E1241A"), PorterDuff.Mode.SRC_IN)
            "바른미래당" -> viewItem.background.setColorFilter(Color.parseColor("#14CDCC"), PorterDuff.Mode.SRC_IN)
            "정의당" -> viewItem.background.setColorFilter(Color.parseColor("#FCDC00"), PorterDuff.Mode.SRC_IN)
            "민중당" -> viewItem.background.setColorFilter(Color.parseColor("#EC8C0D"), PorterDuff.Mode.SRC_IN)
            "대한애국당" -> viewItem.background.setColorFilter(Color.parseColor("#123167"), PorterDuff.Mode.SRC_IN)
            "민주평화당" -> viewItem.background.setColorFilter(Color.parseColor("#3EA437"), PorterDuff.Mode.SRC_IN)
            "무소속" -> viewItem.background.setColorFilter(Color.parseColor("#B7B7B7"), PorterDuff.Mode.SRC_IN)
            else -> viewItem.background.setColorFilter(Color.parseColor("#B7B7B7"), PorterDuff.Mode.SRC_IN)
        }
    }


}
