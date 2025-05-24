package com.bike.infrastructure.Firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

fun initFirerBaseAdmin() {
    val resourceName = "assets/rideon-india-firebase-adminsdk.json"
    val serviceAccountStream =
        Thread.currentThread().contextClassLoader.getResourceAsStream(resourceName)

    if (serviceAccountStream == null) {
        System.err.println(
            "ERROR: Firebase Admin SDK JSON file not found in classpath: $resourceName. " +
                    "Make sure it's in src/main/resources/assets/ and committed to Git."
        )
        throw IllegalStateException("Firebase Admin SDK JSON resource not found.")
    }

    try {
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
            .build()

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
            println("Firebase Admin SDK initialized successfully from classpath resource.")
        } else {
            println("Firebase Admin SDK already initialized.")
        }
    } catch (e: Exception) {
        System.err.println("ERROR: Failed to initialize Firebase Admin SDK from classpath resource: ${e.message}")
        e.printStackTrace()
        throw e
    } finally {
        try {
            serviceAccountStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}