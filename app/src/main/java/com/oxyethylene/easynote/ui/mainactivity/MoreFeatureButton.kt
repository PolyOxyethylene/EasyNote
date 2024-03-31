package com.oxyethylene.easynote.ui.mainactivity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.interfaces.OnBindView
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener
import com.kongzue.dialogx.style.MIUIStyle
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.ui.components.MoreIcon
import com.oxyethylene.easynote.ui.components.PermissionDialog
import me.saket.cascade.CascadeDropdownMenu

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.mainactivity
 * @ClassName    : MoreFeatureButton.java
 * @createTime   : 2024/3/16 0:34
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 主页右上角的更多选项菜单
 */
@Composable
fun MoreFeatureButton () {

    var expanded by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Box{
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.DarkGray
            ),
            onClick = {expanded = true}
        ) {
            MoreIcon(Modifier.size(24.dp), Color.DarkGray)
        }
        CascadeDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            shape = RoundedCornerShape(12.dp),
        ) {

            // 关键词选项
            androidx.compose.material3.DropdownMenuItem(
                leadingIcon = { Image(painter = painterResource(R.mipmap.ic_keywords), contentDescription = "", modifier = Modifier.size(18.dp)) },
                text = {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "关键词", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)
                    }
                },
                onClick = {
                    expanded = false
                    val intent = Intent("com.oxyethylene.COMMON")
                    intent.`package` = context.packageName
                    intent.putExtra("title", "keywords")
                    context.startActivity(intent)
                },
                contentPadding = PaddingValues(10.dp)
            )

            // 导出文件选项
            DropdownMenuItem(
                leadingIcon = { Image(painter = painterResource(R.mipmap.ic_export), contentDescription = "", modifier = Modifier.size(18.dp)) },
                text = {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "导出为", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)
                    }
                },
                contentPadding = PaddingValues(10.dp),
                children = {

                    // 导出为 PDF
                    androidx.compose.material3.DropdownMenuItem(
                        leadingIcon = { Image(painter = painterResource(R.drawable.pdf_icon), contentDescription = "", modifier = Modifier.size(18.dp)) },
                        text = {
                            Row (verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "导出为 PDF", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)
                            }
                        },
                        onClick = {
                            expanded = false
                            PopNotification.build(MIUIStyle()).setMessage("该功能正在开发中，敬请期待!").show()
                        },
                        contentPadding = PaddingValues(10.dp)
                    )

                    // 导出为图片
                    androidx.compose.material3.DropdownMenuItem(
                        leadingIcon = { Image(painter = painterResource(R.drawable.image_icon), contentDescription = "", modifier = Modifier.size(18.dp)) },
                        text = {
                            Row (verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "导出为图片", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)
                            }
                        },
                        onClick = {
                            expanded = false
                            PopNotification.build(MIUIStyle()).setMessage("该功能正在开发中，敬请期待!").show()
                        },
                        contentPadding = PaddingValues(10.dp)
                    )

                    // 导出为纯文本
                    androidx.compose.material3.DropdownMenuItem(
                        leadingIcon = { Image(painter = painterResource(R.drawable.txt_icon), contentDescription = "", modifier = Modifier.size(18.dp)) },
                        text = {
                            Row (verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "导出为 txt", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)
                            }
                        },
                        onClick = {
                            expanded = false
                            PopNotification.build(MIUIStyle()).setMessage("该功能正在开发中，敬请期待!").show()
                        },
                        contentPadding = PaddingValues(10.dp)
                    )

                }
            )

            // 常见问题
            androidx.compose.material3.DropdownMenuItem(
                leadingIcon = { Image(painter = painterResource(R.mipmap.ic_qa), contentDescription = "", modifier = Modifier.size(18.dp)) },
                text = {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Q&A", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)
                    }
                },
                onClick = {
                    expanded = false
                    val intent = Intent("com.oxyethylene.COMMON")
                    intent.`package` = context.packageName
                    intent.putExtra("title", "questions")
                    context.startActivity(intent)
                },
                contentPadding = PaddingValues(10.dp)
            )

            // 权限检查
            androidx.compose.material3.DropdownMenuItem(
                leadingIcon = { Image(painter = painterResource(R.mipmap.ic_permission_check), contentDescription = "", modifier = Modifier.size(18.dp)) },
                text = {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "应用权限检查", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)
                    }
                },
                onClick = {
                    expanded = false
                    callPermissionCheck(context)
                },
                contentPadding = PaddingValues(10.dp)
            )
        }

    }

}

/**
 * 发起权限检查请求
 * @param context 当前栈顶的 Activity
 */
fun callPermissionCheck (context: Context) {

    MessageDialog.build(MIUIStyle())
        .setTitle("关于权限设置")
        .setCustomView(object : OnBindView<MessageDialog>(R.layout.permission_dialog_layout){
            override fun onBind(dialog: MessageDialog?, v: View?) {
                val composeView = v?.findViewById<ComposeView>(R.id.permission_dialog_compose_view)

                composeView?.setContent { PermissionDialog() }
            }
        })
        .setCancelButton("取消")
        .setCancelButtonClickListener { dialog, v -> false }
        .setOkButton("前往检查")
        .setOkButtonClickListener(object : OnDialogButtonClickListener<MessageDialog> {
            override fun onClick(dialog: MessageDialog?, v: View?): Boolean {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
                return false
            }
        })
        .show()

}