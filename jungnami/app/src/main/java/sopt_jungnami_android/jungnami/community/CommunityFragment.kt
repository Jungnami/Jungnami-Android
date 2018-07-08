package sopt_jungnami_android.jungnami.community

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_community.*
import org.jetbrains.anko.support.v4.startActivity
import sopt_jungnami_android.jungnami.CommunityWritePage
import sopt_jungnami_android.jungnami.R

class CommunityFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        community_frag_write_feed_btn.setOnClickListener {
            startActivity<CommunityWritePage>()
        }
    }
}
