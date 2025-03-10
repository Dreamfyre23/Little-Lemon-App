package com.example.littlelemonapp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavHostController

@Composable
fun Onboarding(navController: NavHostController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .padding(20.dp)
                .height(50.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Let's get to know you",
            modifier = Modifier
                .background(color = Color(0xFF495E57))
                .padding(50.dp)
                .fillMaxWidth(),
            fontSize = 22.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Personal information",
            modifier = Modifier.padding(vertical = 40.dp, horizontal = 15.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text(text = "First name") },
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 5.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text(text = "Last name") },
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 5.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = false  // Reset error when typing
            },
            label = { Text(text = "Email") },
            isError = emailError,
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 5.dp)
                .fillMaxWidth()
        )
        if (emailError) {
            Text(
                text = "Invalid email format",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                emailError = !isValidEmail(email)  // Validate on button click

                if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && !emailError) {
                    Toast.makeText(context, "Registration successful!", Toast.LENGTH_LONG).show()
                    sharedPreferences.edit(commit = true) {
                        putString("first_name", firstName)
                        putString("last_name", lastName)
                        putString("email", email)
                    }
                    navController.navigate(Home.route)
                } else {
                    Toast.makeText(
                        context,
                        "Registration unsuccessful. Please enter all data correctly.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, bottom = 30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF4CE14),
                contentColor = Color.Black
            )
        ) {
            Text(text = "Register")
        }
    }
}

