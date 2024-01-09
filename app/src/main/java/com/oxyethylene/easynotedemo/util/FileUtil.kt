package com.oxyethylene.easynotedemo.util

import android.content.Context
import android.os.Handler
import android.os.Message
import com.oxyethylene.easynotedemo.dao.FileDao
import com.oxyethylene.easynotedemo.database.AppDatabase
import com.oxyethylene.easynotedemo.domain.Dentry
import com.oxyethylene.easynotedemo.domain.Dir
import com.oxyethylene.easynotedemo.domain.NoteFile
import com.oxyethylene.easynotedemo.viewmodel.MainViewModel
import java.util.Stack
import kotlin.concurrent.thread

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynotedemo.util
 * @ClassName    : FileUtil.java
 * @createTime   : 2023/12/28 20:00
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */

/**
 *  负责 App 内文件目录的构建、更新，但是不负责文章的单独保存
 */
object FileUtil {

    // 统一管理文件的 id
    private var fileId = 0

    // 根目录
    val root = Dir(0, "根目录", null, "")

    // 当前目录，初始化时为根目录
    private var currentDirectory : Dir

    // 管理文件和 id 的映射关系
    private val fileMap = HashMap<Int, Dentry>()

    // 绑定主页面的 viewModel
    private var mainViewModel: MainViewModel? = null

    // 数据库
    private var database: AppDatabase? = null

    // 主页面的 handler
    private var handler: Handler? = null

    // 数据库 文件类的 dao
    private var fileDao: FileDao? = null

    // 是否已经初始化，用于保证初始化目录操作只发生一次
    private var inited = false

    // 必要的初始化
    init {
        currentDirectory = root
        fileMap.put(0, root)
    }

    /**
     *  初始化 viewModel 和 context
     */
    fun initFileUtil(viewModel: MainViewModel, _database: AppDatabase, _handler: Handler) {
        if (mainViewModel == null || mainViewModel != viewModel) {
            mainViewModel = viewModel
        }
        if (database == null) {
            database = _database
        }
        if (handler == null) {
            handler = _handler
        }
        // 初始化 fileDao
        database?.let {
            fileDao =  it.FileDao()
        }
    }

    /**
     *  统一管理创建文件的功能
     *  @param fileName 文件名
     *  @param fileType 文件类型， 1 表示目录，2 表示文章
     */
    fun createFile (fileName : String, fileType : Int) {
        fileId++
        var newFile : Dentry? = null
        when (fileType) {
            1 -> newFile = Dir.createDirectory(fileId, fileName, currentDirectory)
            2 -> newFile = NoteFile.createFile(fileId, fileName, currentDirectory)
        }
        fileMap.put(fileId, newFile!!)
        saveFileEntry(newFile)
        mainViewModel?.updateCurrentFolder(currentDirectory.clone())
    }

    /**
     *  管理目录的切换
     *  @param dirId 目录的文件 id
     */
    fun updateDirectory (dirId : Int = currentDirectory.fileId) {
        currentDirectory = fileMap.get(dirId) as Dir
        // 只给 viewModel 提供目录的拷贝
        mainViewModel?.updateCurrentFolder(currentDirectory.clone())
    }

    /**
     *  更新当前选中的文件
     *  @param dirId 目录的文件 id
     */
    fun updateSelectedFile (fileId : Int) {
        val selected = fileMap.get(fileId)
        // 只给 viewModel 提供目录的拷贝
        selected?.let { mainViewModel?.updateCurrentSelectedFile(it.clone()) }
    }

    /**
     * 回到上级界面
     */
    fun toParentDir () {
        updateDirectory(currentDirectory.parent!!.fileId)
    }

    /**
     *  判断当前是否在根目录
     */
    fun isRootDir() : Boolean = currentDirectory.fileId == 0

    /**
     *  获取当前目录
     */
    fun getCurrentDir() = currentDirectory

    /**
     * TODO 实现从数据库加载整个目录
     */
    fun initDirectory () {
        if (!inited) {
            thread {
                _initDirectory(root)

                handler?.let {
                    val msg = Message()
                    msg.what = DIRECTORY_INIT_SUCCESS
                    it.sendMessage(msg)
                }

                inited = true

            }
        }

    }

    /**
     * 因为是递归加载目录所以这是递归的部分
     * @param current 表示当前要加载的目录
     */
    fun _initDirectory (current : Dir) {
        fileDao?.let {
            // 获取当前目录的子文件
            val fileList = it.getChildFilesById(current.fileId)
            // 将所有子文件挂载到当前目录中
            fileList.forEach {
                if (it.fileId > fileId) fileId = it.fileId
                when(it.fileType) {
                    // 如果子文件是一个目录，那么还需要递归调用该方法初始化子目录
                    FileType.DIRECTORY.ordinal -> {
                        val newDir = Dir.createDirectory(it.fileId, it.fileName, current, it.date)
                        fileMap.put(newDir.fileId, newDir)
                        _initDirectory(newDir)
                    }
                    // 如果是文章的话只需要初始化然后丢进 fileMap 里面
                    FileType.FILE.ordinal -> {
                        val newNote = NoteFile.createFile(it.fileId, it.fileName, current, it.date)
                        fileMap.put(newNote.fileId, newNote)
                    }
                }
            }
        }
    }

    /**
     *  将文件（目录或者文章）的目录结构保存到数据库表中
     *  @param file 要保存的目录结点
     */
    fun saveFileEntry (file : Dentry) {
        thread {
            fileDao?.insertFile(file.toFileEntity())
        }
    }

    /**
     * 将指定的文件（目录或者文章）的目录结构从数据库表中删除
     * @param fileId 要删除的文件的 id
     * @param context Activity 上下文
     */
    fun deleteFileEntry (fileId : Int, context: Context) {
        thread {

            // 从数据库中删除文件
            val deleteItem = fileMap.get(fileId)
            deleteItem?.let {

                val path = it.fileId.toString()

                if (NoteUtil.deleteFile(path, context)) {
                    // 如果删除成功，则从数据库中删除该文章的记录
                    fileDao?.deleteFile(it.toFileEntity())
                    //从父目录的文件列表以及映射 fileMap 中将其删除
                    deleteItem.parent?.let {
                        (it as Dir).getFileList().remove(deleteItem)
                    }
                    fileMap.remove(fileId)
                }

            }

            // 通知主线程
            val msg = Message()
            msg.what = FILE_DELETE_SUCCESS
            handler?.sendMessage(msg)
        }
    }

    /**
     *  重命名文件
     *  @param fileId 文件的id
     *  @param newFileName 文件的新名字
     */
    fun renameFile (fileId: Int, newFileName : String, context: Context) {

        thread {

            val renameItem = fileMap[fileId]
            renameItem?.let {
                // 重命名文件
                fileDao?.renameFile(fileId, newFileName)
                it.fileName = newFileName

                // 通知主线程更新 UI
                val msg = Message()
                msg.what = FILE_RENAME_SUCCESS
                // 携带要更新的文件对应的 id
                msg.arg1 = fileId
                handler?.sendMessage(msg)

            }

        }

    }

    /**
     *  通过文件的 id 查找文件的保存路径
     *  @param fileId 文件的id
     *  @param separator 路径分隔符
     */
    fun getNoteFilePath (fileId: Int, separator: String = "/"): String {

        val file = fileMap.get(fileId)

        val stack = Stack<String>()

        val path = StringBuffer()

        file?.let {

            var f = it

            stack.push(f.fileName)

            // 获取父目录的文件名
            do {
                f = f.parent!!
                stack.push("${f.fileName}${separator}")
            } while (f.fileId != 0)
            // 拼接字符串得到文件路径
            while (stack.isNotEmpty()) {
                path.append(stack.pop())
            }

        }

        return path.toString()

    }

}