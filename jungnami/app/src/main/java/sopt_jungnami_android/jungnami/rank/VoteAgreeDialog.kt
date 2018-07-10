package sopt_jungnami_android.jungnami.rank

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import kotlinx.android.synthetic.main.dialog_fragment_vote_agree.*
import sopt_jungnami_android.jungnami.MainActivity
import sopt_jungnami_android.jungnami.R

class VoteAgreeDialog(val ctx : Context, val isLikeableTab: Boolean) : Dialog(ctx) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_fragment_vote_agree)

        vote_agree_dialog_yes_btn.setOnClickListener {
            (ctx as MainActivity).setAnimRankTabIcon(isLikeableTab)
            dismiss()
        }
        vote_agree_dialog_no_btn.setOnClickListener {
            dismiss()
        }
    }
}