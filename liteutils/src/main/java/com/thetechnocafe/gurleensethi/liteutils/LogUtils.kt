package com.thetechnocafe.gurleensethi.liteutils

import android.util.Log
import org.json.JSONObject

/**
 * Created by gurleensethi on 17/09/17.
 */

/**
 * Created by gurleensethi on 17/09/17.
 * Motivation : Simplify android logging using Kotlin
 */

class LogUtils {
    /*
    * Maintain which log level the user has allowed to logged by
    * adding all the allowed log levels to a set.
    * */
    companion object {
        private val logLevels = mutableSetOf<LogLevel>()

        fun addLevel(logLevel: LogLevel) {
            if (logLevel == LogLevel.ALL) {
                logLevels.add(LogLevel.DEBUG)
                logLevels.add(LogLevel.INFO)
                logLevels.add(LogLevel.WARN)
                logLevels.add(LogLevel.VERBOSE)
                logLevels.add(LogLevel.ERROR)
                logLevels.add(LogLevel.WTF)
            } else {
                logLevels.add(logLevel)
            }
        }

        fun addLevel(vararg logLevel: LogLevel) {
            if (logLevel.contains(LogLevel.ALL)) {
                logLevels.add(LogLevel.DEBUG)
                logLevels.add(LogLevel.INFO)
                logLevels.add(LogLevel.WARN)
                logLevels.add(LogLevel.VERBOSE)
                logLevels.add(LogLevel.ERROR)
                logLevels.add(LogLevel.WTF)
            } else {
                logLevels.addAll(logLevel)
            }
        }

        fun removeLevel(logLevel: LogLevel) {
            logLevels.remove(logLevel)
        }

        fun containsLevel(logLevel: LogLevel): Boolean = logLevel in logLevels
    }
}

//Different log levels that user can select
enum class LogLevel {
    ALL,
    INFO,
    DEBUG,
    WARN,
    VERBOSE,
    WTF,
    ERROR,
}

fun Any.info(message: String) {
    if (LogUtils.containsLevel(LogLevel.INFO)) {
        Log.i(javaClass.simpleName, message)
    }
}

fun Any.debug(message: String) {
    if (LogUtils.containsLevel(LogLevel.DEBUG)) {
        Log.d(javaClass.simpleName, message)
    }
}

fun Any.error(message: String) {
    if (LogUtils.containsLevel(LogLevel.ERROR)) {
        Log.e(javaClass.simpleName, message)
    }
}

fun Any.verbose(message: String) {
    if (LogUtils.containsLevel(LogLevel.VERBOSE)) {
        Log.v(javaClass.simpleName, message)
    }
}

fun Any.warn(message: String) {
    if (LogUtils.containsLevel(LogLevel.WARN)) {
        Log.w(javaClass.simpleName, message)
    }
}

fun Any.wtf(message: String) {
    if (LogUtils.containsLevel(LogLevel.WTF)) {
        Log.wtf(javaClass.simpleName, message)
    }
}

/**
 * Pretty Print the message inside a box of characters.
 * The default boundary character is '*', but a different one can be provided by
 * the user
 * @param message to be logged
 * @param boundaryCharacter of the box
 * */
fun Any.shout(message: String, boundaryCharacter: Char = '*') {
    val listOfStrings = message.split("\n")
    val largestLength = listOfStrings.maxBy { it.length }?.length ?: message.length

    val logWidth = largestLength + 11
    var lineWithCharsAtEnd = ""
    var lineTop = ""
    var lineBelow = ""
    for (count in 0..logWidth) {
        lineTop += boundaryCharacter
        lineBelow += boundaryCharacter

        lineWithCharsAtEnd += if (count == 0 || count == logWidth) {
            boundaryCharacter
        } else {
            " "
        }
    }

    var finalMessage = "$lineTop\n$lineWithCharsAtEnd\n"
    for (string in listOfStrings) {
        var leftSpace = "$boundaryCharacter    "
        var rightSpace = "    "
        val differenceFromLargestString = largestLength - string.length
        val rightAddedSpace = differenceFromLargestString / 2
        val leftAddedSpace = differenceFromLargestString - rightAddedSpace

        for (count in 0..leftAddedSpace) {
            leftSpace += " "
        }

        for (count in 0..rightAddedSpace) {
            rightSpace += " "
        }
        rightSpace += "$boundaryCharacter"
        finalMessage += "$leftSpace$string$rightSpace\n"
    }
    debug("$finalMessage$lineWithCharsAtEnd\n$lineBelow")
}

/*
* Pretty print string that contains data in json format.
* Use debug log level tp print data and error to print error.
* */
fun Any.json(message: String) {
    try {
        val jsonString = JSONObject(message).toString(4)
        debug(jsonString)
    } catch (e: Exception) {
        if (LogUtils.containsLevel(LogLevel.ERROR)) {
            Log.e(javaClass.simpleName, "Wrong JSON format. Please check the structure.", e)
        }
    }
}

/*
* Print the stack trace for an exception.
* Use Error log level to print.
* */
fun Any.exception(e: Exception) {
    if (LogUtils.containsLevel(LogLevel.ERROR)) {
        Log.e(javaClass.simpleName, "", e)
    }
}