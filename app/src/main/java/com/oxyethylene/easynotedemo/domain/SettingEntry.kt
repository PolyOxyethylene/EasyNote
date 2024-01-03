package com.oxyethylene.easynotedemo.domain

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

class SwitchSetting (settingName : String, var state : Boolean = false) : SettingEntry(settingName)

class PlainSetting (settingName : String) : SettingEntry(settingName)

class DialogSetting (SettingName : String) : SettingEntry(SettingName)


// 测试用的设置列表
object testSettings {

    val SettingList : ArrayList<SettingEntry>

    init {
        SettingList = ArrayList()
        SettingList.let {
            it.add(SwitchSetting("编辑器设置"))
            it.add(SwitchSetting("外观设置"))
            it.add(DialogSetting("语言"))
            it.add(SwitchSetting("自动保存"))
            it.add(SwitchSetting("位置服务"))
            it.add(DialogSetting("统计"))
            it.add(SwitchSetting("健康信息"))
            it.add(PlainSetting("实验功能"))
            it.add(PlainSetting("关于作者"))
            it.add(PlainSetting("关于应用"))
        }
    }

}