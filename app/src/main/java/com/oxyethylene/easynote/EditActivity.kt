package com.oxyethylene.easynote

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.style.MIUIStyle
import com.oxyethylene.easynote.common.arrays.headerSizeList
import com.oxyethylene.easynote.ui.editactivity.EditActionBarButton
import com.oxyethylene.easynote.ui.editactivity.EditActionBarMenu
import com.oxyethylene.easynote.ui.editactivity.EditPageTopBar
import com.oxyethylene.easynote.ui.editactivity.TitleLine
import com.oxyethylene.easynote.ui.theme.BackGround
import com.oxyethylene.easynote.ui.theme.EasyNoteTheme
import com.oxyethylene.easynote.util.FileUtil
import com.oxyethylene.easynote.util.NoteUtil
import jp.wasabeef.richeditor.RichEditor

class EditActivity : ComponentActivity() {

    // 富文本编辑器
    private lateinit var richEditor: RichEditor

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            EasyNoteTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackGround
                ) {
                    val keyboardController = LocalSoftwareKeyboardController.current
                    Column(
                        modifier = Modifier.statusBarsPadding().fillMaxWidth().wrapContentHeight()
                    ) {
                        Column{
                            EditPageTopBar("")
                            TitleLine(NoteUtil.getTitle(), Modifier.padding(top = 10.dp))
                            Row (
                                modifier = Modifier.padding(top = 10.dp)
                                    .padding(start = 10.dp, end = 10.dp)
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .horizontalScroll(rememberScrollState())
                            ) {
                                // 标题文字
                                EditActionBarMenu(headerSizeList, false) {
                                    resId, optionName ->
                                        when (optionName) {
                                            "一号标题" -> richEditor.setHeading(1)
                                            "二号标题" -> richEditor.setHeading(2)
                                            "三号标题" -> richEditor.setHeading(3)
                                            "四号标题" -> richEditor.setHeading(4)
                                            "五号标题" -> richEditor.setHeading(5)
                                            "六号标题" -> richEditor.setHeading(6)
                                            else -> Toast.makeText(this@EditActivity, "未知错误，请检查程序代码", Toast.LENGTH_SHORT).show()
                                        }
                                }
                                // 字体加粗
                                EditActionBarButton(R.mipmap.ic_set_bold) {
                                    richEditor.setBold()
                                }
                                // 字体倾斜
                                EditActionBarButton(R.mipmap.ic_set_italic) {
                                    richEditor.setItalic()
                                }
                                // 添加下划线
                                EditActionBarButton(R.mipmap.ic_set_underline) {
                                    richEditor.setUnderline()
                                }
                                // 添加删除线
                                EditActionBarButton(R.mipmap.ic_set_strike_through) {
                                    richEditor.setStrikeThrough()
                                }
                                // 左对齐
                                EditActionBarButton(R.mipmap.ic_set_align_left) {
                                    richEditor.setAlignLeft()
                                }
                                // 右对齐
                                EditActionBarButton(R.mipmap.ic_set_align_right) {
                                    richEditor.setAlignRight()
                                }
                                // 居中对齐
                                EditActionBarButton(R.mipmap.ic_set_align_center) {
                                    richEditor.setAlignCenter()
                                }
                                // 撤销
                                EditActionBarButton(R.mipmap.ic_set_undo) {
                                    richEditor.undo()
                                }
                                // 重做
                                EditActionBarButton(R.mipmap.ic_set_redo) {
                                    richEditor.redo()
                                }
                                // 插入图片
                                EditActionBarButton(R.mipmap.ic_insert_photo) {
                                    PopNotification.build(MIUIStyle()).setMessage("该功能待完成!").show()
                                }
                                // 插入视频
                                EditActionBarButton(R.mipmap.ic_insert_video) {
                                    PopNotification.build(MIUIStyle()).setMessage("该功能待完成!").show()
                                }
                                // 插入音频
                                EditActionBarButton(R.mipmap.ic_insert_audio) {
                                    PopNotification.build(MIUIStyle()).setMessage("该功能待完成!").show()
                                }
                            }
                        }
//                        Box(Modifier) {
                            AndroidView(
                                modifier = Modifier.navigationBarsPadding().imePadding().padding(start = 30.dp, end = 30.dp, top = 10.dp).fillMaxSize(),
                                factory = { context ->
                                        LayoutInflater.from(context).inflate(R.layout.richtext_editor_layout, null).apply {
                                            richEditor = findViewById(R.id.editor)
                                            richEditor.setBackgroundColor(Color.Transparent.toArgb())
                                            // 加载已有内容
                                            richEditor.html = NoteUtil.loadFile(this@EditActivity)
                                            // 修改富文本编辑器的界面布局
                                            richEditor.loadCSS("editor.css")
                                        }
                                    }
                            )

//                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        richEditor.html?.let { NoteUtil.saveFile(this, it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        richEditor.html?.let { NoteUtil.saveFile(this, it) }
        FileUtil.setNoteUpdateTime(NoteUtil.getNoteId())
    }

    override fun onStop() {
        super.onStop()
        richEditor.html?.let { NoteUtil.saveFile(this, it) }
    }

}