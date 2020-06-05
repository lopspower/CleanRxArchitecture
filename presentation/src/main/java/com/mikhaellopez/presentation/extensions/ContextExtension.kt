package com.mikhaellopez.presentation.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.startActivityWithFlag(intent: Intent) {
    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
}

fun Context.showUrlInBrowser(url: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) }
    startActivityWithFlag(intent)
}