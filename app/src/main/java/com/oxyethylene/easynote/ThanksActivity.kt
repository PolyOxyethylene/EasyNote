package com.oxyethylene.easynote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.theme.BackGround
import com.oxyethylene.easynote.ui.theme.EasyNoteTheme

class ThanksActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val veryThanksList = this.resources.getStringArray(R.array.very_thanks_list)

        val betaOneList = this.resources.getStringArray(R.array.thanks_list_close_beta_1)

        val pageInfo = this.resources.getString(R.string.thanks_page_info)

        setContent {
            EasyNoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackGround
                ) {

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
            }
        }
    }
}
