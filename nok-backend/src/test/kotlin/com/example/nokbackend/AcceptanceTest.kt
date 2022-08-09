package com.example.nokbackend

import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AcceptanceTest {
    @LocalServerPort
    var port = 0

    @Autowired
    private val databaseCleanup: DatabaseCleanup? = null

    @BeforeEach
    fun setUp() {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port
            databaseCleanup!!.afterPropertiesSet()
        }
        databaseCleanup!!.execute()
    }
}