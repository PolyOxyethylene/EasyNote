package com.oxyethylene.easynote.util

import android.content.Context
import androidx.activity.ComponentActivity
import com.oxyethylene.easynote.dao.RecordDao
import com.oxyethylene.easynote.database.AppDatabase
import com.oxyethylene.easynote.domain.entity.NoteRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    /**
     * 数据库
     */
    private var database: AppDatabase? = null

    private var recordDao: RecordDao? = null

    /**
     * 文章的记录
     */
    private var noteRecord: NoteRecord? = null

    /**
     * 初始化 NoteUtil，传入数据库参数
     * @param database 数据库
     */
    fun initNoteUtil (database: AppDatabase) {
        if (this.database == null) {
            this.database = database
            recordDao = database.RecordDao()
        }
    }

    /**
     *  在进行文章编辑之前的操作
     *  @param fileName 文件名
     *  @param fileId 文件id
     */    fun beforeEdit (fileName: String, fileId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            // 设置文章标题
            noteTitle = fileName
            // 设置文章保存路径，暂时设为文件的id
            notePath = fileId.toString()
            noteId = fileId

            // 载入当前的文章记录
            CoroutineScope((Dispatchers.IO)).launch {
                noteRecord = recordDao?.getRecordById(noteId)
            }
        }
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
     * 获取当前文章的编辑记录
     */
    fun getRecords () = noteRecord

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

    /**
     * 更新文章的记录
     * @param location 定位信息
     * @param updateTime 修改时间
     */
    fun updateRecord (location: String, updateTime: String) {
        CoroutineScope(Dispatchers.Main).launch {
            noteRecord?.apply {
                // 如果地点没改变就只有空白字符
                if (locations.size > 0) {
                    var lastLocation: String? = null
                    var i = locations.size - 1
                    // 找到上一个有效地址
                    while (i >= 0) {
                        if (locations[i].isNotEmpty()) {
                            lastLocation = locations[i]
                            break
                        }
                        i--
                    }
                    // 证明存在有效地址，至少是 locations[0]
                    if (lastLocation != null && lastLocation == location) {
                        locations.add("")
                    } else {
                        locations.add(location)
                    }
                } else {
                    locations.add(location)
                }
                modifiedTimes.add(updateTime)

                // 更新数据库记录
                CoroutineScope(Dispatchers.IO).launch {
                    recordDao?.updateRecord(noteRecord!!)
                }
            }
        }
    }

}