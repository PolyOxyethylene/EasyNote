package com.oxyethylene.easynote.util

import android.content.Context
import android.os.Handler
import android.os.Message
import com.oxyethylene.easynote.common.constant.DIRECTORY_INIT_SUCCESS
import com.oxyethylene.easynote.common.constant.FILE_DELETE_SUCCESS
import com.oxyethylene.easynote.common.constant.FILE_RENAME_SUCCESS
import com.oxyethylene.easynote.common.constant.FILE_UPDATE_SUCCESS
import com.oxyethylene.easynote.common.enumeration.FileType
import com.oxyethylene.easynote.dao.FileDao
import com.oxyethylene.easynote.database.AppDatabase
import com.oxyethylene.easynote.domain.Dentry
import com.oxyethylene.easynote.domain.Dir
import com.oxyethylene.easynote.domain.NoteFile
import com.oxyethylene.easynote.viewmodel.MainViewModel
import java.util.Stack
import java.util.TreeMap
import kotlin.concurrent.thread

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote.util
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

    // 目录文件的计数器
    private var countOfDir = 0

    // 文章的计数器
    private var countOfNote = 0;

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
        fileMap[0] = root
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
     *  @param fileType 文件类型， 0 表示目录，1 表示文章
     *  @param context Activity 上下文
     */
    fun createFile (fileName : String, fileType : Int, context: Context): Dentry {
        fileId++
        var newFile : Dentry? = null
        when (fileType) {
            FileType.DIRECTORY.ordinal -> {
                newFile = Dir.createDirectory(fileId, fileName, currentDirectory)
                countOfDir++
            }
            FileType.FILE.ordinal -> {
                newFile = NoteFile.createFile(fileId, fileName, currentDirectory)
                NoteUtil.createEmptyFile("${fileId}", context)
                countOfNote++
            }
        }
        fileMap.put(fileId, newFile!!)
        saveFileEntry(newFile)
        mainViewModel?.updateCurrentFolder(currentDirectory.clone())
        return newFile
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
                file ->
                if (file.fileId > fileId) fileId = file.fileId
                when(file.fileType) {
                    // 如果子文件是一个目录，那么还需要递归调用该方法初始化子目录
                    FileType.DIRECTORY.ordinal -> {
                        val newDir = Dir.createDirectory(file.fileId, file.fileName, current, file.createTime)
                        countOfDir++
                        fileMap.put(newDir.fileId, newDir)
                        _initDirectory(newDir)
                    }
                    // 如果是文章的话需要初始化然后丢进 fileMap 里面，然后检查是否有关键词绑定关系
                    FileType.FILE.ordinal -> {
                        val newNote = NoteFile.createFileFromDB(file.fileId, file.fileName, current, file.createTime, file.updateTime, file.eventId, file.keywordList!!)
                        // 初始化关键词集合
                        KeywordUtil.initKw2NoteMap(newNote.fileId, newNote.keywordList)
                        countOfNote++
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

            var deleteFileName = ""

            // 从数据库中删除文件
            val deleteItem = fileMap.get(fileId)
            deleteItem?.let {

                deleteFileName = it.fileName

                val path = it.fileId.toString()

                if (it is Dir || (it is NoteFile && NoteUtil.deleteFile(path, context))) {
                    when(it) {
                        is Dir -> countOfDir--
                        is NoteFile -> countOfNote--
                    }
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
            msg.obj = deleteFileName
            handler?.sendMessage(msg)
        }
    }

    /**
     *  重命名文件
     *  @param fileId 文件的id
     *  @param newFileName 文件的新名字
     */
    fun renameFile (fileId: Int, newFileName : String) {

        val renameItem = fileMap[fileId]
        renameItem?.let {
            it.fileName = newFileName

            thread {
                // 重命名文件
                fileDao?.renameFile(fileId, newFileName)
                // 通知主线程更新 UI
                val msg = Message()
                msg.what = FILE_RENAME_SUCCESS
                handler?.sendMessage(msg)
            }

        }
    }

    /**
     *  更新文件记录到数据库
     *  @param file 要更新的文件
     */
    fun updateFile (file: Dentry) {
        thread {
            fileDao?.updateFile(file.toFileEntity())
        }
    }

    /**
     *  通过文件的 id 查找文件的保存路径
     *  @param fileId 文件的id
     *  @param separator 路径分隔符
     *  @return 使用分隔符拼接的文件路径（相对于根目录 root）
     */
    fun getNoteFilePath (fileId: Int, separator: String = "/"): String {

        if (fileId == 0) return root.fileName

        val file = fileMap[fileId]

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

    /**
     *  根据绑定的事件 id 来获取对应的文章
     *  @param eventId 事件的 id
     */
    fun getNotesByEventId (eventId: Int): MutableList<NoteFile> {

        val noteList = ArrayList<NoteFile>()

        fileMap.values.forEach {

            if (it is NoteFile && it.eventId == eventId) {

                noteList.add(it)

            }

        }
        return noteList
    }

    /**
     * 获取没有绑定过事件的文章，返回一个 map，键是文章名，值是文章 id
     * @param isFullPathName 是否返回完整路径，如果是 false 则仅返回文件名
     */
    fun getEventUnbindedNotes (isFullPathName: Boolean): Map<String, Int> {
        val noteMap = TreeMap<String, Int>()
        if (isFullPathName) {
            fileMap.values.forEach {
                if (it is NoteFile && it.eventId == 0) {
                    noteMap[getNoteFilePath(it.fileId)] = it.fileId
                }
            }
        } else {
            fileMap.values.forEach {
                if (it is NoteFile && it.eventId == 0) {
                    noteMap[it.fileName] = it.fileId
                }
            }
        }
        return noteMap
    }

    /**
     * 获取文章的上次修改时间，文章没被修改过则返回创建时间
     * @param fileId 文章 id
     */
    fun getNoteUpdateTime (fileId: Int): String {
        fileMap.get(fileId)?.let {
            if (it is NoteFile) {
                it.updateTime?.apply {
                    return "修改于 $this"
                }
                return "创建于 ${it.createTime}"
            }
        }
        return "该操作暂时不支持目录"
    }

    /**
     * 更新文章的修改时间
     * @param fileId 文章 id
     */
    fun setNoteUpdateTime (fileId: Int) {
        fileMap.get(fileId)?.let {
            if (it is NoteFile) {
                it.updateTime = DateUtil.getCurrentDateTime()
                thread {
                    fileDao?.updateFile(it.toFileEntity())

                    val msg = Message()
                    msg.what = FILE_UPDATE_SUCCESS
                    handler?.sendMessage(msg)
                }
            }
        }
    }

    /**
     * 根据文件名搜索对应文件
     * @param fileName 文件名
     * @return 查询到的文件列表
     */
    fun searchFileByName (fileName: String): List<Dentry> {
        val resultList = ArrayList<Dentry>()
        val searchName = fileName.trim()
        if (fileName.isNotEmpty() && fileName.isNotBlank()) {
            fileMap.values.forEach {
                if (it.fileName.contains(searchName)) {
                    resultList.add(it)
                }
            }
        }
        return resultList
    }

    /**
     * 绑定关键词到文章
     * @param keywordId 关键词的 id
     * @param noteId 文章的 id
     */
    fun bindKeyword2Note (keywordId: Int, noteId: Int): Boolean {
        val note = fileMap[noteId] as NoteFile
        note.apply {
            if (keywordList.add(keywordId)) {
                updateFile(this)
            }
        }
        return true
    }

    /**
     * 解绑关键词（文章侧），一次只解除一个绑定关系
     * @param keywordId 关键词的 id
     * @param noteId 文章的 id
     * */
    fun unbindKwFromNote (keywordId: Int, noteId: Int) {
        val note = fileMap[noteId] as NoteFile
        note.apply {
            if (keywordList.remove(keywordId)) {
                updateFile(this)
            }
        }
    }

    /**
     * 解绑关键词（文章侧），批量解绑
     * @param keywordIdSet 关键词的 id 集合
     * @param noteId 文章的 id
     * */
    fun unbindKwFromNote (keywordIdSet: HashSet<Int>, noteId: Int) {
        val note = fileMap[noteId] as NoteFile
        var res = true
        note.apply {
            keywordIdSet.forEach {
                res = res.and(keywordList.remove(it))
            }
            if (res) updateFile(this)
        }

    }

    /**
     *  获取文章数量
     *  @return 文章数量
     */
    fun getNoteCount () = countOfNote

    /**
     *  获取目录数量
     *  @return 目录数量
     */
    fun getDirCount () = countOfDir

    /**
     * 获取文章
     * @param noteId 文章 id
     * @return 如果有对应文章则返回该文章对象，否则返回 null
     */
    fun getNote(noteId: Int): NoteFile? {
        fileMap[noteId]?.let {
            if (it is NoteFile) {
                return it
            }
        }
        return null
    }

    /**
     * 获取文章
     * @param noteIdList 包含文章 id 的集合
     */
    fun getNotes(noteIdList: Collection<Int>): List<NoteFile> {

        val list = ArrayList<NoteFile>(10)

        noteIdList.forEach {
            val item = fileMap[it]
            item?.apply {
                if (this is NoteFile) {
                    list.add(this)
                }
            }
        }
        return list
    }

}