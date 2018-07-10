package sopt_jungnami_android.jungnami.rank

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_fragment_vote_agree.*
import kotlinx.android.synthetic.main.dialog_fragment_vote_agree.view.*
import org.jetbrains.anko.support.v4.toast
import sopt_jungnami_android.jungnami.R

class VoteAgreeDialogFragment : DialogFragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_fragment_vote_agree, container, false)
        view.vote_agree_dialog_yes_btn.setOnClickListener {
            toast("확인")
        }
        view.vote_agree_dialog_no_btn.setOnClickListener {
            dismiss()
        }
        return view
    }
}