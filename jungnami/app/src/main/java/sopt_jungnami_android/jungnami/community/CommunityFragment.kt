package sopt_jungnami_android.jungnami.community


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
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
import sopt_jungnami_android.jungnami.Post.PostCommunityLikeRequset
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.Content
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import sopt_jungnami_android.jungnami.mypage.MyPageActivity

class CommunityFragment : Fragment(), View.OnClickListener {

    lateinit var feedDataList : ArrayList<Content>
    lateinit var communityRecyclerViewAdapter: CommunityRecyclerViewAdapter
    lateinit var networkService: NetworkService
    lateinit var postCommunityLikeRequset : PostCommunityLikeRequset

    override fun onClick(v: View?) {

//        val index : Int = community_frag_feed_list_rv.getChildAdapterPosition(v!!) // 몇번째를 클릭했는지에 대한 정보를 index가 가지고 있다.
//        val name : String = kakaoItems[index].name
//        val profile : Int = kakaoItems[index].profile
//        val intent : Intent = Intent(applicationContext, ChatActivity::class.java)
//        // 인텐트로 정보 전달
//        // 인텐트로 담아서 키값으로 데이터 넘어감
//
//        intent.putExtra("name",name)
//        intent.putExtra("profile",profile)
//
//        startActivity(intent)
//        // 리사이클러뷰의 몇번째를 클릭했는지 처리할 것
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (SharedPreferenceController.getAuthorization(context!!) == ""){
            community_frag_what_do_u_think_box.visibility = View.GONE
        } else {
            community_frag_no_login_status_rl.visibility = View.GONE
        }

        setClickedListener()
        networkService = ApplicationController.instance.networkService
        getCommunityFeed()



//        setRecyclerViewAdapter()
    }

    private fun setRecyclerViewAdapter() {
        communityRecyclerViewAdapter = CommunityRecyclerViewAdapter(feedDataList, activity)
        communityRecyclerViewAdapter.setOnItemClickListener(this)
        community_frag_feed_list_rv.adapter = communityRecyclerViewAdapter
        community_frag_feed_list_rv.layoutManager = LinearLayoutManager(activity)
    }

//    fun postCommunityLike(){
//        val postCommunityLikeRequset = PostCommunityLikeRequset()
//    }


    fun getCommunityFeed(){
        val getCommunityFeedResponse = networkService.getCommunityFeed(SharedPreferenceController.getAuthorization(context!!))
        getCommunityFeedResponse.enqueue(object : Callback<GetCommunityFeedResponse>{
            override fun onFailure(call: Call<GetCommunityFeedResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetCommunityFeedResponse>?, response: Response<GetCommunityFeedResponse>?) {
                if(response!!.isSuccessful){
                    var user_img_url = response!!.body()!!.data!!.user_img_url


                    Glide.with(context!!)
                            .load(user_img_url)
                            .into(community_frag_my_picture_iv)

                    var alarmcnt = response!!.body()!!.data!!.alarmcnt
                    feedDataList = response!!.body()!!.data!!.content as ArrayList<Content>
                    setRecyclerViewAdapter()
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
