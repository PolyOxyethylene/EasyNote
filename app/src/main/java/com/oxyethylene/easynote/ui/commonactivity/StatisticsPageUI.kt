package com.oxyethylene.easynote.ui.commonactivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.theme.GreyDarker
import com.oxyethylene.easynote.util.EventUtil
import com.oxyethylene.easynote.util.FileUtil
import com.oxyethylene.easynote.util.KeywordUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.commonactivity
 * @ClassName    : StatisticsActivityUI.java
 * @createTime   : 2024/3/15 22:52
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 统计界面
 */
@Composable
fun StatisticsPageUI () {

    Column {

        SimpleTitleBar(
            title = "统计"
        )

        Column(Modifier.verticalScroll(rememberScrollState())) {

            Column (
                modifier = Modifier.wrapContentHeight()
                    .padding(start = 20.dp, end = 20.dp, top = 25.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
            ) {

                HeadingWithTail("总数","${FileUtil.getDirCount() + FileUtil.getNoteCount() + EventUtil.getEventCount() + KeywordUtil.getKeywordCount()}", Modifier.padding(start = 20.dp, top = 20.dp))

                Row (
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp).fillMaxWidth()
                ) {
                    HeadingWithTail("目录", "${FileUtil.getDirCount()}")
                    HeadingWithTail("文章", "${FileUtil.getNoteCount()}", Modifier.padding(start = 100.dp))
                }

                Row (
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp).fillMaxWidth()
                ) {
                    HeadingWithTail("事件", "${EventUtil.getEventCount()}")
                    HeadingWithTail("关键词", "${KeywordUtil.getKeywordCount()}", Modifier.padding(start = 100.dp))
                }

            }

        }

    }

}

/**
 * 孩子不会起名字乱起的，就是一个大字标题后跟一个较小的描述文本
 * @param heading 标题
 * @param tail 尾部描述文本
 */
@Composable
fun HeadingWithTail (heading: String, tail: String, modifier: Modifier = Modifier) {

    Row (modifier) {
        Text(heading,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Text(tail,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = GreyDarker,
            modifier = Modifier.padding(start = 10.dp, top = 6.dp)
        )
    }

}