package it.mohammadnasrallah.easymvvm

import android.app.Activity
import android.os.Parcelable

class ActivityResult {
    var resultCode: Int
        private set
    private var data: Parcelable

    constructor(resultCode: Int, data: Parcelable) {
        this.resultCode = resultCode
        this.data = data
    }

    constructor(resultOk: Boolean, data: Parcelable) {
        resultCode = if (resultOk) Activity.RESULT_OK else Activity.RESULT_CANCELED
        this.data = data
    }

    val isResultOk: Boolean
        get() = resultCode == Activity.RESULT_OK

    fun <T : Parcelable?> getData(): T {
        return data as T
    }
}
