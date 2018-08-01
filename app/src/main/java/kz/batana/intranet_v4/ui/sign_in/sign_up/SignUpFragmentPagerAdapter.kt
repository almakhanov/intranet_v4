package kz.batana.intranet_v4.ui.sign_in.sign_up

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SignUpFragmentPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    private var fragments : ArrayList<Fragment> = ArrayList()
    private var titles : ArrayList<String> = ArrayList()


    override fun getItem(position: Int): Fragment = fragments.get(position)

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence = titles.get(position)

    fun addFragment (fragment: Fragment, title : String) {
        fragments.add(fragment)
        titles.add(title)
    }
}