package com.oxyethylene.easynote.ui.editactivity

import android.annotation.SuppressLint
import android.view.View
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kongzue.dialogx.dialogs.BottomMenu
import com.kongzue.dialogx.dialogs.InputDialog
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.interfaces.OnBindView
import com.kongzue.dialogx.interfaces.OnBottomMenuButtonClickListener
import com.kongzue.dialogx.interfaces.OnIconChangeCallBack
import com.kongzue.dialogx.interfaces.OnMenuItemSelectListener
import com.kongzue.dialogx.style.MIUIStyle
import com.oxyethylene.easynote.R
import com.oxyethylene.easynote.domain.NoteFile
import com.oxyethylene.easynote.ui.components.ShowKeywordsDialog
import com.oxyethylene.easynote.ui.theme.GreyDarker
import com.oxyethylene.easynote.ui.theme.GreyLighter
import com.oxyethylene.easynote.ui.theme.LightBlue
import com.oxyethylene.easynote.util.FileUtil
import com.oxyethylene.easynote.util.KeywordUtil
import com.oxyethylene.easynote.util.NoteUtil
import me.saket.cascade.CascadeDropdownMenu

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.ui
 * @ClassName    : MainEditPageUI.java
 * @createTime   : 2023/12/20 20:00
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */


/**
 *  文章的标题栏
 *  @param noteTitle 文章的标题
 *  @param modifier 定制组件外观
 */
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun TitleLine(note: NoteFile, keywordMap: HashMap<String, Int>, modifier: Modifier) {

    Column (
        modifier = modifier.fillMaxWidth().wrapContentHeight().padding(start = 30.dp)
    ) {
        // 标题
        Text(
            text = note.fileName,
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold
        )

        // 显示前两个绑定的关键词
        Row (
            Modifier.padding(top = 4.dp)
                .clickable(
                    onClick =  {
                    MessageDialog.build(MIUIStyle())
                        .setTitle("相关关键词")
                        .setCustomView(object : OnBindView<MessageDialog>(R.layout.show_keyword_dialog_layout){
                            override fun onBind(dialog: MessageDialog?, v: View?) {
                                val composeView = v?.findViewById<ComposeView>(R.id.show_keywords_compose_view)

                                composeView?.setContent { ShowKeywordsDialog(keywordMap) }
                            }
                        })
                        .setOkButton("确定")
                        .show()
                    },
                    indication = null,
                    interactionSource = MutableInteractionSource()
                )
        ) {
            keywordMap.keys.forEachIndexed { index, keyword ->
                if (index < 2) {
                    Row (Modifier.wrapContentSize(Alignment.Center).padding(end = 10.dp).clip(RoundedCornerShape(4.dp)).background(GreyLighter)) {
                        Text(text = keyword, color = Color.DarkGray, fontSize = 12.sp, modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp))
                    }
                }
            }
            if (keywordMap.isNotEmpty()) Image(painter = painterResource(R.mipmap.ic_arrow_right), contentDescription = null, modifier = Modifier.align(Alignment.CenterVertically).size(14.dp))
        }

        Text(text = FileUtil.getNoteUpdateTime(NoteUtil.getNoteId()), fontSize = 10.sp, color = GreyDarker, modifier = Modifier.padding(top = 10.dp))
    }

}


/**
 * 编辑器功能栏的单个按钮
 * @param actionName 功能名称
 * @param function 按钮按下后的工作
 */
@Composable
fun EditActionBarButton (buttonIconResId: Int, function: () -> Unit = {}) {

    Box(
        modifier = Modifier.fillMaxHeight().width(50.dp)
            .clickable {
                function.invoke()
            }
    ) {
        Image(
            painter = painterResource(buttonIconResId),
            contentDescription = "编辑栏按钮",
            modifier = Modifier.size(24.dp).align(Alignment.Center)
        )
    }

}

/**
 * 编辑栏的另一种按钮，点按唤出菜单
 * @param menuList 菜单选项的内容，每个选项内容包含一个图标和一个文本描述，使用 Pair 来存储，Pair 第一个参数为图标对应的资源 id， 第二个参数为选项对应的文字内容
 * @param changeButtonIcon 是否在点击选项时顺便切换按钮的图标，如果为 false，需要指定 menuList[0] 为按钮的图标，其余为选项的图标
 * @param onItemClick 点击菜单选项之后执行的额外动作
 */
@Composable
fun EditActionBarMenu (menuList: List<Pair<Int, String>>, changeButtonIcon: Boolean, onItemClick: (Int, String) -> Unit) {

    // 菜单是否展开
    var expanded by remember { mutableStateOf(false) }

    // 编辑栏按钮显示的图标的 id
    var buttonIconResId by rememberSaveable { mutableStateOf(menuList[0].first) }

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxHeight()
                .width(50.dp)
                .clickable {
                    expanded = true
                }
        ) {
            Image(
                painter = painterResource(buttonIconResId),
                contentDescription = "编辑栏按钮",
                modifier = Modifier.size(24.dp).align(Alignment.Center)
            )
            CascadeDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                shape = RoundedCornerShape(12.dp)
            ) {
                menuList.subList(1, menuList.size).forEach {
                    pair ->
                    DropdownMenuItem(
                        leadingIcon = {Image(painter = painterResource(pair.first), contentDescription = "编辑栏菜单选项", modifier = Modifier.size(18.dp))},
                        text = { Text(pair.second) },
                        onClick = {
                            expanded = false
                            if (changeButtonIcon) buttonIconResId = pair.first
                            onItemClick.invoke(pair.first, pair.second)
                        },
                        contentPadding = PaddingValues(5.dp),
                    )
                }
            }

        }
    }
}

/**
 * 用于提示工具栏可以横划
 * @param modifier 设置提示的位置
 * @param state 工具栏的滑动状态
 */
@Composable
fun EditActionBarHelper (modifier: Modifier, state: ScrollState) {

    Crossfade(
        targetState = state.value in 0 .. 200,
        modifier = modifier,
        label = "",
        animationSpec = tween(durationMillis = 300)
    ) {

        if (it) {

            Row (
                Modifier.wrapContentWidth(Alignment.CenterHorizontally)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .clip(CircleShape)
                    .background(LightBlue)
                    .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp)
            ) {

                Text(
                    text = "右划更多工具",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

            }

        }

    }

}

/**
 * 编辑页右上角关键词功能按钮
 * @param noteId 当前编辑的文章的 id
 * @param onKeywordUpdate 当执行关键词相关的更新操作时，执行的额外操作
 */
@Composable
fun KeywordUtilButton (noteId: Int, onKeywordUpdate: () -> Unit = {}) {

    var expended by remember { mutableStateOf(false) }

    Box {

        Button (
            onClick = {expended = true},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Image(painter = painterResource(R.mipmap.ic_keywords), contentDescription = "", modifier = Modifier.size(18.dp))
        }

        CascadeDropdownMenu(
            expanded = expended,
            onDismissRequest = {expended = false},
            shape = RoundedCornerShape(12.dp)
        ) {

            // 绑定已有关键词
            DropdownMenuItem(
                text = {Text(text = "绑定已有关键词", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)},
                onClick = {
                    expended = false
                    showKeywordBindingDialog(noteId) {onKeywordUpdate()}
                }
            )

            // 新建一个关键词并绑定
            DropdownMenuItem(
                text = {Text(text = "新建一个关键词并绑定", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)},
                onClick = {
                    expended = false
                    InputDialog.build(MIUIStyle())
                            .setTitle("创建关键词")
                            .setMessage("请输入关键词(不超过 10 个字)")
                            .setOkButton("添加")
                            .setOkButtonClickListener {
                                    _, _, inputStr ->
                                val keyword = inputStr.trim()
                                if (keyword.length <= 10) {
                                    val keywordId = KeywordUtil.addKeyword(keyword)
                                    // 关键词和文章的绑定
                                    KeywordUtil.bindNote2Keyword(keywordId, noteId)
                                    FileUtil.bindKeyword2Note(keywordId, noteId)
                                } else {
                                    PopNotification.build(MIUIStyle()).setMessage("关键词过长").show()
                                }
                                onKeywordUpdate()
                                return@setOkButtonClickListener false
                            }
                            .setCancelButton("取消")
                            .show()
                }
            )

            // 解绑关键词
            DropdownMenuItem(
                text = {Text(text = "解除绑定关键词", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)},
                onClick = {
                    expended = false
                    showKeywordUnbindingDialog(noteId) {onKeywordUpdate()}
                }
            )

        }

    }

}

/**
 * 关键词和文章的绑定对话框
 * @param item 指定的文章
 * @param onKeywordUpdate 当执行关键词相关的更新操作时，执行的额外操作
 */
fun showKeywordBindingDialog (noteId: Int, onKeywordUpdate: () -> Unit = {}) {

    val map = KeywordUtil.getUnbindedKeywords(FileUtil.getNote(noteId)!!.keywordList)

    val menuList = ArrayList<CharSequence>().apply {
        addAll(map.keys)
    }

    var selectedKeywords: Array<out CharSequence>? = null

    BottomMenu.build(MIUIStyle())
        .setTitle("绑定已有关键词")
        .setMenuList(menuList)
        .setOnIconChangeCallBack(object : OnIconChangeCallBack<BottomMenu>(false){
            override fun getIcon(dialog: BottomMenu?, index: Int, menuText: String?): Int = R.drawable.tags_icon
        })
        .setOnMenuItemClickListener(object : OnMenuItemSelectListener<BottomMenu>() {
            override fun onMultiItemSelect(
                dialog: BottomMenu?,
                text: Array<out CharSequence>?,
                indexArray: IntArray?
            ) {
                selectedKeywords = text
            }
        })
        .setMultiSelection()
        .setOkButton("确认添加",
            OnBottomMenuButtonClickListener { _, _ ->
                selectedKeywords?.apply {
                    if (isNotEmpty()) {
                        forEach {
                            KeywordUtil.bindNote2Keyword(map[it]!!, noteId)
                            FileUtil.bindKeyword2Note(map[it]!!, noteId)
                        }
                    }
                    KeywordUtil.update()
                }
                onKeywordUpdate()
                return@OnBottomMenuButtonClickListener false
            })
        .setCancelButton("取消")
        .show()

}

/**
 * 关键词和文章的解除绑定对话框
 * @param item 指定的文章
 * @param onKeywordUpdate 当执行关键词相关的更新操作时，执行的额外操作
 */
fun showKeywordUnbindingDialog (noteId: Int, onKeywordUpdate: () -> Unit = {}) {

    val map = KeywordUtil.getBindedKeywords(FileUtil.getNote(noteId)!!.keywordList)

    val menuList = ArrayList<CharSequence>().apply {
        addAll(map.keys)
    }

    var selectedKeywords: Array<out CharSequence>? = null

    BottomMenu.build(MIUIStyle())
        .setTitle("解绑已有关键词")
        .setMenuList(menuList)
        .setOnIconChangeCallBack(object : OnIconChangeCallBack<BottomMenu>(false){
            override fun getIcon(dialog: BottomMenu?, index: Int, menuText: String?): Int = R.drawable.tags_icon
        })
        .setOnMenuItemClickListener(object : OnMenuItemSelectListener<BottomMenu>() {
            override fun onMultiItemSelect(
                dialog: BottomMenu?,
                text: Array<out CharSequence>?,
                indexArray: IntArray?
            ) {
                selectedKeywords = text
            }
        })
        .setMultiSelection()
        .setOkButton("确认解绑",
            OnBottomMenuButtonClickListener { _, _ ->
                selectedKeywords?.apply {
                    if (isNotEmpty()) {
                        val set = HashSet<Int>()
                        forEach {
                            KeywordUtil.unbindNoteFromKw(map[it]!!, noteId)
                            set.add(map[it]!!)
                        }
                        FileUtil.unbindKwFromNote(set, noteId)
                    }
                    KeywordUtil.update()
                }
                onKeywordUpdate()
                return@OnBottomMenuButtonClickListener false
            })
        .setCancelButton("取消")
        .show()

}