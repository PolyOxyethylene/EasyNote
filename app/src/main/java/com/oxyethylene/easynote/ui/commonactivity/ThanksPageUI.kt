package com.oxyethylene.easynote.ui.commonactivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.ui.components.SimpleTitleBar

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.commonactivity
 * @ClassName    : ThanksPageUI.java
 * @createTime   : 2024/3/15 23:41
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 鸣谢界面
 */
@Composable
fun ThanksPageUI () {

    val context = LocalContext.current

//        val veryThanksList = context.resources.getStringArray(R.array.very_thanks_list)

    val betaOneList = context.resources.getStringArray(R.array.thanks_list_close_beta_1)

    val pageInfo = context.resources.getString(R.string.thanks_page_info)

    Column (Modifier.fillMaxSize()) {

        SimpleTitleBar(title = "测试人员感谢名单")

//                        Box (Modifier.fillMaxSize()) {

        Column (
            modifier = Modifier.padding(start = 40.dp, end = 40.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = pageInfo,
                color = Color.Gray,
                fontSize = 12.sp,
//                                    letterSpacing = 0.sp,
                lineHeight = 16.sp,
                modifier = Modifier.padding(top = 10.dp)
            )

//                                Text(
//                                    text = "鸣谢",
//                                    color = Color.DarkGray,
//                                    fontSize = 24.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    fontFamily = FontFamily(Font(R.font.smileysans_oblique)),
//                                    modifier = Modifier.padding(top = 20.dp)
//                                )
//
//                                veryThanksList.forEach {
//                                    Text(
//                                        text = it,
//                                        color = Color.Gray,
//                                        fontSize = 20.sp,
//                                        fontFamily = FontFamily(Font(R.font.smileysans_oblique)),
//                                        modifier = Modifier.padding(top = 10.dp)
//                                    )
//                                }

            Text(
                text = "第一次内测参与者",
                color = Color.DarkGray,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
//                                    fontFamily = FontFamily(Font(R.font.smileysans_oblique)),
                modifier = Modifier.padding(top = 20.dp)
            )

            betaOneList.forEachIndexed {
                    index, name ->
                if (index % 2 == 0) {
                    Text(
                        text = "${betaOneList[index]}   ${if(index + 1 != betaOneList.size) betaOneList[index+1] else ""}",
                        color = Color.Gray,
                        fontSize = 14.sp,
//                                            fontFamily = FontFamily(Font(R.font.smileysans_oblique)),
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }

        }

//                        }

    }

}