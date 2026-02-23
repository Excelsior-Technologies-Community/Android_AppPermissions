package com.ext.apppermission

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

object PermissionResultHandler {

    fun handleResult(
        activity: Activity,
        permissions: Array<out String>,
        grantResults: IntArray,
        callback: PermissionCallback
    ) {
        val denied = mutableListOf<String>()
        val permanentlyDenied = mutableListOf<String>()

        for (i in permissions.indices) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) {
                    permanentlyDenied.add(permissions[i])
                } else {
                    denied.add(permissions[i])
                }
            }
        }

        when {
            denied.isEmpty() && permanentlyDenied.isEmpty() -> callback.onGranted()
            permanentlyDenied.isNotEmpty() -> callback.onPermanentlyDenied(permanentlyDenied)
            else -> callback.onDenied(denied)
        }
    }
}