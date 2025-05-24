package com.bike.infrastructure.Firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

fun initFirerBaseAdmin() {
    val resourceName = "assets/rideon-india-firebase-adminsdk.json" // Note: NO "src/main/resources/" here

    // Use the ClassLoader to get the resource as an InputStream
    // The '::class.java.classLoader' gets the class loader for the current context.
    val serviceAccountStream =
        Thread.currentThread().contextClassLoader.getResourceAsStream(resourceName)

    if (serviceAccountStream == null) {
        // Log an error if the file isn't found in the classpath
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

        if (FirebaseApp.getApps().isEmpty()) { // Check if Firebase is already initialized
            FirebaseApp.initializeApp(options)
            println("Firebase Admin SDK initialized successfully from classpath resource.")
        } else {
            println("Firebase Admin SDK already initialized.")
        }
    } catch (e: Exception) {
        System.err.println("ERROR: Failed to initialize Firebase Admin SDK from classpath resource: ${e.message}")
        e.printStackTrace() // Print the full stack trace for debugging
        throw e // Re-throw to indicate a critical startup failure
    } finally {
        // It's good practice to close the stream to release resources
        try {
            serviceAccountStream.close()
        } catch (e: Exception) { /* Log error if close fails */
        }
    }
}