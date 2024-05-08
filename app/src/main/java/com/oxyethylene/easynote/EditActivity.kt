package com.oxyethylene.easynote

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.drake.net.utils.TipUtils
import com.kongzue.albumdialog.PhotoAlbumDialog
import com.kongzue.albumdialog.util.SelectPhotoCallback
import com.kongzue.filedialog.FileDialog
import com.kongzue.filedialog.interfaces.FileSelectCallBack
import com.oxyethylene.easynote.common.arrays.headerSizeList
import com.oxyethylene.easynote.common.arrays.listInsertList
import com.oxyethylene.easynote.common.arrays.textAlignList
import com.oxyethylene.easynote.richeditor.RichEditor
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.editactivity.AutoExtractionButton
import com.oxyethylene.easynote.ui.editactivity.EditActionBarButton
import com.oxyethylene.easynote.ui.editactivity.EditActionBarHelper
import com.oxyethylene.easynote.ui.editactivity.EditActionBarMenu
import com.oxyethylene.easynote.ui.editactivity.KeywordUtilButton
import com.oxyethylene.easynote.ui.editactivity.LocationInfoButton
import com.oxyethylene.easynote.ui.editactivity.TitleLine
import com.oxyethylene.easynote.ui.theme.BackGround
import com.oxyethylene.easynote.ui.theme.EasyNoteTheme
import com.oxyethylene.easynote.util.DateUtil
import com.oxyethylene.easynote.util.FileUtil
import com.oxyethylene.easynote.util.KeywordUtil
import com.oxyethylene.easynote.util.NoteUtil
import com.oxyethylene.easynote.util.SettingUtil
import com.oxyethylene.easynote.util.dpToPx
import java.io.File

class EditActivity : ComponentActivity() {

    /**
     * 富文本编辑器
     */
    private lateinit var richEditor: RichEditor

    /**
     * 编辑器纯文本内容
     */
    private var plainText = ""

    /**
     * 文本是否被修改过
     */
    private var modified = false

    /**
     * 当前编辑的文章
     */
    private val note = FileUtil.getNote(NoteUtil.getNoteId())

    /**
     * 百度地图 SDK 请求客户端
     */
    private var mLocationClient: LocationClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            mLocationClient = LocationClient(applicationContext)
        } catch (ex : Exception) {
            TipUtils.toast(ex.message)
        }

        val option = LocationClientOption().apply {
            setIsNeedAddress(true)
        }

        mLocationClient?.locOption = option

        mLocationClient?.registerLocationListener(
            object : BDAbstractLocationListener() {
                override fun onReceiveLocation(location: BDLocation?) {
                    val updateTime = DateUtil.getCurrentDateTime()

                    val address = location?.addrStr ?: SettingUtil.defaultLocation

                    // 更新修改地点
                    NoteUtil.updateRecord(address, updateTime)

                    FileUtil.setNoteUpdateTime(NoteUtil.getNoteId(), updateTime)
                    mLocationClient!!.stop()
                }
            }
        )

        setContent {
            EasyNoteTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackGround
                ) {
                    // 工具栏的滑动状态
                    val scrollState = rememberScrollState()

                    // 用于显示的关键词集合
                    var keywordMap by remember { mutableStateOf(KeywordUtil.getBindedKeywords(note!!.keywordList)) }

                    Column(
                        modifier = Modifier.statusBarsPadding().fillMaxWidth().wrapContentHeight()
                    ) {
                        Column{
                            SimpleTitleBar("") {
                                if (SettingUtil.autoExtraction) {
                                    AutoExtractionButton(
                                        modifier = Modifier.align(Alignment.CenterVertically),
                                        onKeywordUpdate = { keywordMap = KeywordUtil.getBindedKeywords(note!!.keywordList) }
                                    ) { plainText }
                                }
                                if (SettingUtil.enableLocation) {
                                    LocationInfoButton(Modifier.align(Alignment.CenterVertically).padding(start = 20.dp))
                                }
                                KeywordUtilButton(NoteUtil.getNoteId()) {
                                    keywordMap = KeywordUtil.getBindedKeywords(note!!.keywordList)
                                }
                            }
                            Box {
                                TitleLine(note!!, keywordMap, Modifier.align(Alignment.TopStart).padding(top = 10.dp))
                                if (SettingUtil.showEditBarTip) EditActionBarHelper(Modifier.align(Alignment.BottomEnd).padding(end = 14.dp), scrollState)
                            }
                            Row (
                                modifier = Modifier.padding(top = 10.dp)
                                    .padding(start = 10.dp, end = 10.dp)
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .horizontalScroll(scrollState)
                            ) {
                                // 标题文字
                                EditActionBarMenu(headerSizeList, false) {
                                        _, optionName ->
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
                                // 文本对齐方式
                                EditActionBarMenu(textAlignList, false) {
                                    _, optionName ->
                                    when (optionName) {
                                        "居中对齐" -> richEditor.setAlignCenter()
                                        "两端对齐" -> richEditor.setAlignJustify()
                                        "左对齐" -> richEditor.setAlignLeft()
                                        "右对齐" -> richEditor.setAlignRight()
                                    }
                                }
                                // 插入列表
                                EditActionBarMenu(listInsertList, false) {
                                        _, optionName ->
                                    when (optionName) {
                                        "无序列表" -> richEditor.setBullets()
                                        "有序列表" -> richEditor.setNumbers()
                                    }
                                }
                                // 插入图片
                                EditActionBarButton(R.mipmap.ic_insert_photo) {
                                    PhotoAlbumDialog.build()
                                        .setMaxSelectPhotoCount(1)
                                        .setCompressQuality(80)
                                        .setCompressPhoto(true)
                                        .setClip(SettingUtil.clipMode)  // 在设置中选择是否开启裁切
                                        .setMaxSize(4000)
                                        .setMaxWidth(4000)
                                        .setMaxHeight(4000)
                                        .setCallback(
                                            object : SelectPhotoCallback() {
                                                override fun selectedPhoto(selectedPhotos: String?) {
                                                    richEditor.insertImage(selectedPhotos, "啊哈哈这个照片出错了")
                                                    richEditor.scrollToBottom()
                                                }
                                            }
                                        )
                                        .setDialogDialogImplCallback { dialog -> dialog.radius = dpToPx(this@EditActivity, 16) }
                                        .show(this@EditActivity)
                                }
                                // 插入视频
                                EditActionBarButton(R.mipmap.ic_insert_video) {
                                    FileDialog.build()
                                        .setShowFileDate(true)
                                        .setSuffixArray(arrayOf(".mp4"))
                                        .selectFile(
                                        object : FileSelectCallBack(){
                                            override fun onSelect(file: File?, filePath: String?) {
                                                richEditor.insertVideo(filePath, 200)
                                                richEditor.scrollToBottom()
                                            }
                                        }
                                    )
                                }
                                // 插入音频
                                EditActionBarButton(R.mipmap.ic_insert_audio) {
                                    FileDialog.build()
                                        .setShowFileDate(true)
                                        .setSuffixArray(arrayOf(".ogg", ".mp3", ".flac"))
                                        .selectFile(
                                        object : FileSelectCallBack(){
                                            override fun onSelect(file: File?, filePath: String?) {
                                                richEditor.insertAudio(filePath)
                                                richEditor.scrollToBottom()
                                            }
                                        }
                                    )
                                }
                                // 撤销
                                EditActionBarButton(R.mipmap.ic_set_undo) {
                                    richEditor.undo()
                                }
                                // 重做
                                EditActionBarButton(R.mipmap.ic_set_redo) {
                                    richEditor.redo()
                                }
                            }
                        }

                        AndroidView(
                            modifier = Modifier.navigationBarsPadding().imePadding().padding(start = 30.dp, end = 30.dp, top = 10.dp).fillMaxSize(),
                            factory = { context ->
                                    LayoutInflater.from(context).inflate(R.layout.richtext_editor_layout, null).apply {
                                        richEditor = findViewById(R.id.editor)
                                        richEditor.setBackgroundColor(Color.Transparent.toArgb())
                                        richEditor.settings.allowFileAccess = true
                                        richEditor.setOnTextChangeListener {
                                            // 更新纯文本内容
                                            plainText = richEditor.plainText
                                            // 更新修改标记
                                            modified = true
                                        }
                                        // 加载已有内容
                                        richEditor.html = NoteUtil.loadFile(this@EditActivity)
                                        // 初始化纯文本内容
                                        plainText = richEditor.plainText
                                        // 修改富文本编辑器的界面布局
                                        // 加载字体配置
                                        richEditor.loadCSS("fontfamily_${SettingUtil.fontFamily}.css")
                                        // 加载行高配置
                                        richEditor.loadCSS("line_h_${SettingUtil.lineHeight}.css")
                                        richEditor.scrollToBottom()
                                    }
                                }
                        )

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
        richEditor.pauseMediaPlayers()
        if (modified && SettingUtil.enableLocation) {
            mLocationClient?.start()
        }
    }

    override fun onStop() {
        super.onStop()
        richEditor.pauseMediaPlayers()
        richEditor.html?.let { NoteUtil.saveFile(this, it) }
    }

}