package com.oxyethylene.easynote.ui.commonactivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.theme.GreyLighter

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.commonactivity
 * @ClassName    : QuestionPageUI.java
 * @createTime   : 2024/3/17 1:23
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 常见问题界面
 */
@Composable
fun QuestionPageUI () {

    val context = LocalContext.current

    val questionList = context.resources.getStringArray(R.array.question_list)

    Column {

        SimpleTitleBar("常见问题")

        LazyColumn (Modifier.navigationBarsPadding().padding(bottom = 10.dp)) {
            items(questionList) { QuestionCard(it) }
        }

    }

}

@Composable
fun QuestionCard (QAndAContent: String) {

    val index = QAndAContent.indexOf('?')

    Column (
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 20.dp, start = 30.dp, end = 30.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
    ) {

        Text(
            text = QAndAContent.substring(0, index + 1),
            fontSize = 14.sp,
            color = Color.DarkGray,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 14.dp, bottom = 6.dp)
        )

        Row(Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp).height(0.6.dp).background(GreyLighter)) {  }

        Text(
            text = QAndAContent.substring(index + 1),
            fontSize = 12.sp,
            color = Color.Gray,
            lineHeight = 16.sp,
            modifier = Modifier.padding(top = 6.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
        )
    }

}