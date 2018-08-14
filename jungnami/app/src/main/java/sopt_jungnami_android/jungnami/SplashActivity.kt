package sopt_jungnami_android.jungnami

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.jetbrains.anko.startActivity
import sopt_jungnami_android.jungnami.legislator_list.SearchRigionActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setStatusBarColor()


        val splashimg: ImageView? = findViewById(R.id.gif_img)
        if (splashimg != null) {
            Glide.with(this).load(R.drawable.asplash).into(splashimg)
        }

        val handler = Handler()
        handler.postDelayed(Runnable {
            val intent = Intent(this, Login::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }, 2900)


    }

    private fun setStatusBarColor(){
        val view : View? = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (view != null){
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = Color.parseColor("#FFFFFF")
            }
        }
    }

}