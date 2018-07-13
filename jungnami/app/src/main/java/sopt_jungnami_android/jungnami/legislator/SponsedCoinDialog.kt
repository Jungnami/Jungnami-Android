package sopt_jungnami_android.jungnami.legislator

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import kotlinx.android.synthetic.main.dialog_legislator_coin_sponse.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetSponseBeforeDataResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostCompleteSponseCoinResponse
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

// made by YunHwan
class SponsedCoinDialog(val ctx : Context, val l_id : Int) : Dialog(ctx) {

    lateinit var networkService : NetworkService
    var user_coin : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_legislator_coin_sponse)

        requestMyCoinDataToServer()
        setClickListener()
    }
    private fun setClickListener(){
        legislator_page_popup_cancel_btn.setOnClickListener {
            dismiss()
        }
        legislator_page_popup_check_btn.setOnClickListener {
            if (legislator_user_coin_tv.text.isEmpty()){
                ctx.toast("코인을 입력해주세요.")
            } else {
                requestCompleteSponse()
            }
        }
    }
    private fun requestMyCoinDataToServer(){
        networkService = ApplicationController.instance.networkService
        val getSponseBeforeDataResponse = networkService.getSponseBeforeDataResponse(SharedPreferenceController.getAuthorization(ctx))
        getSponseBeforeDataResponse.enqueue(object : Callback<GetSponseBeforeDataResponse>{
            override fun onFailure(call: Call<GetSponseBeforeDataResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetSponseBeforeDataResponse>?, response: Response<GetSponseBeforeDataResponse>?) {
                if (response!!.isSuccessful){
                    Log.e("내코인" , response.body()!!.data.user_coin.toString())
                    user_coin = response.body()!!.data.user_coin
                    legislator_user_coin_tv.text = "${user_coin}코인"
                }
            }
        })
    }

    private fun requestCompleteSponse(){
        val coin = legi_dialog_sponse_coin_count_input_et.text.toString().toInt()
        networkService = ApplicationController.instance.networkService
        val postCompleteSponseCoinResponse = networkService.postCompleteSponseCoinResponse(SharedPreferenceController.getAuthorization(ctx),
                l_id, coin)
        postCompleteSponseCoinResponse.enqueue(object : Callback<PostCompleteSponseCoinResponse>{
            override fun onFailure(call: Call<PostCompleteSponseCoinResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<PostCompleteSponseCoinResponse>?, response: Response<PostCompleteSponseCoinResponse>?) {
                if (response!!.isSuccessful){
                    val dialog : Dialog = SponseCompleteDialog(ctx)
                    dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.show()
                    dismiss()
                }
            }
        })
    }

//    private fun setView(){
//        coinpage_frag_popup_exchange_coin_tv.text = "-${coin}코인"
//        coinpage_frag_popup_exchange_vote_tv.text= "${coin}개"
//    }

//    private fun requestExchangeCoinToServer(){
//        val total_coin : Int = coin.toInt()
//        networkService = ApplicationController.instance.networkService
//        val postCoinExchangeResponse = networkService.postCoinExchangeResponse(SharedPreferenceController.getAuthorization(context = context!!),total_coin)
//        postCoinExchangeResponse.enqueue(object : Callback<PostCoinExchangeResponse>{
//            override fun onFailure(call: Call<PostCoinExchangeResponse>?, t: Throwable?) {
//            }
//
//            override fun onResponse(call: Call<PostCoinExchangeResponse>?, response: Response<PostCoinExchangeResponse>?) {
//                if (response!!.isSuccessful){
//                    dismiss()
//                    val completeDialog: Dialog = ExchangeCompleteCoinDialog(ctx!!)
//                    completeDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                    completeDialog.show()
//                }
//            }
//        })
//    }


}
//
//override fun onFailure(call: Call<PostExchangeCoinResponse>?, t: Throwable?) {
//    Log.e("교환 실패", t.toString())
//}
//override fun onResponse(call: Call<PostExchangeCoinResponse>?, response: Response<PostExchangeCoinResponse>?) {
//    Log.e("성공 중?", response!!.body()!!.message)
//    if (response!!.isSuccessful){
//        Log.e("성공 중222222?", "성공?!?!?!")
//        dismiss()
//        val completeDialog : Dialog = ExchangeCompleteCoinDialog(ctx!!)
//        completeDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        completeDialog.show()
//    }
//}