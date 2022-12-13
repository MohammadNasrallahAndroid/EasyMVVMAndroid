package it.mohammadnasrallah.easymvvm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

abstract class ViewModelActivity<VM : ViewModel<*, *>?> : AppCompatActivity(), ViewModelContainer<VM> {
    private var vmManager: ViewModelManager<VM>? = null
    private var viewModel: VM? = null
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        vmManager = ViewModelManager()
        viewModel = vmManager!!.getOrCreate(this, state)
    }

    override fun getFragmentTag(args: Any?): String {
        return javaClass.name
    }

    override fun onResume() {
        super.onResume()
        vmManager?.resume()
    }

    override fun onPause() {
        super.onPause()
        vmManager?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        vmManager?.destroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        vmManager?.saveState(outState)
    }

    override fun onBackPressed() {
        vmManager?.onBackPressed(this)
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        vmManager?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return vmManager?.onCreateOptionsMenu(menu, menuInflater)!! || super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return vmManager?.onOptionsItemSelected(item)!! || super.onOptionsItemSelected(item)
    }

    override val activity: Activity
        get() = this
}
