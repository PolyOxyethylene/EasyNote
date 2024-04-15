package com.oxyethylene.easynote.ui.commonactivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.theme.SkyBlue

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.commonactivity
 * @ClassName    : LicenseInfoPageUI.java
 * @createTime   : 2024/3/15 23:33
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 开放源代码许可界面
 */
@Composable
fun LicenseInfoPageUI () {

    val context = LocalContext.current

    val openSourceInfo = context.resources.getString(R.string.open_source_license)

    // 开放源代码信息列表
    val softwareInfoList = context.resources.getStringArray(R.array.open_source_software_info)

    Column {
        SimpleTitleBar("开放源代码许可")

        Column(
            modifier = Modifier.padding(top = 10.dp, start = 30.dp, end = 30.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(openSourceInfo, fontSize = 12.sp, color = Color.Gray, lineHeight = 14.sp, letterSpacing = 0.sp, modifier = Modifier.padding(bottom = 20.dp))

            softwareInfoList.forEach {
                    info ->
                val i = info.indexOf('^')
                Text(info.substring(0, i), color = Color.DarkGray, fontSize = 14.sp, fontWeight = FontWeight.Bold, lineHeight = 16.sp, letterSpacing = 0.sp, modifier = Modifier.padding(top = 8.dp, bottom = 0.dp))
                Text(info.substring(i+1), color = SkyBlue, fontSize = 12.sp, lineHeight = 14.sp, letterSpacing = 0.sp, textDecoration = TextDecoration.Underline)
            }

            Row(Modifier.fillMaxWidth().height(20.dp)) {  }

        }
    }

}