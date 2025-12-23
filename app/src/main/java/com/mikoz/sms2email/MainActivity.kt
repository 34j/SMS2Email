package com.mikoz.sms2email

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.content.Context
import android.widget.Toast
import com.mikoz.sms2email.ui.theme.SMS2EmailTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SMS2EmailTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MailPreferencesScreen(
                        context = this,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MailPreferencesScreen(context: Context, modifier: Modifier = Modifier) {
    val sharedPreferences = context.getSharedPreferences("", Context.MODE_PRIVATE)

    fun savePreference(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun savePreference(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    var smtpHost by remember {
        mutableStateOf(sharedPreferences.getString("smtp.host", "smtp.gmail.com") ?: "smtp.gmail.com")
    }
    var smtpPort by remember {
        mutableStateOf(sharedPreferences.getInt("smtp.port", 587).toString())
    }
    var smtpUser by remember {
        mutableStateOf(sharedPreferences.getString("smtp.user", "") ?: "")
    }
    var smtpPassword by remember {
        mutableStateOf(sharedPreferences.getString("smtp.password", "") ?: "")
    }
    var fromEmail by remember {
        mutableStateOf(sharedPreferences.getString("from", "") ?: "")
    }
    var toEmail by remember {
        mutableStateOf(sharedPreferences.getString("to", "") ?: "")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Mail Preferences",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = smtpHost,
            onValueChange = {
                smtpHost = it
                savePreference("smtp.host", it)
            },
            label = { Text("SMTP Host") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = smtpPort,
            onValueChange = {
                smtpPort = it
                it.toIntOrNull()?.let { port ->
                    savePreference("smtp.port", port)
                }
            },
            label = { Text("SMTP Port") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = smtpUser,
            onValueChange = {
                smtpUser = it
                savePreference("smtp.user", it)
            },
            label = { Text("SMTP Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = smtpPassword,
            onValueChange = {
                smtpPassword = it
                savePreference("smtp.password", it)
            },
            label = { Text("SMTP Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        OutlinedTextField(
            value = fromEmail,
            onValueChange = {
                fromEmail = it
                savePreference("from", it)
            },
            label = { Text("From Email Address") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            value = toEmail,
            onValueChange = {
                toEmail = it
                savePreference("to", it)
            },
            label = { Text("To Email Address") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Button(
            onClick = {
                Toast.makeText(context, "Sending email ...", Toast.LENGTH_SHORT).show()
                MailSender().send(context, "test", "test")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send Test Email")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MailPreferencesScreenPreview() {
    SMS2EmailTheme {
        // Note: Preview doesn't have access to actual SharedPreferences
        // In a real preview, you'd need to mock the context
    }
}
