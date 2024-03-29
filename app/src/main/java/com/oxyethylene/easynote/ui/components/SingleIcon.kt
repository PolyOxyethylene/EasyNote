package com.oxyethylene.easynote.ui.components

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.oxyethylene.easynote.R

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.ui
 * @ClassName    : SingleIcon.java
 * @createTime   : 2023/12/12 23:17
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */

/**
 *  文件夹的图标
 */
@Composable
fun FolderIcon(_modifer : Modifier) = Image(painterResource(R.drawable.folder_icon), "这是一个目录", modifier = _modifer)

/**
 *  笔记的图标
 */
@Composable
fun FileIcon(_modifer : Modifier) = Image(painterResource(R.drawable.note_icon), "这是一个笔记", modifier = _modifer)

/**
 *  返回按钮的图标
 */
@Composable
fun BackIcon(_modifer: Modifier) = Icon(painterResource(R.mipmap.ic_back), "这是一个返回按钮", modifier = _modifer)


/**
 *  多选按钮的图标
 */
@Composable
fun MultiSelectIcon(_modifer: Modifier) = Icon(painterResource(R.mipmap.ic_multi_select), "这是一个多选按钮", modifier = _modifer)

/**
 *  更多设置按钮的图标
 */
@Composable
fun SettingIcon(_modifer: Modifier) = Icon(painterResource(R.mipmap.ic_more_setting), "这是一个设置按钮", modifier = _modifer)

/**
 *  搜索按钮的图标
 */
@Composable
fun SearchIcon(_modifer: Modifier) = Icon(painterResource(R.mipmap.ic_search), "这是一个搜索按钮", modifier = _modifer)

/**
 *  更多选项按钮的图标
 */
@Composable
fun MoreIcon(_modifer: Modifier, _color: Color) = Icon(imageVector = Icons.Default.MoreVert, contentDescription = "这是一个打开更多选项的按钮", modifier = _modifer, tint = _color)

/**
 *  右向箭头图标
 */
@Composable
fun ArrowRightIcon(_modifer: Modifier) = Image(painterResource(R.mipmap.ic_arrow_right), "这是一个修改(重命名)按钮", modifier = _modifer)

/**
 *  事件图标
 */
@Composable
fun EventIcon (_modifier: Modifier) = Image(painter = painterResource(R.drawable.event_icon), "这是表示事件的图标", modifier = _modifier)