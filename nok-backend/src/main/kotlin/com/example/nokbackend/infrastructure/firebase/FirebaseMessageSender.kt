package com.example.nokbackend.infrastructure.firebase

import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.AndroidNotification
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.springframework.stereotype.Component

@Component
class FirebaseMessageSender {

    fun sendAppPush(title: String, body: String, targetToken: String) {
        val message = Message.builder()
            .setAndroidConfig(
                AndroidConfig.builder()
                    .setTtl(3600 * 1000)
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .setDirectBootOk(true)
                    .setNotification(
                        AndroidNotification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build()
                    )
                    .build()
            )
            .setToken(targetToken)
            .build()

        val firebaseApps = FirebaseApp.getApps()
        val defaultFirebaseApp = firebaseApps.find { it.name.startsWith("message") }

        FirebaseMessaging.getInstance(defaultFirebaseApp)
            .send(message)
    }
}