package com.oxyethylene.easynote

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

    val content = "aabccdeef"

    val a = content.indexOf("aa")
    val c = content.indexOf("cc")
    val e = content.indexOf("ee")
    println(content.substring(a+2, c))

    println(content.substring(c+2, e))

    println(content.substring(e+2))

}