package com.example.myapplication.task5

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.task3.StepThreeFragment
import com.example.myapplication.task3.StepTwoFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> WifiFragment()
            1 -> StepTwoFragment()
            2 -> StepThreeFragment()
            else -> throw IllegalStateException("Unexpected position: $position")
        }
    }
}