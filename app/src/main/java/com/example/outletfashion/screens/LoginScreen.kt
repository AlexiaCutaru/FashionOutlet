
package com.example.outletfashion.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val auth = remember { FirebaseAuth.getInstance() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var infoMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(32.dp))

        Text(
            text = "OUTLET GALAXY",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Light
        )

        Spacer(Modifier.height(28.dp))

        Text(
            text = "WELCOME BACK",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Light
        )

        Spacer(Modifier.height(8.dp))

        Text("Sign in to your fashion galaxy.")

        Spacer(Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = null
                infoMessage = null
            },
            label = { Text("Email address") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = null
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage != null) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = errorMessage ?: "",
                color = MaterialTheme.colorScheme.error
            )
        }

        if (infoMessage != null) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = infoMessage ?: "",
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Complete all fields."
                } else {
                    isLoading = true
                    errorMessage = null

                    auth.signInWithEmailAndPassword(
                        email.trim(),
                        password
                    ).addOnCompleteListener { task ->
                        isLoading = false

                        if (task.isSuccessful) {
                            onLoginSuccess()
                        } else {
                            errorMessage =
                                task.exception?.localizedMessage
                                    ?: "Login failed."
                        }
                    }
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoading) "SIGNING IN..." else "LOG IN")
        }

        Spacer(Modifier.height(12.dp))

        TextButton(
            onClick = {
                val cleanEmail = email.trim()

                if (cleanEmail.isBlank()) {
                    errorMessage =
                        "Enter your email address first."
                } else {
                    isLoading = true
                    errorMessage = null
                    infoMessage = null

                    auth.sendPasswordResetEmail(cleanEmail)
                        .addOnCompleteListener { task ->
                            isLoading = false

                            if (task.isSuccessful) {
                                infoMessage =
                                    "If the account exists, a reset email will be sent."
                            } else {
                                errorMessage =
                                    "Could not send reset email. Try again."
                            }
                        }
                }
            },
            enabled = !isLoading
        ) {
            Text("FORGOT PASSWORD?")
        }

        TextButton(
            onClick = onRegisterClick,
            enabled = !isLoading
        ) {
            Text("Don't have an account? CREATE ONE")
        }

        Spacer(Modifier.height(24.dp))
    }
}
