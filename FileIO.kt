package com.example.fileiotest

import android.content.Context
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class FileIO(private val context: Context) {

    // ファイルの削除
    fun delete(file_name: String) {
        // ファイル削除
        context.deleteFile(file_name)
    }

    // 時間とともにmagをファイルを保存
    fun writeLog(file_name: String, msg: String) {
        val stringBuffer  = StringBuffer()
        val currentTime   = System.currentTimeMillis()
        val dataFormat    = SimpleDateFormat("yyyy/MM/dd/HH:mm:ss", Locale.US)
        val cTime: String = dataFormat.format(currentTime)

        stringBuffer.append("$cTime: $msg")
        stringBuffer.append(System.getProperty("line.separator")) // 改行

        val fileOutputStream: FileOutputStream = context.openFileOutput(file_name, Context.MODE_APPEND)
        fileOutputStream.write(stringBuffer.toString().toByteArray())
    }

    // 文字列をファイルに保存
    // ファイルが存在する場合は改行して追記
    fun write(file_name: String, msg: String) {
        val stringBuffer = StringBuffer()
        val file = context.getFileStreamPath(file_name)

        if (file.exists()) {
            stringBuffer.append(System.getProperty("line.separator")) // 改行
        }
        stringBuffer.append(msg)

        val fileOutputStream: FileOutputStream = context.openFileOutput(file_name, Context.MODE_APPEND)
        fileOutputStream.write(stringBuffer.toString().toByteArray())
    }

    // ファイルを読み出し
    fun read(file_name: String): String {

        val stringBuffer = StringBuffer()

        //　ファイルの中身の読み出し
        try {
            val fileInputStream: FileInputStream = context.openFileInput(file_name)
            val reader = BufferedReader(InputStreamReader(fileInputStream, "UTF-8"))

            var lineBuffer: String? = reader.readLine()

            while ( lineBuffer  != null) {
                stringBuffer.append(lineBuffer)
                stringBuffer.append(System.getProperty("line.separator"))
                lineBuffer = reader.readLine()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return stringBuffer.toString()
    }
}