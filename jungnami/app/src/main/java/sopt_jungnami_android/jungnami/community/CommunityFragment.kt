package sopt_jungnami_android.jungnami.community


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_community.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Alarm
import sopt_jungnami_android.jungnami.CommunityWritePage
import sopt_jungnami_android.jungnami.Get.GetCommunityFeedResponse
import sopt_jungnami_android.jungnami.MainActivity
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.Content
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import sopt_jungnami_android.jungnami.mypage.MyPageActivity

class CommunityFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
    }

    private val REQUEST_CODE_WRITE = 1001
    lateinit var feedDataList: ArrayList<Content>
    lateinit var communityRecyclerViewAdapter: CommunityRecyclerViewAdapter
    lateinit var networkService: NetworkService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_community, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (SharedPreferenceController.getAuthorization(context!!) == "") {
            community_frag_what_do_u_think_box.visibility = View.GONE
        } else {
            community_frag_no_login_status_rl.visibility = View.GONE
        }

        setClickedListener()
        networkService = ApplicationController.instance.networkService

        community_frag_refresh.isRefreshing = true
        getCommunityFeed()

    }

    private fun setRecyclerViewAdapter() {
        communityRecyclerViewAdapter = CommunityRecyclerViewAdapter(context!!, feedDataList)
        communityRecyclerViewAdapter.setOnItemClickListener(this)
        community_frag_feed_list_rv.adapter = communityRecyclerViewAdapter
        community_frag_feed_list_rv.layoutManager = LinearLayoutManager(activity)
    }

    fun getCommunityFeed() {
        (context as MainActivity).window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        (context as MainActivity).isLoading = true
        val getCommunityFeedResponse = networkService.getCommunityFeed(SharedPreferenceController.getAuthorization(context!!))
        getCommunityFeedResponse.enqueue(object : Callback<GetCommunityFeedResponse> {
            override fun onFailure(call: Call<GetCommunityFeedResponse>?, t: Throwable?) {
                (context as MainActivity).window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

            override fun onResponse(call: Call<GetCommunityFeedResponse>?, response: Response<GetCommunityFeedResponse>?) {
                (context as MainActivity).isLoading = false
                if (response!!.isSuccessful) {
                    var user_img_url = response!!.body()!!.data!!.user_img_url

                    Glide.with(context!!)
                            .load(user_img_url)
                            .into(community_frag_my_picture_iv)

                    community_top_bar_new_post_counter_tv.text = response!!.body()!!.data!!.alarmcnt.toString()

                    feedDataList = response!!.body()!!.data!!.content

                    setRecyclerViewAdapter()

                    community_frag_refresh.isRefreshing = false
                    (context as MainActivity).window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                }
            }

        })
    }

    private fun setClickedListener() {
        community_frag_refresh.setOnRefreshListener {
            getCommunityFeed()
        }
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
            startActivityForResult<CommunityWritePage>(REQUEST_CODE_WRITE, "isShared" to 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_WRITE) {
            if (resultCode == Activity.RESULT_OK) {
                val isComplete = data!!.getBooleanExtra("isComplete", false)
                if (isComplete) {
                    getCommunityFeed()
                }
            }
        }
    }

}
