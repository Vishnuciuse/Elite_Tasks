package com.example.myapplication.task5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentTabViewBinding
import com.example.myapplication.databinding.FragmentWifiBinding
import com.example.myapplication.task5.fragments.DynamicTabFragment1
import com.example.myapplication.task5.fragments.DynamicTabFragment2
import com.example.myapplication.task5.fragments.DynamicTabFragment3
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class WifiFragment : Fragment() {

    private var _binding: FragmentWifiBinding? = null
    private val binding get() = _binding!!
    private val tabList = arrayListOf<String>("tab 1","tab 2","tab 3","tab 4")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWifiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabTitles = arrayOf("Dynamic Tab 1", "Dynamic Tab 2","Dynamic Tab 3")
        replaceFragment(DynamicTabFragment1())

            binding.dynamicTabLayout.addTab(binding.dynamicTabLayout.newTab().setText("Tab 1"))
            binding.dynamicTabLayout.addTab(binding.dynamicTabLayout.newTab().setText("Tab 2"))
            binding.dynamicTabLayout.addTab(binding.dynamicTabLayout.newTab().setText("Tab 3"))
            binding.dynamicTabLayout.addTab(binding.dynamicTabLayout.newTab().setText("Tab 3"))

        binding.dynamicTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val selectedFragment: Fragment = when (tab.position) {
                    0 -> DynamicTabFragment1()
                    1 -> DynamicTabFragment2()
                    2 -> DynamicTabFragment3()
                    else -> DynamicTabFragment1()
                }
                replaceFragment(selectedFragment)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.dynamicFrameLayout, fragment)
        fragmentTransaction.commit()
    }


}