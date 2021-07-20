package com.mikhaellopez.presentation.extensions

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

inline fun <T : Fragment> T.build(args: Bundle.() -> Unit): T =
    apply { arguments = Bundle().apply(args) }

fun Fragment.getLongArg(key: String): Long = arguments!!.getLong(key)

fun Fragment.getBooleanArg(key: String): Boolean = arguments!!.getBoolean(key)

fun Fragment.getStringArg(key: String): String = arguments!!.getString(key)!!

fun <T : View?> Fragment.findViewById(@IdRes id: Int): T? = view?.findViewById<T>(id)