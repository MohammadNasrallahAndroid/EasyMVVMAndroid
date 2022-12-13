package it.mohammadnasrallah.easymvvm

import android.app.Activity
import android.os.Parcelable

abstract class ViewModel<A, M : Parcelable?> {
    var model: M? = null
        protected set
    var argument: A? = null
        protected set
    private val activityHolder = ActivityHolder()

    @Deprecated("Use activityHolder.getActivity() instead.")
    protected var activity: Activity? = null
    fun pause() {}
    fun resume() {}
    fun destroy() {}
    fun detachView() {
        activityHolder.setViewModelContainer(null)
        activity = null
    }

    protected abstract fun createModel(): M
    fun initArgumentAndModel(arguments: A?, model: M?) {
        argument = arguments
        this.model = model ?: createModel()
    }

    @JvmOverloads
    fun initAndResume(arguments: A? = null): M? {
        initArgumentAndModel(arguments, null)
        resume()
        return model
    }

    fun attachActivity(view: ViewModelContainer<*>) {
        activityHolder.setViewModelContainer(view)
        activity = view.activity
    }

    fun onBackPressed(): ActivityResult? {
        return null
    }

    fun onResult(requestCode: Int, activityResult: ActivityResult?) {}
    val optionMenuId: Int
        get() = -1

    fun onOptionsItemSelected(itemId: Int): Boolean {
        return false
    }

    companion object {
        const val MODEL = "model"
    }
}
