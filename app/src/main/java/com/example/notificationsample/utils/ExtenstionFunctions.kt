package com.example.notificationsample

import android.content.Context
import android.widget.Toast


inline fun Context.showToast(message:String,time:Int=Toast.LENGTH_SHORT){
    Toast.makeText(this,message,time).show()
}