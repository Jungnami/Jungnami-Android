package sopt_jungnami_android.jungnami.coinpage

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import kotlinx.android.synthetic.main.fragment_vote_charge.*
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetVotePageInfoResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

//made by YunHwan
class VoteChargeFragment : Fragment() {
    lateinit var networkService: NetworkService
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        return inflater.inflate(R.layout.fragment_vote_charge, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getVoteChargePageDataFromServer()

        setClickListener()
    }

    private fun setClickListener(){
        votecharge_frag_exchange_btn_tv.setOnClickListener {
            if (votecharge_frag_vote_count_input_et.text.toString().isEmpty()){
                longToast("교환할 코인 수를 입력해주세요.")
            } else {
                val exchangeDialog : Dialog = ExchangeCoinDialog(activity!!, votecharge_frag_vote_count_input_et.text.toString())
                exchangeDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                exchangeDialog.show()
            }

        }
    }



    private fun getVoteChargePageDataFromServer(){
        networkService = ApplicationController.instance.networkService
        val getVotePageInfoResponse = networkService.getVotePageInfoResponse(SharedPreferenceController.getAuthorization(context = context!!))
       getVotePageInfoResponse.enqueue(object : Callback<GetVotePageInfoResponse>{
           override fun onFailure(call: Call<GetVotePageInfoResponse>?, t: Throwable?) {
               Log.e("투표권 요청 실패", t.toString())
           }
           override fun onResponse(call: Call<GetVotePageInfoResponse>?, response: Response<GetVotePageInfoResponse>?) {
                if (response!!.isSuccessful){
                    votecharge_frag_my_having_coin_count_tv.text = response.body()!!.data.toString()
                }
           }
       })

    }
}
