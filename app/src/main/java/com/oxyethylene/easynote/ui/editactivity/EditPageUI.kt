package com.oxyethylene.easynote.ui.editactivity

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.components.TextArea
import com.oxyethylene.easynote.ui.theme.AliceBlue
import com.oxyethylene.easynote.ui.theme.BackGround
import com.oxyethylene.easynote.ui.theme.GreyDarker
import com.oxyethylene.easynote.ui.theme.SkyBlue
import com.oxyethylene.easynote.util.FileUtil
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
 *  编辑界面的 ui 界面
 *  @param viewModel 编辑活动的 viewModel
 */
@Composable
fun EditPageArea() {

    var input = rememberSaveable { mutableStateOf(NoteUtil.noteContent) }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(BackGround),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        EditPageTopBar("")

        TitleLine(NoteUtil.getTitle(), Modifier.padding(top = 10.dp))

        EditableArea(input)

    }

}

/**
 *  顶部导航栏
 *  @param title 顶部的标题
 *  @param modifier 设置导航栏外观
 */
@Composable
fun EditPageTopBar (title: String, modifier: Modifier = Modifier) = SimpleTitleBar(title, modifier)

/**
 *  文章的标题栏
 *  @param noteTitle 文章的标题
 *  @param modifier 定制组件外观
 */
@Composable
fun TitleLine(noteTitle: String, modifier: Modifier) {

    Column (
        modifier = modifier.fillMaxWidth().wrapContentHeight().padding(start = 30.dp)
    ) {
        Text(
            text = noteTitle,
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(text = FileUtil.getNoteUpdateTime(NoteUtil.getNoteId()), fontSize = 10.sp, color = GreyDarker, modifier = Modifier.padding(top = 10.dp))
    }

}

@Composable
fun EditableArea(input: MutableState<String>) {

    Column (modifier = Modifier.padding(20.dp).navigationBarsPadding()) {
        TextArea(
            Modifier,
            input,
            true
        ) {
            input.value = it
            NoteUtil.noteContent = it
        }
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
                    .background(AliceBlue)
                    .padding(top = 6.dp, bottom = 6.dp, start = 10.dp, end = 10.dp)
            ) {

                Text(
                    text = "右划更多工具",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SkyBlue
                )

            }

        }

    }

}