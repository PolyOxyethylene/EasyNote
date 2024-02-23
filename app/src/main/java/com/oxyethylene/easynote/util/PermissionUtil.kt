package com.oxyethylene.easynote.util

import android.Manifest
import android.os.Build
import androidx.fragment.app.FragmentActivity

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.util
 * @ClassName    : PermissionUtil.java
 * @createTime   : 2024/2/23 17:06
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  : 用于申请应用所需的运行时权限
 */
object PermissionUtil {

    // 请求的运行时权限列表
    val requestList = ArrayList<String>().apply {
        add(Manifest.permission.WRITE_EXTERNAL_STORAGE) // 写外部存储
        add(Manifest.permission.READ_EXTERNAL_STORAGE)  // 读外部存储

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.READ_MEDIA_AUDIO)
            add(Manifest.permission.READ_MEDIA_VIDEO)
            add(Manifest.permission.READ_MEDIA_IMAGES)
        }
    }

    fun init(activity: FragmentActivity) {
//        PermissionX.init(activity)
//            .permissions(requestList)
//            .request { allGranted, grantedList, deniedList ->
//                if (allGranted) {
//                    PopNotification.build(MIUIStyle()).setMessage("所有申请的权限都已通过").show()
//                } else {
//                    // 没同意完就结束应用
//                    PopNotification.build(MIUIStyle()).setMessage("请求失败了").show()
////                    activity.finish()
//                }
//            }
    }

}