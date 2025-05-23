package com.bike.infrastructure.Firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.FileInputStream

fun initFirerBaseAdmin() {
    val serviceAccount = FileInputStream("src/main/resources/rideon-india-firebase-adminsdk.json")

    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build()

    FirebaseApp.initializeApp(options)

}