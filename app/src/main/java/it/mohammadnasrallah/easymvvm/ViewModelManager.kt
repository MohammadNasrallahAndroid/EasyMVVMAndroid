package it.mohammadnasrallah.easymvvm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

internal class ViewModelManager<VM : ViewModel<*, *>?> {
    private var viewModel: VM? = null
    fun <A> getOrCreate(activity: A, state: Bundle?): VM? where A : AppCompatActivity?, A : ViewModelContainer<VM>? {
        return getOrCreate(activity as ViewModelContainer<VM>, activity.supportFragmentManager, state, activity.intent.extras)
    }

    fun <F> getOrCreate(fragment: F, state: Bundle?): VM? where F : Fragment?, F : ViewModelContainer<VM>? {
        val viewModel = getOrCreate(fragment as ViewModelContainer<VM>, fragment.getActivity()!!.supportFragmentManager, state, fragment.arguments)
        if (viewModel?.optionMenuId!! > 0) {
            fragment.setHasOptionsMenu(true)
        }
        return viewModel
    }

    private fun getOrCreate(container: ViewModelContainer<VM>, fragmentManager: FragmentManager, state: Bundle?, arguments: Bundle?): VM? {
        var args: Any? = null
        if (arguments != null) {
            args = ArgumentManager.readArgument(arguments)
        }
        val retainedFragment: ViewModelRetainedFragment<VM> =
            ViewModelRetainedFragment.getOrCreateFragment(fragmentManager, ViewModelRetainedFragment.TAG + container.getFragmentTag(args))
        viewModel = retainedFragment.viewModel
        if (viewModel == null) {
            viewModel = container.createViewModel()
            retainedFragment.viewModel = viewModel
            var model: Parcelable? = null
            if (state != null) {
                model = state.getParcelable(ViewModel.MODEL)
            }
            viewModel?.initArgumentAndModel(args as Nothing?, model as Nothing?)
        }
        viewModel?.attachActivity(container)
        return viewModel
    }

    fun resume() {
        viewModel?.resume()
    }

    fun pause() {
        viewModel?.pause()
    }

    fun destroy() {
        viewModel?.detachView()
    }

    fun saveState(outState: Bundle) {
        outState.putParcelable(ViewModel.MODEL, viewModel?.model)
    }

    fun onBackPressed(activity: Activity) {
        val result = viewModel?.onBackPressed()
        if (result != null) {
            val intent = Intent()
            intent.putExtra(RESULT_DATA, result.getData() as Parcelable)
            activity.setResult(if (result.isResultOk) Activity.RESULT_OK else Activity.RESULT_CANCELED, intent)
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val resultData = data?.getParcelableExtra<Parcelable>(RESULT_DATA)
        viewModel?.onResult(requestCode, ActivityResult(resultCode, resultData!!))
    }

    fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater): Boolean {
        val menuId: Int = viewModel?.optionMenuId!!
        return if (menuId > 0) {
            menuInflater.inflate(menuId, menu)
            true
        } else {
            false
        }
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        return viewModel!!.onOptionsItemSelected(item.itemId)
    }

    companion object {
        const val RESULT_DATA = "RESULT_DATA"
    }
}
