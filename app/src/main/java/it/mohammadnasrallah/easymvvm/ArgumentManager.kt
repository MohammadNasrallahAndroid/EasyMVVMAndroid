package it.mohammadnasrallah.easymvvm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import java.io.Serializable

object ArgumentManager {
    private const val ARGUMENT = "argument"
    fun readArgument(arguments: Bundle): Any? {
        return arguments[ARGUMENT]
    }

    fun writeArgument(intent: Intent, argument: Any?): Intent {
        if (argument != null) {
            if (argument is Int) {
                intent.putExtra(ARGUMENT, argument as Int?)
            } else if (argument is Float) {
                intent.putExtra(ARGUMENT, argument as Float?)
            } else if (argument is Double) {
                intent.putExtra(ARGUMENT, argument as Double?)
            } else if (argument is Long) {
                intent.putExtra(ARGUMENT, argument as Long?)
            } else if (argument is Parcelable) {
                intent.putExtra(ARGUMENT, argument as Parcelable?)
            } else if (argument is String) {
                intent.putExtra(ARGUMENT, argument as String?)
            } else if (argument is Serializable) {
                intent.putExtra(ARGUMENT, argument as Serializable?)
            } else {
                throw RuntimeException("Invalid argument of class " + argument.javaClass + ", it can't be stored in a bundle")
            }
        }
        return intent
    }

    fun writeArgument(bundle: Bundle, argument: Any?): Bundle {
        if (argument != null) {
            if (argument is Int) {
                bundle.putInt(ARGUMENT, (argument as Int?)!!)
            } else if (argument is Float) {
                bundle.putFloat(ARGUMENT, (argument as Float?)!!)
            } else if (argument is Double) {
                bundle.putDouble(ARGUMENT, (argument as Double?)!!)
            } else if (argument is Long) {
                bundle.putLong(ARGUMENT, (argument as Long?)!!)
            } else if (argument is Parcelable) {
                bundle.putParcelable(ARGUMENT, argument as Parcelable?)
            } else if (argument is String) {
                bundle.putString(ARGUMENT, argument as String?)
            } else if (argument is Serializable) {
                bundle.putSerializable(ARGUMENT, argument as Serializable?)
            } else {
                throw RuntimeException("Invalid argument of class " + argument.javaClass + ", it can't be stored in a bundle")
            }
        }
        return bundle
    }

    @Deprecated("Use {@link ActivityHolder#startActivity(Class, Object)} instead.", ReplaceWith(
        "activity?.startActivity(createIntent(activity, cls, argument))",
        "it.mohammadnasrallah.easymvvm.ArgumentManager.createIntent"
    )
    )
    fun <ARG, VM : ViewModel<ARG, *>?, A : ViewModelActivity<VM>?> startActivity(activity: Activity?, cls: Class<A>?, argument: ARG) {
        activity?.startActivity(createIntent(activity, cls, argument))
    }

    fun <ARG, VM : ViewModel<ARG, *>?, F : ViewModelFragment<VM>?> instantiateFragment(activity: Activity?, cls: Class<F>, argument: ARG): F {
        val args = Bundle()
        writeArgument(args, argument)
        return Fragment.instantiate(activity, cls.name, args) as F
    }

    @Deprecated("Use {@link ActivityHolder#startActivityForResult(Class, int, Object)} instead.", ReplaceWith(
        "activity?.startActivityForResult(createIntent(activity, cls, argument), requestCode)",
        "it.mohammadnasrallah.easymvvm.ArgumentManager.createIntent"
    )
    )
    fun <ARG, VM : ViewModel<ARG, *>?, A : ViewModelActivity<VM>?> startActivityForResult(
        activity: Activity?,
        cls: Class<A>?,
        requestCode: Int,
        argument: ARG
    ) {
        activity?.startActivityForResult(createIntent(activity, cls, argument), requestCode)
    }

    fun <ARG, VM : ViewModel<ARG, *>?, A : ViewModelActivity<VM>?> createIntent(activity: Activity?, cls: Class<A>?, argument: ARG): Intent {
        return writeArgument(Intent(activity, cls), argument)
    }
}
