package com.ruurd.peruse.ui.dialogs.utils

import android.text.Editable

class DialogUtils {

    companion object {

        fun isEmpty(vararg editable: Editable?): Boolean {
            editable.forEach { e ->
                if (e == null) {
                    return true
                }
                if (e.toString().trim().isEmpty()) {
                    return true
                }
            }
            return false
        }
    }

}