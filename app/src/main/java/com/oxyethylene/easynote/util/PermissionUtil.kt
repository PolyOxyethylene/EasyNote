package com.oxyethylene.easynote.util

import android.Manifest
import android.os.Build

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

    /**
     * 主界面初始化时执行，请求所需运行时权限
     * 如果权限请求未通过的话直接结束活动
     * @param activity 主活动
     */
//    fun init(activity: FragmentActivity) {
//        PermissionX.init(activity)
//            .permissions(requestList)
//            .request { allGranted, grantedList, deniedList ->
//                if (!allGranted) {
//                    MessageDialog.build(MIUIStyle())
//                        .setTitle("关于权限设置")
//                        .setMessageTextInfo(TextInfo().setGravity(Gravity.CENTER))
//                        .setMessage(R.string.permission_request_info)
//                        .setCustomView(object : OnBindView<MessageDialog>(R.layout.permission_dialog_layout){
//                            override fun onBind(dialog: MessageDialog?, v: View?) {
//                                val composeView = v?.findViewById<ComposeView>(R.id.permission_dialog_compose_view)
//
//                                composeView?.setContent { PermissionDialog() }
//                            }
//                        })
//                        .setCancelButton("拒绝")
//                        .setCancelButtonClickListener(object : OnDialogButtonClickListener<MessageDialog> {
//                            override fun onClick(dialog: MessageDialog?, v: View?): Boolean {
////                                activity.finish()
//                                return false
//                            }
//                        })
//                        .setOkButton("同意")
//                        .setOkButtonClickListener(object : OnDialogButtonClickListener<MessageDialog> {
//                            override fun onClick(dialog: MessageDialog?, v: View?): Boolean {
//                                val intent = Intent()
//                                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                                val uri = Uri.fromParts("package", activity.packageName, null)
//                                intent.data = uri
//                                activity.startActivity(intent)
//                                return false
//                            }
//                        })
//                        .show()
//                }
//            }
//    }

    /**
     * 对应用所请求权限是否全部通过的再检查
     * @param activity 主活动
     */
//    fun checkAgain (activity: FragmentActivity) {
//        PermissionX.init(activity)
//            .permissions(requestList)
//            .request { allGranted, grantedList, deniedList ->
//                if (!allGranted) {
//                    activity.finish()
//                }
//            }
//    }

}