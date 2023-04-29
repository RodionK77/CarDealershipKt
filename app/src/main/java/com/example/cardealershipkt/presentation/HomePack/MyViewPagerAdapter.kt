package com.example.cardealershipkt.presentation.HomePack

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.cardealershipkt.presentation.MainActivity
import com.example.cardealershipkt.presentation.AuthorizationPack.ControlFragment
import com.example.cardealershipkt.presentation.SearchPack.SearchFragment

class MyViewPagerAdapter(mainActivity: MainActivity?) : FragmentStateAdapter(mainActivity!!) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> SearchFragment()
            2 -> ControlFragment()
            else -> HomeFragment()
        }
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    class ZoomOutPageTransformer : ViewPager2.PageTransformer {
        override fun transformPage(page: View, position: Float) {
            val pageWidth = page.width
            val pageHeight = page.height
            if (position < -1) { // [-Infinity,-1)
                page.alpha = 0f
            } else if (position <= 1) { // [-1,1]
                val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                val vertMargin = pageHeight * (1 - scaleFactor) / 2
                val horzMargin = pageWidth * (1 - scaleFactor) / 2
                if (position < 0) {
                    page.translationX = horzMargin - vertMargin / 2
                } else {
                    page.translationX = -horzMargin + vertMargin / 2
                }
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
                page.alpha =
                    MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)
            } else {
                page.alpha = 0f
            }
        }

        companion object {
            private const val MIN_SCALE = 0.85f
            private const val MIN_ALPHA = 0.5f
        }
    }

    companion object {
        private const val NUM_PAGES = 3
    }
}