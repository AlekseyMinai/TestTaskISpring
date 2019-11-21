package com.alesno.testtaskispring.ui.listsactivity.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.alesno.testtaskispring.R
import com.alesno.testtaskispring.model.objectbox.ObjectBox
import com.alesno.testtaskispring.model.objectbox.dao.VideosDaoImpl
import com.alesno.testtaskispring.model.objectbox.entities.VideoObject
import com.alesno.testtaskispring.model.objectbox.transformer.ObjectTransformerImpl
import com.alesno.testtaskispring.model.repository.ApiRepository
import com.alesno.testtaskispring.model.service.ApiService
import com.alesno.testtaskispring.ui.listsactivity.videos.fragments.ListAllMoviesFragment
import com.alesno.testtaskispring.ui.listsactivity.videos.fragments.ListFavoritMoviesFragment
import com.alesno.testtaskispring.ui.listsactivity.videos.viewmodel.CommonViewModel
import com.alesno.testtaskispring.ui.listsactivity.videos.viewmodel.CommonViewModelFactory
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

    companion object{
        fun getCommonViewModel(activity: FragmentActivity): CommonViewModel {
            //redo it!
            val apiService = ApiService.create()
            val repository = ApiRepository(apiService)
            val videosBox: Box<VideoObject> = ObjectBox.boxStore.boxFor(VideoObject::class.java)
            val videosDao = VideosDaoImpl(videosBox)

            return ViewModelProviders
                .of(activity,
                    CommonViewModelFactory(
                        repository,
                        videosDao,
                        ObjectTransformerImpl
                    )
                )
                .get(CommonViewModel::class.java)
        }
    }

    private fun setUpViewPager() {
        val adapter =
            ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ListAllMoviesFragment())
        adapter.addFragment(ListFavoritMoviesFragment())
        view_pager.adapter = adapter
    }

    private fun setupTabs(){
        setupTab(R.drawable.ic_movie_white_24dp, resources.getString(R.string.library), 0)
        setupTab(R.drawable.ic_favorite_white_24dp, resources.getString(R.string.favorite), 1)
    }

    private fun setupTab(iconResource: Int, text: String, position: Int){
        val tab: TextView = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tab.text = text
        tab.setCompoundDrawablesWithIntrinsicBounds(0, iconResource, 0,0)
        tab_layout.getTabAt(position)?.customView = tab
    }
}
