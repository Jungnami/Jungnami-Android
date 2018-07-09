package sopt_jungnami_android.jungnami.legislator_list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_party_tab.*
import org.jetbrains.anko.support.v4.startActivity
import sopt_jungnami_android.jungnami.LegislatorList
import sopt_jungnami_android.jungnami.LegislatorPageActivity
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.mypage.UserPageActivity

class PartyTab : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_party_tab, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setClickListener()
    }

    private fun setClickListener(){
        party_tab_blue_btn.setOnClickListener {
            startActivity<LegislatorList>("party_name" to "blue")
        }
        party_tab_red_btn.setOnClickListener {
            startActivity<UserPageActivity>()
        }
    }

}
