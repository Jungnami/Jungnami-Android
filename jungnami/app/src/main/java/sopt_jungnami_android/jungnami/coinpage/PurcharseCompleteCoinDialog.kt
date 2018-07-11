package sopt_jungnami_android.jungnami.coinpage

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import kotlinx.android.synthetic.main.dialog_fragment_vote_agree.*
import kotlinx.android.synthetic.main.fragment_coinpage_popup_exchange.*
import kotlinx.android.synthetic.main.fragment_coinpage_popup_purchase.*
import kotlinx.android.synthetic.main.fragment_coinpage_popup_purchase_complete.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetVotingResponse
import sopt_jungnami_android.jungnami.MainActivity
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostCoinChargeCompletionResponse
import sopt_jungnami_android.jungnami.Post.PostCompletingVote
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.VotingCountData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

// made by YunHwan
class PurcharseCompleteCoinDialog(val ctx : Context) : Dialog(ctx) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.fragment_coinpage_popup_purchase_complete)
        setClickListener()

    }
    private fun setClickListener(){
        coinpage_exchange_popup_complete_check_btn.setOnClickListener {
            (ctx as CoinPageActivity).finish()
            ctx.startActivity<CoinPageActivity>("target" to "coin")
            dismiss()
        }
    }




}