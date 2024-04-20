package com.oxyethylene.easynote.common.arrays

import com.oxyethylene.easynote.R

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.common.arrays
 * @ClassName    : SpinnerItemTexts.java
 * @createTime   : 2024/2/19 13:55
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
/**
 * 设置标题文字大小的菜单的选项
 */
val headerSizeList =
    ArrayList<Pair<Int, String>>().apply {
        add(Pair(R.mipmap.ic_set_heading_btn, "默认图标"))
        add(Pair(R.mipmap.ic_set_heading_1, "一号标题"))
        add(Pair(R.mipmap.ic_set_heading_2, "二号标题"))
        add(Pair(R.mipmap.ic_set_heading_3, "三号标题"))
        add(Pair(R.mipmap.ic_set_heading_4, "四号标题"))
        add(Pair(R.mipmap.ic_set_heading_5, "五号标题"))
        add(Pair(R.mipmap.ic_set_heading_6, "六号标题"))
    }

/**
 * 设置文本对齐方式的菜单选项
 */
val textAlignList =
    ArrayList<Pair<Int, String>>().apply {
        add(Pair(R.mipmap.ic_set_align_justify, "默认图标"))
        add(Pair(R.mipmap.ic_set_align_center, "居中对齐"))
        add(Pair(R.mipmap.ic_set_align_justify, "两端对齐"))
        add(Pair(R.mipmap.ic_set_align_left, "左对齐"))
        add(Pair(R.mipmap.ic_set_align_right, "右对齐"))
    }

/**
 * 插入列表的菜单选项
 */
val listInsertList =
    ArrayList<Pair<Int, String>>().apply {
        add(Pair(R.mipmap.ic_insert_list, "默认图标"))
        add(Pair(R.mipmap.ic_insert_list_ul, "无序列表"))
        add(Pair(R.mipmap.ic_insert_list_ol, "有序列表"))
    }