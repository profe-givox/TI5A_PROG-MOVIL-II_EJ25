package net.ivanvega.androidfcm2024a

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService
    : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("FCMMessagge", "Mensaje ${message.data.toString()}" )
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCMMessagge", "El nuevo token: ${token}" )
    }
}