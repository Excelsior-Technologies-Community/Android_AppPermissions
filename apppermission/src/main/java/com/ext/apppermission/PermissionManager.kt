package com.ext.apppermission

import android.app.Activity
import androidx.core.app.ActivityCompat

object PermissionManager {

    fun request(
        activity: Activity,
        permissions: Array<String>,
        callback: PermissionCallback
    ) {
        ActivityCompat.requestPermissions(activity, permissions, 1001)

        // You will handle result in activity (next step)
    }
}