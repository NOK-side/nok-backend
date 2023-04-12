package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.util.CodeService
import com.example.nokbackend.domain.memberGifticon.MemberGifticonRepository
import com.example.nokbackend.domain.membermission.MemberMissionRepository
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.AndroidNotification
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController(
    private val memberMissionRepository: MemberMissionRepository,
    private val memberGifticonRepository: MemberGifticonRepository,
    private val codeService: CodeService,
) {

    @DeleteMapping("/member-mission")
    fun deleteMemberMission(): ResponseEntity<ApiResponse<EmptyBody>> {
        memberMissionRepository.deleteAll()
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @DeleteMapping("/member-gifticon")
    fun deleteMemberGifticon(): ResponseEntity<ApiResponse<EmptyBody>> {
        memberGifticonRepository.deleteAll()
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @PostMapping("/fcm")
    fun sendMessage(): ResponseEntity<ApiResponse<String>> {
        val message = Message.builder()
            .setAndroidConfig(
                AndroidConfig.builder()
                    .setTtl(3600 * 1000)
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .setRestrictedPackageName("com.example.nokbackend")
                    .setDirectBootOk(true)
                    .setNotification(
                        AndroidNotification.builder()
                            .setTitle("title")
                            .setBody("테스트 푸시")
                            .build()
                    )
                    .build()
            ).setToken("APA91bGnfJm9kOxh-2nJB3dYTs-U317PZa57nopf9EELtJUJ52E9Unw7OAHnCtrNcUVzXJexNb7ZDqQa47K_Ted47NK_Z9X5aB60yVeB5SKWTf7uE2Eu3m1eN2V9wpjZcXDDeyl8e5ye")
            .build()

        val firebaseApps = FirebaseApp.getApps()
        val defaultFirebaseApp = firebaseApps.find { it.name.startsWith("message") }

        val result = FirebaseMessaging.getInstance(defaultFirebaseApp).send(message)

        return responseEntity {
            body = apiResponse {
                data = result
            }
        }
    }
}