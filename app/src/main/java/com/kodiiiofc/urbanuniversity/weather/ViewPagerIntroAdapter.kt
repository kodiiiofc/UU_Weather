package com.kodiiiofc.urbanuniversity.weather

import android.content.Context
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerIntroAdapter(fragment: FragmentActivity, private val list: List<Intro>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        val introFragment = IntroFragment()
        introFragment.arguments = bundleOf("intro" to list[position])
        return introFragment
    }
}