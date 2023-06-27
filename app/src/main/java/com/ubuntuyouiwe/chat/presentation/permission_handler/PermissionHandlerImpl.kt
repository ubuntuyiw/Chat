package com.ubuntuyouiwe.chat.presentation.permission_handler

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import javax.inject.Inject

class PermissionHandlerImpl @Inject constructor (private val context: Activity) : PermissionHandler {

    companion object {
        private const val REQUEST_CODE = 0
    }

    override fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.POST_NOTIFICATIONS)) {
                ActivityCompat.requestPermissions(context as Activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_CODE)
            }
        }
    }
}