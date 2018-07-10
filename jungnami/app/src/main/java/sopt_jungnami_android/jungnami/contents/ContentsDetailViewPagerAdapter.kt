package sopt_jungnami_android.jungnami.contents

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import sopt_jungnami_android.jungnami.R

class ContentsDetailViewPagerAdapter(val ctx : Context, val image_url : ArrayList<Int>) : PagerAdapter(){

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }

    override fun getCount(): Int = image_url.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView : View = LayoutInflater.from(ctx).inflate(R.layout.view_pager_item_contents_detail, container, false)
        val imageView : ImageView = itemView.findViewById(R.id.contents_detail_viewpager_item_image_iv) as ImageView
//        val pageCounter : TextView = itemView.findViewById(R.id.contents_detail_viewpager_item_page_counter_tv) as TextView
        imageView.setImageResource(image_url[position])
//        pageCounter.text = "${position+1}/${this.image_url.size}"
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.invalidate()
    }
}