package sopt_jungnami_android.jungnami

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import kotlinx.android.synthetic.main.activity_community_write_page.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.toast
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.*

// Written by SooYoung

class CommunityWritePage : AppCompatActivity(), View.OnClickListener {

    private val REQ_CODE_SELECT_IMAGE = 100
    lateinit var data: Uri
    private var image: MultipartBody.Part? = null

    var isText: Boolean = false
    var isIMG: Boolean = false
    var isGIF: Boolean = false

    override fun onClick(v: View?) {
        when (v) {
            community_act_writepage_back_btn -> {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_write_page)
        setStatusBarColor()

        val editTextContent = findViewById<EditText>(R.id.community_act_writepage_posting_et)

        editTextContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editTextContent.text.toString().isNotBlank()) {
                    isText = true
                    checkUploadedContent()
                    if (editTextContent.text.toString().length >= 150) {
                        Toast.makeText(applicationContext, "글자 수가 150자를 초과하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    isText = false
                    checkUploadedContent()
                }
            }
        })

        community_act_writepage_complete_btn.setOnClickListener {
            // 통신
        }
        community_act_writepage_upload_pic_btn.setOnClickListener {
            changeImage()
        }
        community_act_writepage_upload_gif_btn.setOnClickListener {
            val imageviewGIF: ImageView = findViewById(R.id.community_act_writepage_upload_gif_iv)
            val gifImage = DrawableImageViewTarget(imageviewGIF)
            Visible(imageviewGIF)
            Glide.with(this).load(R.drawable.community_gif_box).into(gifImage)
            community_act_writepage_upload_gif_iv.setOnClickListener {
                Gone(imageviewGIF)
                Glide.with(this).load(R.drawable.community_gif_box).into(community_act_writepage_upload_pic_iv)
                isGIF = true
                checkUploadedContent()
            }
        }
        community_act_writepage_upload_pic_iv.setOnClickListener {
            community_act_writepage_upload_pic_iv.setImageBitmap(null)
            isGIF = false
            isIMG = false
            checkUploadedContent()
        }

        val handler = Handler()
        handler.postDelayed({
            community_act_writepage_gif_bubble_iv.setImageBitmap(null)
        }, 2000)
    }

    private fun checkUploadedContent() {
        community_act_writepage_complete_btn.isSelected = isText || isIMG || isGIF
    }

    fun Gone(v: View?){
        val gif = findViewById<ImageView>(R.id.community_act_writepage_upload_gif_iv)
        gif.visibility = View.GONE
    }

    fun Visible(v: View?){
        val gif = findViewById<ImageView>(R.id.community_act_writepage_upload_gif_iv)
        gif.visibility = View.VISIBLE
    }

    fun changeImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //if(ApplicationController.getInstance().is)
                    this.data = data!!.data
                    Log.v("이미지", this.data.toString())
                    val options = BitmapFactory.Options()

                    var input: InputStream? = null

                    try {
                        input = contentResolver.openInputStream(this.data)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                    val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 생성
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                    val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                    val photo = File(this.data.toString()) // 가져온 파일의 이름

                    // RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());
                    // MultipartBody.Part 실제 파일의 이름 전송

                    image = MultipartBody.Part.createFormData("photo", photo.name, photoBody)

                    //body = MultipartBody.Part.createFormData("image", photo.getName(), profile_pic);

                    Glide.with(this).load(data.data).into(community_act_writepage_upload_pic_iv)
                    isIMG = true
                    checkUploadedContent()
                } catch (e: Exception) {
                    toast("잘못된 파일 형식입니다!")
                    isIMG = false
                    checkUploadedContent()
                    e.printStackTrace()
                }
            }
        }
    }

    private fun setStatusBarColor() {
        val view: View? = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = Color.parseColor("#FFFFFF")
            }
        }
    }
}