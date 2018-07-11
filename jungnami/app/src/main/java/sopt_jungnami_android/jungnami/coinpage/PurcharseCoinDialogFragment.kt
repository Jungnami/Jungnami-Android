package sopt_jungnami_android.jungnami.coinpage

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.Content

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class PurcharseCoinDialogFragment : DialogFragment() {
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                PurcharseCoinDialogFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
    private var param1: String? = null
    private var param2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
//        setStyle(DialogFragment.STYLE_NO_FRAME,android.R.style.Theme_Translucent_NoTitleBar)
        //setStyle(DialogFragment.STYLE_NO_FRAME,android.R.style.Theme_Translucent_NoTitleBar)

    }
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//
//        val view = inflater.inflate(R.layout.fragment_coinpage_popup_purchase, container, false)
//        return view
//    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.fragment_coinpage_popup_purchase, null)
        val builder = AlertDialog.Builder(activity)
//        val dialog : Dialog = dialog
//        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.setCanceledOnTouchOutside(true)
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.setView(view)
        return builder.create()
    }
}