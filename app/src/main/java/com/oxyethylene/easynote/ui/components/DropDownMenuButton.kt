package com.oxyethylene.easynote.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.saket.cascade.CascadeDropdownMenu

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.components
 * @ClassName    : DropDownMenuButton.java
 * @createTime   : 2024/3/4 0:02
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  : 点击打开一个下拉菜单的普通样式按钮
 */
/**
 * 点击打开一个下拉菜单的普通样式按钮
 * @param modifier 修改按钮本体的外观
 * @param colors 按钮的颜色设置
 * @param menuItems 菜单列表的文字
 * @param onMenuItemClick 点击菜单项执行的动作
 * @param content 按钮的内容
 */
@Composable
fun DropDownMenuButton (
    modifier: Modifier = Modifier,
    colors: ButtonColors,
    menuItems: List<Pair<Int, String>>,
    onMenuItemClick: (String) -> Unit,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box{
        Button(
            modifier = modifier,
            colors = colors,
            onClick = {expanded = true}
        ) {
            content()
        }
        CascadeDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            shape = RoundedCornerShape(12.dp),
        ) {
            menuItems.forEach {
                item ->
                DropdownMenuItem(
                    leadingIcon = { Image(painter = painterResource(item.first), contentDescription = "", modifier = Modifier.size(18.dp)) },
                    text = {
                        Row (verticalAlignment = Alignment.CenterVertically) {
                            Text(text = item.second, fontSize = 14.sp, color = Color.DarkGray, maxLines = 1)
                        }
                    },
                    onClick = {
                        expanded = false
                        onMenuItemClick(item.second)
                    },
                    contentPadding = PaddingValues(10.dp)
                )
            }
        }

    }
}