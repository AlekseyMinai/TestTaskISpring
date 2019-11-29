package com.alesno.testtaskispring.ui.videoactivity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.common.VIDEO_ID_EXTRA
import com.alesno.testtaskispring.common.VIDEO_URL_EXTRA
import com.alesno.testtaskispring.model.repository.RepositoryImpl
import com.alesno.testtaskispring.ui.common.getViewModel

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
        var fragment = supportFragmentManager.findFragmentById(R.id.videos_container)
        if (fragment == null) {
            fragment = VideoFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.videos_container, fragment)
            transaction.commit()
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showSystemUI()
        }

        val decorView = window.decorView
        decorView.setOnSystemUiVisibilityChangeListener {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                hideSystemUI()
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                showSystemUI()
            }
        }
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }


    companion object {
        fun startActivity(context: Context, idVideo: Long, urlVideo: String) {
            val intent = Intent(context, VideoActivity::class.java)
            intent.putExtra(VIDEO_ID_EXTRA, idVideo)
            intent.putExtra(VIDEO_URL_EXTRA, urlVideo)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            context.startActivity(intent)
        }

        fun getVideoViewModel(activity: FragmentActivity): VideoViewModel {
            return activity.getViewModel {
                VideoViewModel(RepositoryImpl.RepositoryProvider.getRepositoryIml())
            }
        }
    }
}