package sopt_jungnami_android.jungnami.rank

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_likeable_tab.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetRankingResponse
import sopt_jungnami_android.jungnami.legislator.LegislatorPageActivity
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.RankItemData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.WindowManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.uiThread
import sopt_jungnami_android.jungnami.MainActivity


class LikeableTab : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        val index: Int = likeable_tab_rank_list_rv.getChildAdapterPosition(v)
        val l_id : Int = legislatorRankDataList[index].l_id
        startActivity<LegislatorPageActivity>("l_id" to l_id)
    }


    //통신
    lateinit var networkService: NetworkService
    lateinit var legislatorRankDataList: ArrayList<RankItemData>
    lateinit var likeableRankRecyclerViewAdapter: LikeableRankRecyclerViewAdapter

    private var mainimg_1st: ImageView? = null
    private var mainimg_2st: ImageView? = null

//    private lateinit var likeableRecyclerView : RecyclerView
//    private lateinit var layoutManager: LinearLayoutManager

//    private lateinit var dataSet : ArrayList<RankItemData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        initDataSet()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_likeable_tab, container, false)

        mainimg_1st = view.findViewById(R.id.likeable_tab_1st_picture_iv) as ImageView
        mainimg_2st = view.findViewById(R.id.likeable_tab_2st_picture_iv) as ImageView

//        likeableRecyclerView = view.findViewById(R.id.likeable_tab_rank_list_rv) as RecyclerView
//        layoutManager = LinearLayoutManager(activity)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListener()
        legislatorRankDataList = ArrayList()
        if (savedInstanceState != null) {
            Log.i("상태 불러오는 중", "legislatorRankDataList 불러옴!!!!!!")
            legislatorRankDataList = savedInstanceState.getSerializable("legislatorRankDataList") as ArrayList<RankItemData>
            refreshView()
        } else {
            Log.i("상태 저장된것 없음", "legislatorRankDataList 처음임!!!!!!!!")
            likeable_tab_root_rl_to_refreshing.visibility = View.INVISIBLE
            likeable_tab_refresh_srl.isRefreshing = true
            getRankItemDataAtServer()
        }
    }

//    private fun setRecyclerviewLayoutManager(){
//        var scrollPosition = 0
//        if (likeableRecyclerView.layoutManager != null){
//            scrollPosition = (likeableRecyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
//        }
//
//        with(likeableRecyclerView){
//            layoutManager = this@LikeableTab.layoutManager
//            scrollToPosition(scrollPosition)
//        }
//    }
//    private fun initDataSet(){
//        dataSet = ArrayList()
//        val getLikeableTabResponse = ApplicationController.instance.networkService.getRanking(
//                SharedPreferenceController.getAuthorization(context = context!!), 1)
//        getLikeableTabResponse.enqueue(object :Callback<GetRankingResponse>{
//            override fun onFailure(call: Call<GetRankingResponse>?, t: Throwable?) {
//                toast("응답 실패")
//            }
//
//            override fun onResponse(call: Call<GetRankingResponse>?, response: Response<GetRankingResponse>?) {
//                if (response!!.isSuccessful){
//                    dataSet = response.body()!!.data
//                }
//            }
//        })
//    }

    private fun initSettingView() {
        set1stVS2stRankView()
        likeable_tab_root_rl_to_refreshing.visibility = View.VISIBLE
        likeable_tab_refresh_srl.isRefreshing = false
        setRecyclerViewAdapter()
        setRankVoteCountProgressbarAnimation()
    }

    private fun refreshView() {
        set1stVS2stRankView()
        likeable_tab_root_rl_to_refreshing.visibility = View.VISIBLE
        likeable_tab_refresh_srl.isRefreshing = false
        setRecyclerViewAdapter()
        setRankVoteCountProgressbarAnimation()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("상태 저장 중", "legislatorRankDataList 저장!!!")
        outState.putSerializable("legislatorRankDataList", legislatorRankDataList)
        super.onSaveInstanceState(outState)
    }

    //서버에서 데이터 받기
    fun getRankItemDataAtServer() {
        (context as MainActivity).window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        (context as MainActivity).isLoading = true
        networkService = ApplicationController.instance.networkService

        val getLikeableRankingResponse = networkService.getRanking(SharedPreferenceController.getAuthorization(context = context!!),1)
        getLikeableRankingResponse.enqueue(object : Callback<GetRankingResponse> {
            override fun onFailure(call: Call<GetRankingResponse>?, t: Throwable?) {
                toast("응답 실패")
                (context as MainActivity).window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            }

            override fun onResponse(call: Call<GetRankingResponse>?, response: Response<GetRankingResponse>?) {
                (context as MainActivity).isLoading = false
                if (response!!.isSuccessful) {
                    legislatorRankDataList = response.body()!!.data
                    if (legislatorRankDataList.size > 1) {
                        legislatorRankDataList = legislatorRankDataList.take(100) as ArrayList<RankItemData>
                        initSettingView()
                        (context as MainActivity).window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    } else {
                        (context as MainActivity).window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        toast("데이터 수 부족")
                    }
                }
            }
        })

    }

    private fun setClickListener() {
        likeable_tab_refresh_srl.setOnRefreshListener {
            getRankItemDataAtServer()
        }
        likeable_tab_1st_btn.setOnClickListener {
            startActivity<LegislatorPageActivity>("l_id" to legislatorRankDataList[0].l_id)
        }
        likeable_tab_2st_btn.setOnClickListener {
            startActivity<LegislatorPageActivity>("l_id" to legislatorRankDataList[1].l_id)
        }
    }

    private fun setRankVoteCountProgressbarAnimation() {
        val animOf1st: Animation = AnimationUtils.loadAnimation(context!!, R.anim.rank_1st_progress_anim)
        val animOf2st: Animation = AnimationUtils.loadAnimation(context!!, R.anim.rank_2st_progress_anim)
        animOf1st.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                likeable_tab_1st_vote_count_tv.visibility = View.VISIBLE
            }

            override fun onAnimationStart(animation: Animation?) {
                likeable_tab_1st_vote_count_tv.visibility = View.INVISIBLE
                likeable_tab_1st_vote_count_bar.visibility = View.VISIBLE
            }
        })
        animOf2st.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                likeable_tab_2st_vote_count_tv.visibility = View.VISIBLE
            }

            override fun onAnimationStart(animation: Animation?) {
                likeable_tab_2st_vote_count_tv.visibility = View.INVISIBLE
                likeable_tab_2st_vote_count_bar.visibility = View.VISIBLE
            }
        })
        likeable_tab_1st_vote_count_bar.startAnimation(animOf1st)
        likeable_tab_2st_vote_count_bar.startAnimation(animOf2st)
    }


    private fun setRecyclerViewAdapter() {
        likeableRankRecyclerViewAdapter = LikeableRankRecyclerViewAdapter(activity!!, legislatorRankDataList)
        likeableRankRecyclerViewAdapter.setOnItemClickListener(this)
        likeable_tab_rank_list_rv.layoutManager = LinearLayoutManager(context)
        likeable_tab_rank_list_rv.adapter = likeableRankRecyclerViewAdapter
    }

    //랭크 메인 vs 뷰 바꾸기
    private fun set1stVS2stRankView() {

        val rank1: RankItemData = legislatorRankDataList[0]
        val rank2: RankItemData = legislatorRankDataList[1]
        setVoteBarColor(rank1.party_name, likeable_tab_1st_vote_count_bar)
        setVoteBarColor(rank2.party_name, likeable_tab_2st_vote_count_bar)
        if (rank1.ranking == rank2.ranking){
            //공동 1등
            likeable_tab_2st_title.text = "1위"
        }
        //1등 셋팅
        val requestOptions = RequestOptions()
        if (rank1.mainimg != "0") {
            requestOptions.centerCrop()
            Glide.with(context!!)
                    .setDefaultRequestOptions(requestOptions)
                    .load(legislatorRankDataList[0].mainimg)
                    .into(mainimg_1st!!)
        }
        likeable_tab_1st_name_tv.text = rank1.l_name
        likeable_tab_1st_party_name_tv.text = rank1.party_name
        var vote_count: String = String.format("%,d", rank1.score)
        likeable_tab_1st_vote_count_tv.text = "${vote_count}표"

        //2등 셋팅
        if (rank2.mainimg != "0") {
            requestOptions.centerCrop()
            Glide.with(context!!)
                    .setDefaultRequestOptions(requestOptions)
                    .load(legislatorRankDataList[1].mainimg)
                    .into(mainimg_2st!!)
        }


        likeable_tab_2st_name_tv.text = rank2.l_name
        likeable_tab_2st_party_name_tv.text = rank2.party_name
        vote_count = String.format("%,d", rank2.score)
        likeable_tab_2st_vote_count_tv.text = "${vote_count}표"

        //투표율 바 동적 셋팅
        val dp = context!!.resources.displayMetrics.density
        val params1: RelativeLayout.LayoutParams = likeable_tab_1st_vote_count_bar.layoutParams as RelativeLayout.LayoutParams
        params1.width = (130 * dp).toInt()
        likeable_tab_1st_vote_count_bar.layoutParams = params1
        val params2: RelativeLayout.LayoutParams = likeable_tab_2st_vote_count_bar.layoutParams as RelativeLayout.LayoutParams
        params2.width = (130 * legislatorRankDataList[1].width * dp).toInt()
        likeable_tab_2st_vote_count_bar.layoutParams = params2


    }

    //1st 2st
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
