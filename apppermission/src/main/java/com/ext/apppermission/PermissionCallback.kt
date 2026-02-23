package com.ext.apppermission

interface PermissionCallback {
    fun onGranted()
    fun onDenied(deniedPermissions: List<String>)
    fun onPermanentlyDenied(permanentlyDeniedPermissions: List<String>)
}