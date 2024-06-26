package com.oxyethylene.easynote.ui.commonactivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynote.common.arrays.editorSettingList
import com.oxyethylene.easynote.common.constant.FONT_FAMILY_DEFAULT
import com.oxyethylene.easynote.common.constant.FONT_FAMILY_NEO_XIHEI
import com.oxyethylene.easynote.common.constant.FONT_FAMILY_WENKAI
import com.oxyethylene.easynote.common.constant.FONT_FAMILY_YOZAI
import com.oxyethylene.easynote.common.typefaces.EasyNoteTypefaces.lxgwNeoXiHei
import com.oxyethylene.easynote.common.typefaces.EasyNoteTypefaces.lxgwWenKai
import com.oxyethylene.easynote.common.typefaces.EasyNoteTypefaces.lxgwYoZai
import com.oxyethylene.easynote.domain.DropDownMenuSetting
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.settingactivity.DropDownMenuSettingItem
import com.oxyethylene.easynote.ui.settingactivity.SettingSubList
import com.oxyethylene.easynote.util.SettingUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.commonactivity
 * @ClassName    : EditorSettingPageUI.java
 * @createTime   : 2024/3/15 23:46
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 编辑器设置界面
 */
@Composable
fun EditorSettingPageUI () {

    Column {

        SimpleTitleBar(
            title = "编辑器设置"
        )

        Column(Modifier.verticalScroll(rememberScrollState())) {

            var fontFamily by remember { mutableStateOf(SettingUtil.fontFamily) }

            val context = LocalContext.current

            SettingSubList(editorSettingList.subList(0,3))

            Row (
                modifier = Modifier.wrapContentHeight()
                    .padding(start = 20.dp, end = 20.dp, top = 25.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
            ) {

                DropDownMenuSettingItem(editorSettingList[3] as DropDownMenuSetting) {
                    fontFamily = it
                }

            }

            Column (
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 25.dp)
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
            ) {

                Text(
                    text = "字体预览",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 16.dp, top = 12.dp)
                )

                when (fontFamily) {

                    FONT_FAMILY_DEFAULT ->
                        Text(
                            text = "系统默认",
                            fontSize = 24.sp,
                            color = Color.DarkGray,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(top = 20.dp)
                        )


                    FONT_FAMILY_NEO_XIHEI ->
                        Text(
                            text = "霞鹜新晰黑",
                            fontSize = 24.sp,
                            color = Color.DarkGray,
                            fontFamily = FontFamily(lxgwNeoXiHei(context)),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(top = 20.dp)
                        )

                    FONT_FAMILY_WENKAI ->
                        Text(
                            text = "霞鹜文楷",
                            fontSize = 24.sp,
                            color = Color.DarkGray,
                            fontFamily = FontFamily(lxgwWenKai(context)),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(top = 20.dp)
                        )

                    FONT_FAMILY_YOZAI ->
                        Text(
                            text = "霞鹜悠哉",
                            fontSize = 24.sp,
                            color = Color.DarkGray,
                            fontFamily = FontFamily(lxgwYoZai(context)),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(top = 20.dp)
                        )

                }

            }

        }

    }

}