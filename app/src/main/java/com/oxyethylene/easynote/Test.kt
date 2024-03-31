package com.oxyethylene.easynote

import com.hankcs.hanlp.restful.HanLPClient

/**
 * Created with IntelliJ IDEA.
 * @Project      : EasyNoteDemo
 * @Package      : com.oxyethylene.easynote
 * @ClassName    : Test.java
 * @createTime   : 2024/2/21 15:45
 * @version      : 1.0
 * @author       : Polyoxyethylene
 * @Description  :
 */
//class Test {
//}

fun main () {

    val content =
        """
        综合路透社、法新社等媒体报道，美国总统拜登当地时间周四（28日）晚与美国前总统奥巴马、克林顿一同现身纽约一场筹款活动，为拜登竞选连任“拉票”。据活动组织者统计，本次活动为拜登筹集了2500多万美元经费。尽管如此，本次活动数次被抗议者打断以及场外支持巴勒斯坦抗议者的呼声，也成为媒体关注焦点。
        “三位有着复杂过去的（美国）总统试图联手击败特朗普。”美国《华尔街日报》28日以此为题报道，关注了拜登和这两位民主党前总统的联合行动。路透社称，拜登与奥巴马一同乘坐“空军一号”抵达了纽约，当天筹款活动在纽约曼哈顿无线电城音乐厅举行，活动由《深夜秀》主持人斯蒂芬·科尔伯特主持，现场约有数千观众参加。
        """.trimIndent()


    val HanLP = HanLPClient("https://www.hanlp.com/api", null, "zh", 10000) // auth不填则匿名，zh中文，mul多语种
    val map: Map<String, Double> = HanLP.keyphraseExtraction(content)

    println(map.toString())

}