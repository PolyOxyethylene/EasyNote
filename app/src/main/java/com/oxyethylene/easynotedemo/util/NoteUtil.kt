package com.oxyethylene.easynotedemo.util

import android.content.Context
import androidx.activity.ComponentActivity
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.util
 * @ClassName    : NoteUtil.java
 * @createTime   : 2024/1/9 14:32
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
object NoteUtil {

    // 文章标题
    private var noteTitle = ""

    // 文章内容的存储路径
    private var notePath = ""

    // 文章的内容
    var noteContent = ""

    /**
     *  在进行文章编辑之前的操作
     *  @param fileName 文件名
     *  @param fileId 文件id
     */
    fun beforeEdit (fileName: String, fileId: Int) {
        // 设置文章标题
        noteTitle = fileName
        // 设置文章保存路径，暂时设为文件的id
        notePath = fileId.toString()

    }

    /**
     *  获取文章标题
     */
    fun getTitle () = noteTitle

    /**
     *  保存文章内容
     *  @param context Activity 上下文
     */
    fun saveFile (context: Context) {
        try {
            val output = context.openFileOutput(notePath, ComponentActivity.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use {
                it.write(noteContent)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     *  加载文章内容
     *  @param context Activity 上下文
     */
    fun loadFile (context: Context): String {
        val content = StringBuffer()
        try {
            val input = context.openFileInput(notePath)
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                reader.forEachLine {
                    content.append("${it}\n")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return content.toString()
    }

    /**
     *  删除指定文章
     *  @param path 文章保存路径
     *  @param context Activity 上下文
     */
    fun deleteFile (path: String, context: Context) = context.deleteFile(path)

}