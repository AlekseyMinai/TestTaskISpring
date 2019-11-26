package com.alesno.testtaskispring.ui.listsactivity.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.model.objectbox.ObjectBox
import com.alesno.testtaskispring.model.objectbox.dao.VideosDaoImpl
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformerImpl
import com.alesno.testtaskispring.model.repository.RepositoryImpl
import com.alesno.testtaskispring.model.service.ApiService
import com.alesno.testtaskispring.ui.listsactivity.videos.fragments.ListAllMoviesFragment
import com.alesno.testtaskispring.ui.listsactivity.videos.fragments.ListFavoriteMoviesFragment
import com.alesno.testtaskispring.ui.listsactivity.videos.viewmodel.CommonViewModel
import com.alesno.testtaskispring.ui.listsactivity.videos.viewmodel.CommonViewModelFactory
import com.google.android.material.tabs.TabLayout
import io.objectbox.Box
import kotlinx.android.synthetic.main.activity_list_videos.*

class FragmentListActivity : AppCompatActivity() {

    private lateinit var viewModel: CommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_videos)
        setUpViewPager()
        tab_layout.setupWithViewPager(view_pager)
        setupTabs()
    }

    companion object {
        fun getCommonViewModel(activity: FragmentActivity): CommonViewModel {
            //redo it!
            val apiService = ApiService.create()
            val videosBox: Box<VideoObject> = ObjectBox.boxStore.boxFor(VideoObject::class.java)
            val videosDao = VideosDaoImpl(videosBox)
            val repository = RepositoryImpl(apiService, videosDao, ObjectTransformerImpl)
            return ViewModelProviders
                .of(activity, CommonViewModelFactory(repository))
                .get(CommonViewModel::class.java)
        }
    }

    private fun setUpViewPager() {
        val adapter =
            ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ListAllMoviesFragment())
        adapter.addFragment(ListFavoriteMoviesFragment())
        view_pager.adapter = adapter
    }

    private fun setupTabs() {
        setupTab(R.drawable.ic_movie_white_24dp, resources.getString(R.string.library), 0)
        setupTab(R.drawable.ic_favorite_grey_24dp, resources.getString(R.string.favorite), 1)
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                val tabPosition = p0!!.position
                val tab: TextView = tab_layout.getTabAt(tabPosition)!!.customView as TextView
                when (tabPosition) {
                    0 -> changeIconInTab(tab, R.drawable.ic_movie_grey_24dp)
                    1 -> changeIconInTab(tab, R.drawable.ic_favorite_grey_24dp)
                }
                tab.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorGrey))
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val tabPosition = p0!!.position
                val tab: TextView = tab_layout.getTabAt(tabPosition)!!.customView as TextView
                when (tabPosition) {
                    0 -> changeIconInTab(tab, R.drawable.ic_movie_white_24dp)
                    1 -> changeIconInTab(tab, R.drawable.ic_favorite_white_24dp)
                }
                tab.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorWhite))
            }
        })
    }

    private fun changeIconInTab(tab: TextView, iconResource: Int) {
        tab.setCompoundDrawablesWithIntrinsicBounds(
            0,
            iconResource, 0, 0
        )
    }

    private fun setupTab(iconResource: Int, text: String, position: Int) {
        val tab: TextView = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tab.text = text
        tab.setCompoundDrawablesWithIntrinsicBounds(0, iconResource, 0, 0)
        when (position) {
            1 -> tab.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorGrey))
        }
        tab_layout.getTabAt(position)?.customView = tab
    }
}
