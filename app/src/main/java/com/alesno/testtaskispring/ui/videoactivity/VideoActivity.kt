package com.alesno.testtaskispring.ui.videoactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.common.VIDEO_ID_EXTRA
import com.alesno.testtaskispring.common.VIDEO_URL_EXTRA
import com.alesno.testtaskispring.model.objectbox.ObjectBox
import com.alesno.testtaskispring.model.objectbox.dao.VideosDaoImpl
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformerImpl
import com.alesno.testtaskispring.model.repository.RepositoryImpl
import com.alesno.testtaskispring.model.service.ApiService
import io.objectbox.Box

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videos)
        startFragment()
        val viewModel = getVideoViewModel(this)
        viewModel.videoId = getVideoId()
        viewModel.setVideoUrl(getVideoUrl())
    }


    private fun startFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.videos_container, VideoFragment())
        transaction.commit()
    }

    companion object {
        fun startActivity(context: Context, idVideo: Long, urlVideo: String) {
            val intent: Intent = Intent(context, VideoActivity::class.java)
            intent.putExtra(VIDEO_ID_EXTRA, idVideo)
            intent.putExtra(VIDEO_URL_EXTRA, urlVideo)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            context.startActivity(intent)
        }

        fun getVideoViewModel(activity: FragmentActivity): VideoViewModel {
            //redo it!

            val apiService = ApiService.create()
            val videosBox: Box<VideoObject> = ObjectBox.boxStore.boxFor(VideoObject::class.java)
            val videosDao = VideosDaoImpl(videosBox)
            val repository = RepositoryImpl(apiService, videosDao, ObjectTransformerImpl)

            return ViewModelProviders.of(
                activity, VideoViewModelFactory(repository)
            ).get(VideoViewModel::class.java)
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    private fun getVideoId(): Long {
        return intent.getLongExtra(VIDEO_ID_EXTRA, 0)
    }

    private fun getVideoUrl(): String {
        return intent.getStringExtra(VIDEO_URL_EXTRA)!!
    }
}