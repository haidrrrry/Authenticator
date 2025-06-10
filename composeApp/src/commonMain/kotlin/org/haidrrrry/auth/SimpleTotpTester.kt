package org.haidrrrry.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTotpTester() {
    var accountName by remember { mutableStateOf("") }
    var secretKey by remember { mutableStateOf("") }
    var currentOTP by remember { mutableStateOf("") }
    var timeRemaining by remember { mutableStateOf(30) }
    var isValid by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    // Auto-refresh TOTP every second
    LaunchedEffect(secretKey) {
        while (true) {
            if (secretKey.isNotBlank()) {
                val result = generateTOTP(secretKey.trim())
                currentOTP = result.otp
                timeRemaining = result.timeRemaining
                isValid = result.isValid
                errorMessage = result.errorMessage
            }
            delay(1000L)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "TOTP Generator Test",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Account Name Input
        OutlinedTextField(
            value = accountName,
            onValueChange = { accountName = it },
            label = { Text("Account Name (Optional)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )

        // Secret Key Input
        OutlinedTextField(
            value = secretKey,
            onValueChange = { secretKey = it },
            label = { Text("Secret Key") },
            placeholder = { Text("Enter Base32 secret key") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            supportingText = {
                Text(
                    text = "Example: JBSWY3DPEHPK3PXP",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // TOTP Display Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (accountName.isNotBlank()) {
                    Text(
                        text = accountName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                if (isValid && currentOTP.isNotBlank()) {
                    // OTP Display
                    Text(
                        text = currentOTP,
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 4.sp
                    )

                    // Progress Bar
                    LinearProgressIndicator(
                        progress = timeRemaining / 30f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = when {
                            timeRemaining > 20 -> Color.Green
                            timeRemaining > 10 -> Color.Yellow
                            else -> Color.Red
                        },
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Text(
                        text = "Valid for ${timeRemaining}s",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else if (secretKey.isNotBlank()) {
                    // Error Display
                    Text(
                        text = errorMessage.ifBlank { "Invalid secret key" },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                } else {
                    // Placeholder
                    Text(
                        text = "Enter a secret key to generate TOTP",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Test Buttons with common keys
        Text(
            text = "Test with sample keys:",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    accountName = "Google"
                    secretKey = "JBSWY3DPEHPK3PXP"
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Sample 1")
            }

            Button(
                onClick = {
                    accountName = "GitHub"
                    secretKey = "GEZDGNBVGY3TQOJQGEZDGNBVGY3TQOJQ"
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Sample 2")
            }
        }

        // Clear Button
        OutlinedButton(
            onClick = {
                accountName = ""
                secretKey = ""
                currentOTP = ""
                errorMessage = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear")
        }
    }
}

// TOTP Generation Result
data class TotpResult(
    val otp: String,
    val timeRemaining: Int,
    val isValid: Boolean,
    val errorMessage: String
)

// Pure Kotlin TOTP Generation Function
fun generateTOTP(secretKey: String): TotpResult {
    if (secretKey.isBlank()) {
        return TotpResult("", 30, false, "Secret key is empty")
    }

    val trimmedSecretKey = secretKey.trim()
    if (trimmedSecretKey.isEmpty()) {
        return TotpResult("", 30, false, "Secret key is empty")
    }

    val timeStep = 30L
    val currentTimestamp = getCurrentTimeSeconds()
    val timestamp = currentTimestamp / timeStep
    val secretKeyBytes: ByteArray

    try {
        secretKeyBytes = Base32.decode(trimmedSecretKey.uppercase())
    } catch (e: IllegalArgumentException) {
        return TotpResult("", 30, false, "Invalid Base32 encoding")
    }

    // Convert timestamp to byte array (8 bytes, big-endian)
    val msg = ByteArray(8)
    var value = timestamp
    for (i in 7 downTo 0) {
        msg[i] = (value and 0xFF).toByte()
        value = value shr 8
    }

    try {
        val hash = HmacSha1.hmacSha1(secretKeyBytes, msg)

        val offset = hash[hash.size - 1].toInt() and 0xf
        val binary = ((hash[offset].toInt() and 0x7f) shl 24) or
                ((hash[offset + 1].toInt() and 0xff) shl 16) or
                ((hash[offset + 2].toInt() and 0xff) shl 8) or
                (hash[offset + 3].toInt() and 0xff)

        val otp = binary % 1_000_000

        // Calculate remaining time in current time step
        val remainingTime = (timeStep - (currentTimestamp % timeStep)).toInt()

        return TotpResult(
           otp= otp.toString().padStart(6, '0'),
            timeRemaining = remainingTime,
            isValid = true,
            errorMessage = ""
        )
    } catch (e: Exception) {
        return TotpResult("", 30, false, "Error generating TOTP: ${e.message}")
    }
}



