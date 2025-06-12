package com.zekewang.basemvvmandroid.utils
import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Logger @Inject constructor(
    @ApplicationContext private val context: Context
) {

    var enableLog = true
    var logToFile = false
    var minLogLevel = Log.DEBUG

    private val defaultTag = "AppLog"

    private val logFile: File by lazy {
        File(context.getExternalFilesDir(null), "logs/app_log.txt").apply {
            parentFile?.mkdirs()
        }
    }

    /** 安全访问 logFile */
    val logFileOrNull: File?
        get() = logFile.takeIf { it.exists() }

    fun d(tag: String = defaultTag, msg: String) = log(Log.DEBUG, tag, msg)
    fun i(tag: String = defaultTag, msg: String) = log(Log.INFO, tag, msg)
    fun w(tag: String = defaultTag, msg: String) = log(Log.WARN, tag, msg)

    fun e(tag: String = defaultTag, msg: String, throwable: Throwable? = null) {
        val full = withStackTrace(msg)
        if (shouldLog(Log.ERROR)) {
            if (throwable != null) Log.e(tag, full, throwable) else Log.e(tag, full)
            if (logToFile) writeToFile(tag, "ERROR", full + (throwable?.stackTraceToString() ?: ""))
        }
    }

    fun json(tag: String = defaultTag, json: String) {
        if (!shouldLog(Log.DEBUG)) return
        try {
            val formatted = when {
                json.trim().startsWith("{") -> JSONObject(json).toString(4)
                json.trim().startsWith("[") -> JSONArray(json).toString(4)
                else -> json
            }
            d(tag, formatted)
        } catch (e: Exception) {
            e(tag, "Invalid JSON", e)
        }
    }

    fun clearLogFile() {
        logFileOrNull?.delete()
    }

    /** 是否应当记录该等级日志 */
    private fun shouldLog(level: Int): Boolean = enableLog && level >= minLogLevel

    private fun log(level: Int, tag: String, msg: String) {
        if (!shouldLog(level)) return

        val full = withStackTrace(msg)

        when (level) {
            Log.DEBUG -> Log.d(tag, full)
            Log.INFO -> Log.i(tag, full)
            Log.WARN -> Log.w(tag, full)
        }

        if (logToFile) writeToFile(tag, levelName(level), full)
    }

    private fun writeToFile(tag: String, level: String, msg: String) {
        try {
            val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            logFile.appendText("$time [$level] [$tag] $msg\n")
        } catch (e: IOException) {
            Log.e("WLogger", "Failed to write log file: ${e.message}")
        }
    }

    private fun withStackTrace(msg: String): String {
        val trace = Throwable().stackTrace.firstOrNull {
            !it.className.contains("WLogger")
        } ?: return msg
        return "(${trace.fileName}:${trace.lineNumber}) $msg"
    }

    private fun levelName(level: Int): String = when (level) {
        Log.DEBUG -> "DEBUG"
        Log.INFO -> "INFO"
        Log.WARN -> "WARN"
        Log.ERROR -> "ERROR"
        else -> "LOG"
    }
}
