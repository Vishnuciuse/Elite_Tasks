package com.example.myapplication.task5

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.task5.fragments.DynamicTabFragment1
import com.example.myapplication.task5.fragments.DynamicTabFragment2
import com.example.myapplication.task5.fragments.DynamicTabFragment3

class DynamicViewPagerAdapter(fragment: Fragment, private val tabTitles: List<String>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DynamicTabFragment1()
            1 -> DynamicTabFragment2()
            2 -> DynamicTabFragment3()
            else -> throw IllegalStateException("Unexpected position: $position")
        }
    }
}