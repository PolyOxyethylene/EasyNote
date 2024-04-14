package com.oxyethylene.easynote.ui.commonactivity

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyethylene.easynote.common.constant.EXTRACTION_MODEL_DEFAULT
import com.oxyethylene.easynote.common.constant.EXTRACTION_MODEL_GPT
import com.oxyethylene.easynote.domain.DropDownMenuSetting
import com.oxyethylene.easynote.domain.SettingEntry
import com.oxyethylene.easynote.domain.SwitchSetting
import com.oxyethylene.easynote.ui.components.SimpleTitleBar
import com.oxyethylene.easynote.ui.settingactivity.SettingSubList
import com.oxyethylene.easynote.util.SettingUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.ui.commonactivity
 * @ClassName    : LabSettingPageUI.java
 * @createTime   : 2024/3/15 23:28
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 实验性功能界面
 */
@Composable
fun LabSettingPageUI () {

    // 是否显示选择备份目录的设置项
    var enableAutoExtraction by remember { mutableStateOf(SettingUtil.autoExtraction) }

    var model by remember { mutableStateOf(SettingUtil.extractionModel) }

    val labSettingList =
        listOf<SettingEntry>(
            SwitchSetting(
                settingName = "开启关键词提取",
                state = SettingUtil.autoExtraction,
                onValueChanged = {
                    SettingUtil.autoExtraction = it
                    enableAutoExtraction = it
                },
                description = "通过向服务器上传您的文章内容自动提取关键词和摘要",
                warning = "此功能需要连接互联网并发送您的文章内容"
            )
        )

    val density = LocalDensity.current

    Column {

        SimpleTitleBar(
            title = "实验性功能"
        )

        Column(Modifier.verticalScroll(rememberScrollState())) {

            SettingSubList(labSettingList)

            AnimatedVisibility(
                visible = enableAutoExtraction,
                enter = slideInVertically { with(density) { -25.dp.roundToPx() } } // 从顶部 40dp 的地方开始滑入
                        + expandVertically(expandFrom = Alignment.Top)  // 从顶部开始展开
                        + fadeIn(initialAlpha = 0.3f),   // 从初始透明度 0.3f 开始淡入
                exit = shrinkVertically() + fadeOut()
            ) {

                Column {

                    SettingSubList(
                        listOf(
                            DropDownMenuSetting(
                                settingName = "使用模型",
                                menuList = listOf(
                                    Pair(-1, EXTRACTION_MODEL_DEFAULT),
                                    Pair(-1, EXTRACTION_MODEL_GPT)
                                ),
                                value = SettingUtil.extractionModel,
                                onValueChanged = {
                                    SettingUtil.extractionModel = it
                                    model = it
                                }
                            )
                        )
                    )

                    when (model) {
                        EXTRACTION_MODEL_DEFAULT ->
                            Text(
                                text = "默认模型，可以分析出文章摘要和关键词，优点是速度较快，缺点是比较笨，支持分析的最大字数较少，有时候摘要会牛头不对马嘴",
                                fontSize = 10.sp,
                                color = Color.Gray,
                                lineHeight = 12.sp,
                                letterSpacing = 0.sp,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier.padding(top = 16.dp, start = 30.dp, end = 30.dp)
                            )
                        EXTRACTION_MODEL_GPT ->
                            Text(
                                text = "使用 gpt-3.5-turbo 进行分析, 可以分析出文章标题、摘要和关键词，优点是相对更聪明，支持分析的最大字数更多，分析更加准确，缺点是现阶段不太稳定，可能因为网络、文章内容等原因分析失败，或者分析结果出现多余的内容",
                                fontSize = 10.sp,
                                color = Color.Gray,
                                lineHeight = 12.sp,
                                letterSpacing = 0.sp,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier.padding(top = 16.dp, start = 30.dp, end = 30.dp)
                            )
                    }

                }



            }

        }

    }
    
}
