package com.ext.apppermission

import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

class PermissionManager private constructor() {

    private var permissions: List<String> = emptyList()

    private var onGranted: (() -> Unit)? = null
    private var onDenied: ((List<String>) -> Unit)? = null
    private var onPermanentlyDenied: ((List<String>) -> Unit)? = null
    private var onRationaleNeeded: ((List<String>) -> Unit)? = null

    private var launcher: ActivityResultLauncher<Array<String>>? = null
    private var activity: ComponentActivity? = null
    private var fragment: Fragment? = null

    companion object {

        fun with(activity: ComponentActivity): PermissionManager {
            val manager = PermissionManager()
            manager.activity = activity
            manager.initLauncher(activity)
            return manager
        }

        fun with(fragment: Fragment): PermissionManager {
            val manager = PermissionManager()
            manager.fragment = fragment
            manager.initLauncher(fragment)
            return manager
        }
    }

    // ✅ Register launcher safely
    private fun initLauncher(owner: ComponentActivity) {
        launcher = owner.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            handleResult(result)
        }
    }

    private fun initLauncher(owner: Fragment) {
        launcher = owner.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            handleResult(result)
        }
    }

    // DSL functions
    fun permissions(vararg permissions: String) = apply {
        this.permissions = permissions.toList()
    }

    fun onGranted(callback: () -> Unit) = apply {
        this.onGranted = callback
    }

    fun onDenied(callback: (List<String>) -> Unit) = apply {
        this.onDenied = callback
    }

    fun onPermanentlyDenied(callback: (List<String>) -> Unit) = apply {
        this.onPermanentlyDenied = callback
    }

    fun onRationaleNeeded(callback: (List<String>) -> Unit) = apply {
        this.onRationaleNeeded = callback
    }

    fun request() {
        val deniedBeforeRequest = permissions.filter {
            val context = activity ?: fragment?.requireContext()
            !ActivityCompat.shouldShowRequestPermissionRationale(
                activity ?: fragment!!.requireActivity(),
                it
            )
        }

        if (deniedBeforeRequest.isNotEmpty()) {
            onRationaleNeeded?.invoke(deniedBeforeRequest)
        }

        launcher?.launch(permissions.toTypedArray())
    }

    private fun handleResult(result: Map<String, Boolean>) {
        val denied = mutableListOf<String>()
        val permanentlyDenied = mutableListOf<String>()

        val hostActivity = activity ?: fragment?.requireActivity()

        result.forEach { (permission, granted) ->
            if (!granted) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        hostActivity!!,
                        permission
                    )
                ) {
                    permanentlyDenied.add(permission)
                } else {
                    denied.add(permission)
                }
            }
        }

        when {
            denied.isEmpty() && permanentlyDenied.isEmpty() -> onGranted?.invoke()
            permanentlyDenied.isNotEmpty() -> onPermanentlyDenied?.invoke(permanentlyDenied)
            else -> onDenied?.invoke(denied)
        }
    }
}