package com.alesno.testtaskispring.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.alesno.testtaskispring.R
import com.google.android.material.tabs.TabLayout

class FragmentActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        viewPager = findViewById<ViewPager>(R.id.view_pager)
        setUpViewPager()
        tabLayout.setupWithViewPager(viewPager)
        setupTabs()
    }

    private fun setUpViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ListAllMoviesFragment())
        adapter.addFragment(ListFavoritMoviesFragment())
        viewPager.adapter = adapter
    }

    private fun setupTabs(){
        setupTabs(R.drawable.ic_movie_white_24dp, resources.getString(R.string.library), 0)
        setupTabs(R.drawable.ic_favorite_white_24dp, resources.getString(R.string.favorite), 1)

    }

    private fun setupTabs(iconResource: Int, text: String, position: Int){
        val tab: TextView = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tab.text = text
        tab.setCompoundDrawablesWithIntrinsicBounds(0, iconResource, 0,0)
        tabLayout.getTabAt(position)?.customView = tab
    }


}
