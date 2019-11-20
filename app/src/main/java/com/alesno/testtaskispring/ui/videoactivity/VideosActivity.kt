package com.alesno.testtaskispring.ui.videoactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alesno.testtaskispring.R

class VideosActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videos)
        startFragment()
    }

    private fun startFragment(){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.videos_container, VideosFragment())
        transaction.commit()
    }

    companion object{
        fun startActivity(context: Context){
            val intent: Intent = Intent(context, VideosActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            context.startActivity(intent)

        }
    }
}