package com.oxyethylene.easynote.util

import com.drake.serialize.serialize.serialLazy
import java.util.TreeSet

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNote
 * @Package      : com.oxyethylene.easynote.util
 * @ClassName    : KeywordUtil.java
 * @createTime   : 2024/3/17 22:47
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
object KeywordUtil {

    private var keywordId: Int by serialLazy(0)

    private var keywordMap: HashMap<Int, String> by serialLazy(HashMap())

    // 存储每个关键词关联的文章
    private var kw2NoteMap = HashMap<Int, HashSet<Int>>()

    class KeywordMap (val map: HashMap<Int, String>)

    fun getMap() = KeywordMap (keywordMap)

    /**
     * 新增一个关键词
     * @param keyword 关键词的新内容
     * @return 当创建成功时返回关键词的 id，当新建一个已有的关键词时会创建失败，返回 -1
     */
    fun addKeyword (keyword: String): Int {
        if (keywordMap.values.contains(keyword)) {
            return -1
        }
        keywordId++
        keywordMap[keywordId] = keyword
        keywordMap = keywordMap
        return keywordId
    }

    /**
     * 修改一个关键词
     * @param keywordId 关键词的 id
     * @param keyword 关键词的新内容
     */
    fun updateKeyword (keywordId: Int, keyword: String) {
        keywordMap[keywordId] = keyword
        update()
    }

    /**
     * 手动刷新关键词集合
     */
    fun update () {
        keywordMap = keywordMap
    }

    /**
     * 删除一个关键词
     * @param keywordId 关键词的 id
     * @return 如果关键词关联了文章，不允许删除，返回 false；删除成功返回 true
     */
    fun deleteKeyword (keywordId: Int): Boolean {
        // 如果存在关键词，且没有与之相关联的文章，则允许删除
        if (keywordMap.keys.contains(keywordId) && kw2NoteMap[keywordId].isNullOrEmpty()) {
            keywordMap.remove(keywordId)
            if (kw2NoteMap.keys.contains(keywordId)) {
                kw2NoteMap.remove(keywordId)
            }
            update()
            return true
        }

        return false
    }

    /**
     * 绑定文章到关键词（关键词侧）
     * @param keywordId 关键词的 id
     * @param noteId 文章的 id
     */
    fun bindNote2Keyword (keywordId: Int, noteId: Int): Boolean {
        // 映射的初始化
        if (!kw2NoteMap.keys.contains(keywordId)) {
            kw2NoteMap[keywordId] = HashSet()
        }
        // 如果已经添加过就添加失败
        if (kw2NoteMap[keywordId]?.contains(noteId) == true) {
            return false
        }
        // 添加关联
        kw2NoteMap[keywordId]?.add(noteId)
        update()
        return true
    }

    /**
     * 解绑文章（关键词侧），一次只解除一个一对一绑定关系
     * @param keywordId 关键词的 id
     * @param noteId 文章的 id
     */
    fun unbindNoteFromKw (keywordId: Int, noteId: Int): Boolean {
        // 假如没有该关键词或者该关键词没有绑定过文章
        if (!kw2NoteMap.keys.contains(keywordId)) {
            return false
        }

        kw2NoteMap[keywordId]?.apply {
            // 如果关键词和文章没有绑定关系
            return if (!contains(noteId)) {
                false
            } else {
                remove(noteId).apply { update() }
            }
        }
        return false
    }

    /**
     * 解绑文章所有关联的关键词（关键词侧），只在删除文章时使用
     * @param keywordIdList 文章的关联关键词 id 列表
     * @param noteId 文章的 id
     */
    fun unbindNoteFromKw (keywordIdList: TreeSet<Int>, noteId: Int): Boolean {

        var res = true

        keywordIdList.forEach {
            // 假如没有该关键词或者该关键词没有绑定过文章
            if (!kw2NoteMap.keys.contains(it)) {
                res = false
            }

            kw2NoteMap[it]?.apply {
                // 如果关键词和文章没有绑定关系
                res = if (!contains(noteId)) {
                    false
                } else {
                    remove(noteId)
                }
            }
        }

        if (res) update()

        return res
    }

    /**
     * 初始化关键词映射集合
     * @param noteId 文章 id
     * @param keywordList 文章关联的关键词集合
     */
    fun initKw2NoteMap (noteId: Int, keywordList: TreeSet<Int>) {

        keywordList.forEach { id ->
            if (!kw2NoteMap.keys.contains(id)) {
                kw2NoteMap[id] = HashSet()
            }
            kw2NoteMap[id]?.add(noteId)
        }

        update()

    }

    /**
     * 获取文章没有绑定的关键词
     * @param keywords 文章已经绑定的关键词 id 集合
     */
    fun getUnbindedKeywords (keywords: TreeSet<Int>): HashMap<String, Int> {

        val res = HashMap<String, Int>()

        keywordMap.forEach { (id, keyword) ->
            if (!keywords.contains(id)) {
                res[keyword] = id
            }
        }

        return res
    }

    /**
     * 获取文章绑定的关键词
     * @param keywords 文章已经绑定的关键词 id 集合
     */
    fun getBindedKeywords (keywords: TreeSet<Int>): HashMap<String, Int> {

        val res = HashMap<String, Int>()

        keywordMap.forEach { (id, keyword) ->
            if (keywords.contains(id)) {
                res[keyword] = id
            }
        }

        return res
    }

    /**
     * 根据关键词 id 返回关联的文章 id 集合
     * @param keywordId 关键词 id
     */
    fun getBindedNotes (keywordId: Int): HashSet<Int> {
        return kw2NoteMap[keywordId]?: HashSet()
    }



}
