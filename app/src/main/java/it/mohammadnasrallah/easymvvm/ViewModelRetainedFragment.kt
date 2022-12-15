package it.mohammadnasrallah.easymvvm

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

class ViewModelRetainedFragment<VM : ViewModel<*, *>?> : Fragment() {
    var viewModel: VM? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (viewModel != null) {
            viewModel!!.destroy()
        }
    }

    companion object {
        val TAG = ViewModelRetainedFragment::class.java.name
        fun <P : ViewModel<*, *>?> getOrCreateFragment(fragmentManager: FragmentManager, tag: String?): ViewModelRetainedFragment<P> {
            var fragment = fragmentManager.findFragmentByTag(tag) as ViewModelRetainedFragment<P>?
            if (fragment == null) {
                fragment = ViewModelRetainedFragment<P>()
                fragmentManager.beginTransaction().add(fragment, tag).commit()
            }
            return fragment
        }
    }
}