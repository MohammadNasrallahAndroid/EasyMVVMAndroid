package it.mohammadnasrallah.easymvvm

import android.app.Activity
import android.content.Intent

interface ViewModelContainer<VM : ViewModel<*, *>?> {
    fun createViewModel(): VM
    fun getFragmentTag(args: Any?): String?
    val activity: Activity?

    fun startActivity(intent: Intent?)
    fun startActivityForResult(intent: Intent?, requestCode: Int)
}