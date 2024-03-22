package com.oxyethylene.easynote.ui.commonactivity

import android.view.View
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kongzue.dialogx.dialogs.BottomMenu
import com.kongzue.dialogx.dialogs.InputDialog
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.interfaces.OnBindView
import com.kongzue.dialogx.style.MIUIStyle
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.ui.components.ShowBindedNotesDialog
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.theme.LightBlue
import com.oxyethylene.easynote.util.KeywordUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.commonactivity
 * @ClassName    : KeywordPageUI.java
 * @createTime   : 2024/3/17 1:36
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
@Composable
fun KeywordPageUI () {

    var keywordMap by remember { mutableStateOf(KeywordUtil.getMap()) }

    val enable = true

    Column {

        SimpleTitleBar("关键词")

        Column (
            Modifier.padding(top = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {

            keywordMap.map.forEach {

                (key, keyword) -> KeywordCapsule(key, keyword, Modifier.padding(top = 10.dp, start = 30.dp, end = 30.dp)) {keywordMap = KeywordUtil.getMap()}

            }

            if (keywordMap.map.isNotEmpty()) Row (Modifier.padding(top = 10.dp, start = 30.dp, end = 30.dp, bottom = 10.dp).fillMaxWidth().height(0.6.dp).background(Color.LightGray)) {  }

            Text(text = "Tips: emoji 表情字节数是普通文字的两倍，所以最多只能输入 5 个 emoji", fontSize = 10.sp, color = Color.Gray, lineHeight = 12.sp, letterSpacing = 0.sp, modifier = Modifier.padding(start = 30.dp, end = 30.dp, bottom = 6.dp))

            OutlinedButton(
                modifier = Modifier.padding(start = 30.dp, bottom = 20.dp),
                border = BorderStroke(2.dp, LightBlue),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = {
                    if (enable) {
                        InputDialog.build(MIUIStyle())
                            .setTitle("创建关键词")
                            .setMessage("请输入关键词(不超过 10 个字)")
                            .setOkButton("添加")
                            .setOkButtonClickListener {
                                    _, _, inputStr ->
                                val keyword = inputStr.trim()
                                if (keyword.length <= 10) {
                                    KeywordUtil.addKeyword(keyword)
                                    keywordMap = KeywordUtil.getMap()
                                } else {
                                    PopNotification.build(MIUIStyle()).setMessage("关键词过长").show()
                                }
                                return@setOkButtonClickListener false
                            }
                            .setCancelButton("取消")
                            .show()
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = LightBlue)
                Text(text = "新建关键词", fontSize = 14.sp, color = Color.DarkGray, modifier = Modifier.padding(start = 4.dp))
            }

        }

    }

}

/**
 * 关键词胶囊，负责显示一个关键词
 * @param keyword 关键词的内容
 * @param modifier 胶囊的边距
 */
@Composable
fun KeywordCapsule (keywordId: Int, keyword: String, modifier: Modifier, onClick: () -> Unit) {

    Row (
        modifier = modifier
            .defaultMinSize(minWidth = 60.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(LightBlue)
            .clickable {
                onKeywordCapClick(keywordId, keyword, onClick)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row (Modifier.padding(start = 9.dp).size(18.dp).clip(CircleShape).border(3.dp, Color.White.copy(alpha = 0.8f), CircleShape).background(Color.White.copy(alpha = 0.86f))) {  }

        Text(
            text = keyword,
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier.padding(start = 10.dp, end = 20.dp)
        )

    }

}


fun onKeywordCapClick (keywordId: Int, keyword: String, onClick: () -> Unit) {

    BottomMenu.build(MIUIStyle())
        .setTitle(keyword)
        .setMenuList(arrayOf("修改关键词", "删除", "查看关联的文章"))
        .setOnMenuItemClickListener { _, text, _ ->
            when (text) {
                "修改关键词" -> {
                    InputDialog.build(MIUIStyle())
                        .setTitle("修改关键词")
                        .setMessage("请输入关键词(不超过 10 个字)")
                        .setOkButton("添加")
                        .setOkButtonClickListener {
                                _, _, inputStr ->
                            val word = inputStr.trim()
                            if (word.length <= 10) {
                                KeywordUtil.updateKeyword(keywordId, word)
                                onClick()
                            } else {
                                PopNotification.build(MIUIStyle()).setMessage("关键词过长").show()
                            }
                            return@setOkButtonClickListener false
                        }
                        .setCancelButton("取消")
                        .show()
                }
                "删除" -> {
                    // TODO 需要完善，因为还没关联文章
                    MessageDialog.build(MIUIStyle())
                        .setTitle("删除关键词")
                        .setMessage("是否删除本关键词？")
                        .setCancelButton("取消")
                        .setOkButton("确定")
                        .setOkButtonClickListener { _, _ ->
                            val res = KeywordUtil.deleteKeyword(keywordId)
                            if (res) onClick()
                            else PopNotification.build(MIUIStyle()).setMessage("该关键词有绑定的文章，不能删除").show()
                            return@setOkButtonClickListener false
                        }
                        .show()
                }
                "查看关联的文章" -> {
                    MessageDialog.build(MIUIStyle())
                        .setTitle("关联的文章")
                        .setCustomView(object : OnBindView<MessageDialog>(R.layout.show_binded_notes_dialog_layout) {
                            override fun onBind(dialog: MessageDialog?, v: View?) {
                                val composeView = v?.findViewById<ComposeView>(R.id.show_notes_compose_view)

                                composeView?.setContent { ShowBindedNotesDialog(keywordId) }
                            }
                        })
                        .setOkButton("确定")
                        .show()
                }
            }
            return@setOnMenuItemClickListener false
        }
        .show()


}