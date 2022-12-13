package it.mohammadnasrallah.easymvvm

import android.app.Activity
import android.content.Intent
import android.os.Parcelable

class ActivityHolder {
    private var viewModelContainer: ViewModelContainer<*>? = null
    fun setViewModelContainer(viewModelContainer: ViewModelContainer<*>?) {
        this.viewModelContainer = viewModelContainer
    }

    private val activity: Activity?
        get() = viewModelContainer?.activity

    fun startActivity(intent: Intent?) {
        viewModelContainer?.startActivity(intent)
    }

    fun startActivityForResult(intent: Intent?, requestCode: Int) {
        viewModelContainer?.startActivityForResult(intent, requestCode)
    }

    fun finishActivity() {
        val activity: Activity? = activity
        activity?.finish()
    }

    fun finishActivity(result: ActivityResult) {
        val activity: Activity? = activity
        val intent = Intent()
        intent.putExtra(ViewModelManager.RESULT_DATA, result.getData() as Parcelable)
        activity?.setResult(if (result.isResultOk) Activity.RESULT_OK else Activity.RESULT_CANCELED, intent)
        activity?.finish()
    }

    fun <ARG, VM : ViewModel<ARG, *>?, F : ViewModelFragment<VM>?> instantiateFragment(cls: Class<F>, argument: ARG): F {
        return ArgumentManager.instantiateFragment(activity, cls, argument)
    }

    fun <ARG, VM : ViewModel<ARG, *>?, A : ViewModelActivity<VM>?> startActivity(cls: Class<A>?, argument: ARG) {
        val activity: Activity? = activity
        activity?.startActivity(ArgumentManager.createIntent(activity, cls, argument))
    }

    fun <ARG, VM : ViewModel<ARG, *>?, A : ViewModelActivity<VM>?> startActivityForResult(cls: Class<A>?, requestCode: Int, argument: ARG) {
        val activity: Activity? = activity
        activity?.startActivityForResult(ArgumentManager.createIntent(activity, cls, argument), requestCode)
    }
}