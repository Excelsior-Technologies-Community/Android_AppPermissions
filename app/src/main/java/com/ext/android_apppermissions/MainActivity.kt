package com.ext.android_apppermissions

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ext.apppermission.PermissionManager
import com.ext.apppermission.PermissionResultHandler
import com.ext.apppermission.PermissionCallback

class MainActivity : AppCompatActivity(), PermissionCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        PermissionManager.request(
            activity = this,
            permissions = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            callback = this
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1001) {
            PermissionResultHandler.handleResult(
                activity = this,
                permissions = permissions,
                grantResults = grantResults,
                callback = this
            )
        }
    }

    override fun onGranted() {
        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
    }

    override fun onDenied(deniedPermissions: List<String>) {
        Toast.makeText(this, "Denied: $deniedPermissions", Toast.LENGTH_SHORT).show()
    }

    override fun onPermanentlyDenied(permanentlyDeniedPermissions: List<String>) {
        Toast.makeText(this, "Permanently Denied: $permanentlyDeniedPermissions", Toast.LENGTH_SHORT).show()
    }
}