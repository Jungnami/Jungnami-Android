package sopt_jungnami_android.jungnami.coinpage

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_coin_charge.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetCoinInfoResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.Exchange
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

//made by YunHwan
class CoinChargeFragment : Fragment(), View.OnClickListener{
    override fun onClick(v: View?) {
        val idx = coincharge_frag_recyclerview_rv.getChildAdapterPosition(v)
        val won : String = coinChargeKindList[idx].won.toString()
        val coin : String = coinChargeKindList[idx].coin.toString()
        val purcharseDialog : Dialog = PurcharseCoinDialog(activity!!, won, coin)
        purcharseDialog.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        purcharseDialog.show()
    }

    lateinit var networkService: NetworkService
    lateinit var coinChargeRecyclerViewAdapter: CoinChargeRecyclerViewAdapter
    lateinit var coinChargeKindList : ArrayList<Exchange>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coin_charge, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        requestCoinPageInfo()
    }
    private fun setRecyclerViewAdapter(){
        coinChargeRecyclerViewAdapter = CoinChargeRecyclerViewAdapter(context!!,coinChargeKindList)
        coinChargeRecyclerViewAdapter.setOnItemClickListener(this)
        coincharge_frag_recyclerview_rv.layoutManager = LinearLayoutManager(context)
        coincharge_frag_recyclerview_rv.adapter = coinChargeRecyclerViewAdapter
    }

    private fun requestCoinPageInfo(){
        coinChargeKindList = ArrayList()
        networkService = ApplicationController.instance.networkService
        val getCoinInfoResponse = networkService.getCoinInfoResponse(SharedPreferenceController.getAuthorization(context = context!!))
        getCoinInfoResponse.enqueue(object : Callback<GetCoinInfoResponse>{
            override fun onFailure(call: Call<GetCoinInfoResponse>?, t: Throwable?) {
                Log.e("요청 실패", t.toString())
            }
            override fun onResponse(call: Call<GetCoinInfoResponse>?, response: Response<GetCoinInfoResponse>?) {
                if (response!!.isSuccessful){
                    coinChargeKindList = response.body()!!.data.exchange
                    setRecyclerViewAdapter()

                    coincharge_frag_my_coin_tv.text = "${response.body()!!.data.coin.toString()}코인"
                }
            }
        })

    }

}
