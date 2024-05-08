package com.oxyethylene.easynote.domain

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.domain
 * @ClassName    : SettingEntry.java
 * @createTime   : 2023/12/14 22:15
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  : 不同的设置项类型，会对应不同的外观和功能
 */
/**
 * 所有设置项的父类
 * @param settingName 设置项的名称
 * @param description 设置项的描述
 * @param warning 设置项的警示信息
 */
sealed class SettingEntry (val settingName : String, var description: String? = null, var warning: String? = null)

/**
 * 有一个开关的设置项
 * @param settingName 设置项的名称
 * @param state 开关的状态，默认为关闭
 * @param onValueChanged 当开关切换状态时，将执行的动作
 * @param description 设置项的描述
 * @param warning 设置项的警示信息
 */
class SwitchSetting (
    settingName : String,
    var state : Boolean = false,
    val onValueChanged: (Boolean) -> Unit,
    description: String? = null,
    warning: String? = null
) : SettingEntry(settingName, description, warning)

/**
 * 打开一个新的页面的设置项
 * @param settingName 设置项的名称
 * @param actionName 点击该设置项可以启动的 Activity 的 action name
 * @param commonActivityTitle 如果不为 null，即为 CommonActivity 中 UI 的名字
 * @param description 设置项的描述
 * @param warning 设置项的警示信息
 */
class PlainSetting (settingName : String, val actionName: String, val commonActivityTitle: String? = null, description: String? = null, warning: String? = null) : SettingEntry(settingName, description, warning)

/**
 * 打开一个对话框的设置项
 * @param settingName 设置项的名称
 * @param dialogAction 打开一个对话框
 * @param description 设置项的描述
 * @param warning 设置项的警示信息
 */
class DialogSetting (settingName : String, description: String? = null, warning: String? = null, val dialogAction: () -> Unit) : SettingEntry(settingName, description, warning)

/**
 * 打开一个下拉菜单的设置项
 * @param settingName 设置项的名称
 * @param menuList 下拉菜单的选项列表，每个选项需要提供一个图标的资源id和选项的名字，id为-1时表示不需要图标
 * @param value 设置项右侧显示的值，初始化时的值为设置项显示的默认值
 * @param onValueChanged 当通过下拉菜单改变了设置项的值时，需要执行的动作，String 类型参数即修改后的值
 * @param description 设置项的描述
 * @param warning 设置项的警示信息
 */
class DropDownMenuSetting (
    settingName: String,
    val menuList: List<Pair<Int, String>>,
    var value: String,
    val onValueChanged: (String) -> Unit,
    description: String? = null,
    warning: String? = null
) : SettingEntry(settingName, description, warning)

/**
 * 必要时用于表示一个设置项的别名和值
 * @param alias 设置项的值的别名，当此项不为 null 时，UI 中设置项显示的内容是该别名，实际设置的是 value 成员
 * @param value 设置项右侧显示的值，初始化时的值为设置项显示的默认值
 */
//data class SettingValues (
//    val alias: String? = null,
//    val value: String
//)

