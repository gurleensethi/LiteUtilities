package com.thetechnocafe.gurleensethi.liteutils

import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by gurleensethi on 27/07/17.
 */

interface ScrollListener {
    fun scrolledDown();

    fun scrolledUp();
}

/**
 * Hide/Show the FloatingActionButton when a NestedScrollView is scrolled
 * @param floatingActionButton to be hidden/shown
 * */
public fun NestedScrollView.hideFloatingActionButtonOnScroll(floatingActionButton: FloatingActionButton) {
    setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
        override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
            //Calculate the y-axis displacement of the scroll
            val displacementY = scrollY - oldScrollY

            //According to the displacement hide or show the layout
            if (displacementY > 0) {
                floatingActionButton.hide()
            } else if (displacementY < 0) {
                floatingActionButton.show()
            }
        }
    })
}

/**
 * Hide/Show callbacks when NestedScrollView is scrolled
 * @param listener with the required callbacks
 * */
public fun NestedScrollView.addScrollListener(listener: ScrollListener) {
    setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
        override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
            //Calculate the y-axis displacement of the scroll
            val displacementY = scrollY - oldScrollY

            //According to the displacement hide or show the layout
            if (displacementY > 0) {
                listener.scrolledDown()
            } else if (displacementY < 0) {
                listener.scrolledUp()
            }
        }

    })
}

/**
 * Hide/Show the FloatingActionButton when RecyclerView is scrolled
 * @param floatingActionButton to be hidden/shown
 * */
public fun RecyclerView.hideFloatingActionButtonOnScroll(floatingActionButton: FloatingActionButton) {
    setOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0 && floatingActionButton.visibility == View.VISIBLE) {
                floatingActionButton.hide()
            } else if (dy < 0 && floatingActionButton.visibility != View.VISIBLE) {
                floatingActionButton.show()
            }
        }
    })
}

/**
 * Hide/Show the FloatingActionButton when RecyclerView is scrolled
 * @param floatingActionButton to be hidden/shown
 * */
public fun RecyclerView.addScrollListener(listener: ScrollListener) {
    setOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                listener.scrolledDown()
            } else if (dy < 0) {
                listener.scrolledUp()
            }
        }
    })
}