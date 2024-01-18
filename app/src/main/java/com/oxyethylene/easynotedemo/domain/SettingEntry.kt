package com.oxyethylene.easynotedemo.domain

import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.style.MIUIStyle
import com.kongzue.dialogx.style.MaterialStyle
import com.kongzue.dialogx.util.TextInfo
import com.oxyethylene.easynotedemo.R
import com.oxyethylene.easynotedemo.util.FileUtil

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.domain
 * @ClassName    : SettingEntry.java
 * @createTime   : 2023/12/14 22:15
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
sealed class SettingEntry (val settingName : String)

/**
 * @param settingName 设置项的名称
 * @param state 开关的状态，默认为关闭
 */
class SwitchSetting (settingName : String, var state : Boolean = false) : SettingEntry(settingName)

/**
 * @param settingName 设置项的名称
 * @param actionName 点击该设置项可以启动的 Activity 的 action name
 */
class PlainSetting (settingName : String, val actionName: String) : SettingEntry(settingName)

/**
 * @param settingName 设置项的名称
 * @param dialogAction 打开一个对话框
 */
class DialogSetting (SettingName : String, val dialogAction: () -> Unit) : SettingEntry(SettingName)


// 测试用的设置列表
object testSettings {

    val SettingList : ArrayList<SettingEntry>

    init {
        SettingList = ArrayList()
        SettingList.let {
            it.add(DialogSetting("编辑器设置") {
                MessageDialog.build(MIUIStyle())
                    .setTitle("敬请期待")
                    .setMessage("这个功能我还没做")
                    .setOkButton("确认")
                    .show()
            })
            it.add(DialogSetting("统计") {
                val textInfo = TextInfo()
                textInfo.setBold(true)
                MessageDialog.build(MaterialStyle.style())
                    .setTitle("统计")
                    .setTitleIcon(R.drawable.analyze_icon)
                    .setMessage(
                        "总数              ${FileUtil.getDirCount() + FileUtil.getNoteCount()}\n" +
                        "目录              ${FileUtil.getDirCount()}\n" +
                        "文章              ${FileUtil.getNoteCount()}")
                    .setMessageTextInfo(textInfo)
                    .setEnterAnimDuration(300)
                    .show()
            })
            it.add(SwitchSetting("位置服务"))
            it.add(SwitchSetting("健康信息跟踪"))
            it.add(PlainSetting("关于应用", "com.oxyethylene.INFO"))
        }
    }

}