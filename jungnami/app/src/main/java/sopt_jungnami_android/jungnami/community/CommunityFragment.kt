package sopt_jungnami_android.jungnami.community


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_community.*
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Alarm
import sopt_jungnami_android.jungnami.CommunityWritePage
import sopt_jungnami_android.jungnami.Get.GetCommunityFeedResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.Content
import sopt_jungnami_android.jungnami.mypage.MyPageActivity

class CommunityFragment : Fragment() {
    lateinit var networkService: NetworkService
    lateinit var feedDataList : ArrayList<Content>
    lateinit var communityRecyclerViewAdapter: CommunityRecyclerViewAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickedListener()
        networkService = ApplicationController.instance.networkService
        getCommunityFeed()

        community_frag_no_login_status_rl.visibility = View.VISIBLE

//        setRecyclerViewAdapter()
    }


    fun getCommunityFeed(){
        val getCommunityFeedResponse = networkService.getCommunityFeed()
        getCommunityFeedResponse.enqueue(object : Callback<GetCommunityFeedResponse>{
            override fun onFailure(call: Call<GetCommunityFeedResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetCommunityFeedResponse>?, response: Response<GetCommunityFeedResponse>?) {
                if(response!!.isSuccessful){

                    var alarmcnt = response!!.body()!!.data!!.alarmcnt
                    feedDataList = response!!.body()!!.data!!.content as ArrayList<Content>
                    communityRecyclerViewAdapter = CommunityRecyclerViewAdapter(feedDataList, activity)
                    community_frag_feed_list_rv.adapter = communityRecyclerViewAdapter
                    community_frag_feed_list_rv.layoutManager = LinearLayoutManager(activity)
                }
            }

        })
    }
//    윤환이형이 짠 코드
//    private fun recquestDataToServer(){
//        feedDataList = ArrayList()
//        feedDataList.add(FeedItemData("문어"))
//        feedDataList.add(FeedItemData("오징어"))
//        feedDataList.add(FeedItemData("꼴뚜기"))
//    }
//
//    private fun setRecyclerViewAdapter(){
//        communityRecyclerViewAdapter = CommunityRecyclerViewAdapter(activity!!, dataList = feedDataList)
//        community_frag_feed_list_rv.layoutManager = LinearLayoutManager(activity)
//        community_frag_feed_list_rv.adapter = communityRecyclerViewAdapter
//    }


    private fun setClickedListener(){
        //종
        community_frag_top_bar_bell_btn.setOnClickListener {
            startActivity<Alarm>()
        }
        //마이페이지
        community_frag_top_bar_my_page_btn.setOnClickListener {
            startActivity<MyPageActivity>()
        }
        //내 피드 작성
        community_frag_write_feed_btn.setOnClickListener {
            startActivity<CommunityWritePage>("isShared" to 0)
        }

    }

}
