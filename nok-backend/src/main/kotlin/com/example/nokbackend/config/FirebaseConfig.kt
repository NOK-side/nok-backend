package com.example.nokbackend.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


@Component
class FirebaseConfig {

    @Value("\${app.firebase-configuration-file}")
    private val firebaseConfigPath: String = ""

    @PostConstruct
    fun initialize() {
        val options = FirebaseOptions.builder().setCredentials(
            GoogleCredentials.fromStream(
                ClassPathResource(firebaseConfigPath).inputStream
            )
        ).build()

        FirebaseApp.initializeApp(options)

        check(FirebaseApp.getApps().isNotEmpty()) { "파이어베이스 초기화 실패" }
    }
}