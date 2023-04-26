package com.example.cardealershipkt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.ui.AppBarConfiguration
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.cardealershipkt.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.viewpager.adapter = MyViewPagerAdapter(this)
        binding.viewpager.setPageTransformer(MyViewPagerAdapter.ZoomOutPageTransformer())

        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_home, R.id.navigation_loupe, R.id.navigation_user
        ).build()
        binding.navView.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_home -> binding.viewpager.currentItem = 0
                    R.id.navigation_loupe -> binding.viewpager.currentItem = 1
                    R.id.navigation_user -> binding.viewpager.currentItem = 2
                }
                return@OnNavigationItemSelectedListener false
            })

        binding.viewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> binding.navView.menu.getItem(position).isChecked = true
                    1 -> binding.navView.menu.getItem(position).isChecked = true
                    2 -> binding.navView.menu.getItem(position).isChecked = true
                }
            }
        })

        fun controlFragmentSelected(fragment: Fragment?) {
            fragmentManager.beginTransaction().replace(R.id.control_fr, fragment!!).commit()
        }
    }
}