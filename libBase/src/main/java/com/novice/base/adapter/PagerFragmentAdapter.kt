package com.novice.base.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.blankj.utilcode.util.CollectionUtils

/**
 * @author novice
 */
class PagerFragmentAdapter<T : Fragment>(fm: FragmentManager, titles: ArrayList<String>) : FragmentStatePagerAdapter(fm) {

    private var fragments = arrayListOf<T>()
    private var titles = arrayListOf<String>()

    init {
        this.titles = titles
    }

    fun addFragment(fragment: T?) {
        if (fragment != null) {
            fragments.add(fragment)
            notifyDataSetChanged()
        }
    }

    fun setFragment(fragment: T?) {
        if (fragment != null) {
            fragments.clear()
            fragments.add(fragment)
            notifyDataSetChanged()
        }
    }

    fun addFragments(frags: ArrayList<T>?) {
        if (frags != null) {
            fragments.addAll(frags)
            notifyDataSetChanged()
        }
    }

    fun setFragments(fragments: ArrayList<T>?) {
        if (fragments != null) {
            this.fragments.clear()
            this.fragments.addAll(fragments)
            notifyDataSetChanged()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (CollectionUtils.isEmpty(titles)) {
            return ""
        }
        return titles[position]
    }

    override fun getItem(position: Int): T {
        return fragments[position]
    }

    override fun getCount(): Int {
        if (CollectionUtils.isEmpty(fragments)) {
            return 0
        }
        return fragments.size
    }

}