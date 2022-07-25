package com.example.notificationsample.notification.message

data class Message(val text:CharSequence,val sender:CharSequence?,val timeStamp:Long = System.currentTimeMillis())
