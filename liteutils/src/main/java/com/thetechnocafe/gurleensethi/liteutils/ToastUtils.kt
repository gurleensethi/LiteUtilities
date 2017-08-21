package com.thetechnocafe.gurleensethi.liteutils

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.widget.TextView
import android.widget.Toast

/**
 * Created by gurleensethi on 01/08/17.
 */

fun Context.shortToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

/**
 * Create a custom short toast with a colored background
 * @param message to be shown
 * @param color of the background
 * */
fun Context.coloredShortToast(message: String?, backgroundColor: Int, textColor: Int) {
    showColoredToast(this, message, backgroundColor, textColor, Toast.LENGTH_SHORT)
}

/**
 * Create a custom long toast with a colored background
 * @param message to be shown
 * @param color of the background
 * */
fun Context.coloredLongToast(message: String?, backgroundColor: Int, textColor: Int) {
    showColoredToast(this, message, backgroundColor, textColor, Toast.LENGTH_LONG)
}

/*
 * Create a custom toast with a colored background with the provided
 * message, color and toast length
 */
private fun showColoredToast(context: Context, message: String?, backgroundColor: Int, textColor: Int, length: Int) {
    val toast = Toast.makeText(context, message, length)
    val view = toast.view

    //Get the original padding values of the Toast
    val originalPaddingRect = Rect()
    view.background.getPadding(originalPaddingRect)

    //Set the custom background drawable
    view.background = ContextCompat.getDrawable(context, R.drawable.background_toast)

    //Set the original padding because after setting custom drawable the padding is lost
    view.setPadding(originalPaddingRect.left, originalPaddingRect.top, originalPaddingRect.right, originalPaddingRect.bottom)

    //Change the background color to user provided color
    view.background.setColorFilter(ContextCompat.getColor(context, backgroundColor), PorterDuff.Mode.SRC)

    //Change the text color to user provided
    val textView = view.findViewById<TextView>(android.R.id.message)
    textView.setTextColor(ContextCompat.getColor(context, textColor))

    toast.show()
}