package com.example.nokbackend.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.annotation.PostConstruct


@Component
class FirebaseConfig {

    @Value("\${app.firebase-configuration-file}")
    private val firebaseConfigPath: String = ""

    @PostConstruct
    fun initializeStorage() {
        val firebaseApps = FirebaseApp.getApps()
        val defaultFirebaseApp = firebaseApps.find { it.name == storageName }

        if (defaultFirebaseApp == null) {
            val options = FirebaseOptions.builder().setCredentials(
                GoogleCredentials.fromStream(
                    ClassPathResource(firebaseConfigPath).inputStream
                )
            ).build()

            FirebaseApp.initializeApp(options, storageName)
        }

        check(FirebaseApp.getApps().isNotEmpty()) { "파이어베이스 초기화 실패" }
    }

    @PostConstruct
    fun initializeMessage() {
        val firebaseApps = FirebaseApp.getApps()
        val defaultFirebaseApp = firebaseApps.find { it.name == messageName }

        if (defaultFirebaseApp == null) {
            val options = FirebaseOptions.builder().setCredentials(
                GoogleCredentials.fromStream(
                    ClassPathResource("config/nok-message-firebase-key.json").inputStream
                )
            ).build()

            FirebaseApp.initializeApp(options, messageName)
        }

        check(FirebaseApp.getApps().isNotEmpty()) { "파이어베이스 초기화 실패" }
    }

    companion object {
        private val storageName = "storage_${LocalDateTime.now()}"
        private val messageName = "message_${LocalDateTime.now()}"
    }
}