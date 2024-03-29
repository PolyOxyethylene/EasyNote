package com.oxyethylene.easynote.util

import android.content.Context
import androidx.activity.ComponentActivity
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.util
 * @ClassName    : NoteUtil.java
 * @createTime   : 2024/1/9 14:32
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
object NoteUtil {

    private var noteId = -1

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
        noteId = fileId

    }

    /**
     *  获取文章标题
     */
    fun getTitle () = noteTitle

    /**
     * 获取文章的 id
     */
    fun getNoteId () = noteId

    /**
     *  产生一个空文件
     *  @param path 文件路径
     *  @param context Activity 上下文
     */
    fun createEmptyFile (path: String, context: Context) {
        var output : FileOutputStream? = null
        try {
            output = context.openFileOutput(path, ComponentActivity.MODE_PRIVATE)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            output?.close()
        }
    }

    /**
     *  保存文章内容
     *  @param context Activity 上下文
     *  @param fileContent 文章内容
     */
    fun saveFile (context: Context, fileContent: String) {
        var output : FileOutputStream? = null
        try {
            output = context.openFileOutput(notePath, ComponentActivity.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use {
                it.write(fileContent)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            output?.close()
        }
    }

    /**
     *  加载文章内容
     *  @param context Activity 上下文
     */
    fun loadFile (context: Context): String {
        val content = StringBuffer()
        var input : FileInputStream? = null
        try {
            input = context.openFileInput(notePath)
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                reader.forEachLine {
                    content.append("${it}\n")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            input?.close()
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