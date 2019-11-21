package com.alesno.testtaskispring.ui.videoactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.common.VIDEO_ID_EXTRA
import com.alesno.testtaskispring.model.objectbox.ObjectBox
import com.alesno.testtaskispring.model.objectbox.dao.VideosDaoImpl
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformerImpl
import io.objectbox.Box

class VideoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videos)
        startFragment()
        val videoId = getVideoId()
        val viewModel = getVideoViewModel(this)
        viewModel.videoId = videoId
    }

    private fun getVideoId(): Long {
        return intent.getLongExtra(VIDEO_ID_EXTRA, 0)
    }

    private fun startFragment(){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.videos_container, VideoFragment())
        transaction.commit()
    }

    companion object{
        fun startActivity(context: Context, idVideo: Long){
            val intent: Intent = Intent(context, VideoActivity::class.java)
            intent.putExtra(VIDEO_ID_EXTRA, idVideo)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            context.startActivity(intent)
        }

        fun getVideoViewModel(activity: FragmentActivity): VideoViewModel {
            //redo it!

            val videosBox: Box<VideoObject> = ObjectBox.boxStore.boxFor(VideoObject::class.java)
            val videosDao = VideosDaoImpl(videosBox)

            return ViewModelProviders.of(
                    activity,
                    VideoViewModelFactory(
                        videosDao,
                        ObjectTransformerImpl
                    )
                ).get(VideoViewModel::class.java)
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0,0)
    }
}