package com.licious.sample.design.ui.permission

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 *  This class is used for Android runtime permissions handling.
 */
class PermissionUtil {
    private var permissionName: String = ""
    private var permissionListener: IGetPermissionListener? = null

    /**
     *  Handle single permission results.
     */
    fun handleSinglePermissionResult(activity: AppCompatActivity, isGranted: Boolean) {
        when {
            isGranted -> {
                permissionListener?.onPermissionGranted()
            }
            isShouldShowRequestPermissionRationale(activity, permissionName) -> {
                permissionListener?.onPermissionRationale()
            }
            else -> {
                permissionListener?.onPermissionDenied()
            }
        }
    }

    /**
     *  Register listener to get updates on permission.
     */
    fun setPermissionListener(permissionListener: IGetPermissionListener) {
        this.permissionListener = permissionListener
    }

    /**
     *  Handle multiple permission results.
     */
    fun handleMultiPermissionResult(
        activity: AppCompatActivity,
        permissions: Map<String, @JvmSuppressWildcards Boolean>
    ) {
        var isGranted = true
        permissions.entries.forEach {
            if (!it.value) {
                isGranted = false
                return@forEach
            }
        }
        when {
            isGranted -> {
                permissionListener?.onPermissionGranted()
            }
            isShouldShowRequestPermissionRationale(activity, permissions) -> {
                permissionListener?.onPermissionRationale()
            }
            else -> {
                permissionListener?.onPermissionDenied()
            }
        }
    }

    /***
     *  Check the requested permission is granted ot not.
     */
    fun hasPermission(context: Context, permissionName: String): Boolean {
        if (ContextCompat.checkSelfPermission(
                context,
                permissionName
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }

    /***
     *  Check the requested permission is granted ot not.
     */
    fun hasMultiPermissions(context: Context, permissions: Array<String>): Boolean {
        permissions.forEach {
            if (!hasPermission(context, it)) {
                return false
            }
        }
        return true
    }

    /**
     *  Ask user for single permission.
     */
    fun requestPermission(permission: String, requestPermissions: ActivityResultLauncher<String>) {
        permissionName = permission
        requestPermissions.launch(permission)
    }

    /**
     * Ask user for multiple permission for seamless experience of the App features.
     */
    fun requestMultiplePermissions(
        permissions: Array<String>,
        requestMultiplePermissions: ActivityResultLauncher<Array<String>>
    ) {
        requestMultiplePermissions.launch(permissions)
    }

    /**
     *  Open App setting page to allow permission manually.
     */
    fun openAppSettingPage(
        activity: AppCompatActivity,
        resultLauncher: ActivityResultLauncher<Intent>
    ) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", activity.packageName, null)
        )
        resultLauncher.launch(intent)
    }

    /**
     *  Check if user select "Never ask again" option on the permission dialog.
     *  If it is true that means user have to go on setting to enable permission manually.
     */
    private fun isShouldShowRequestPermissionRationale(
        activity: AppCompatActivity,
        permission: String
    ): Boolean {
        return !ActivityCompat.shouldShowRequestPermissionRationale(
            activity, permission
        ) && ContextCompat.checkSelfPermission(
            activity,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    }

    /**
     *  Check rationale case for multiple permission.
     */
    private fun isShouldShowRequestPermissionRationale(
        activity: AppCompatActivity,
        permissions: Map<String, Boolean>
    ): Boolean {
        permissions.entries.forEach {
            val isRationale = isShouldShowRequestPermissionRationale(activity, it.key)
            if (isRationale) {
                permissionName = it.key
                return isRationale
            }
        }
        return false
    }
}