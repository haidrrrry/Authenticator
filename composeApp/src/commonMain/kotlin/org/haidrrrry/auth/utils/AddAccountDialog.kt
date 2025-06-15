
package org.haidrrrry.auth.utils

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

import org.haidrrrry.auth.classes.AuthAccount
import org.haidrrrry.auth.screens.appTheme

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
// ADD THIS IMPORT - This is the missing import for detectTapGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.haidrrrry.auth.classes.ViewMode
import kotlin.math.*
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onSave: (AuthAccount) -> Unit
) {
    val theme = appTheme()

    var serviceName by remember { mutableStateOf("") }
    var accountName by remember { mutableStateOf("") }
    var secretKey by remember { mutableStateOf("") }
    var issuer by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf("🔐") }
    var selectedColor by remember { mutableStateOf(Color(0xFF4285F4)) }
    var isValidSecret by remember { mutableStateOf(false) }

    // Animation states for each form element
    var showHeader by remember { mutableStateOf(false) }
    var showServiceName by remember { mutableStateOf(false) }
    var showAccountName by remember { mutableStateOf(false) }
    var showSecretKey by remember { mutableStateOf(false) }
    var showIssuer by remember { mutableStateOf(false) }
    var showQuickSelect by remember { mutableStateOf(false) }
    var showButtons by remember { mutableStateOf(false) }

    // Reset form when dialog closes
    LaunchedEffect(isVisible) {
        if (!isVisible) {
            serviceName = ""
            accountName = ""
            secretKey = ""
            issuer = ""
            selectedIcon = "🔐"
            selectedColor = Color(0xFF4285F4)
            isValidSecret = false
            // Reset animation states
            showHeader = false
            showServiceName = false
            showAccountName = false
            showSecretKey = false
            showIssuer = false
            showQuickSelect = false
            showButtons = false
        } else {
            // Staggered entrance animations
            delay(100)
            showHeader = true
            delay(150)
            showServiceName = true
            delay(100)
            showAccountName = true
            delay(100)
            showSecretKey = true
            delay(100)
            showIssuer = true
            delay(150)
            showQuickSelect = true
            delay(100)
            showButtons = true
        }
    }

    // Validate secret key (simplified validation)
    LaunchedEffect(secretKey) {
        isValidSecret = try {
            val cleanKey = secretKey.uppercase().replace(" ", "").replace("-", "")
            // Basic validation: should be base32 characters and reasonable length
            cleanKey.matches(Regex("^[A-Z2-7]+$")) && cleanKey.length >= 16
        } catch (e: Exception) {
            false
        }
    }

    val predefinedServices = listOf(
        Triple("Google", "🔍", Color(0xFF4285F4)),
        Triple("GitHub", "🐙", Color(0xFF24292e)),
        Triple("Discord", "🎮", Color(0xFF5865F2)),
        Triple("Microsoft", "Ⓜ️", Color(0xFF00BCF2)),
        Triple("Amazon", "📦", Color(0xFFFF9900)),
        Triple("Facebook", "📘", Color(0xFF1877F2)),
        Triple("Twitter", "🐦", Color(0xFF1DA1F2)),
        Triple("Instagram", "📷", Color(0xFFE4405F)),
        Triple("LinkedIn", "💼", Color(0xFF0077B5)),
        Triple("Dropbox", "📦", Color(0xFF0061FF))
    )

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
        ) + scaleIn(
            initialScale = 0.8f,
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
        ) + slideInVertically(
            initialOffsetY = { it / 4 },
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
        ),
        exit = fadeOut(
            animationSpec = tween(
                durationMillis = 250,
                easing = FastOutLinearInEasing
            )
        ) + scaleOut(
            targetScale = 0.8f,
            animationSpec = tween(
                durationMillis = 250,
                easing = FastOutLinearInEasing
            )
        ) + slideOutVertically(
            targetOffsetY = { -it / 4 },
            animationSpec = tween(
                durationMillis = 250,
                easing = FastOutLinearInEasing
            )
        )
    ) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = theme.cardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Animated Header with icon
                    AnimatedVisibility(
                        visible = showHeader,
                        enter = slideInVertically(
                            initialOffsetY = { -it / 2 },
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = FastOutSlowInEasing
                            )
                        ) + fadeIn(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = FastOutSlowInEasing
                            )
                        ) + scaleIn(
                            initialScale = 0.8f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMedium
                            )
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = theme.primary,
                                modifier = Modifier
                                    .size(28.dp)
                                    .padding(end = 12.dp)
                            )
                            Text(
                                text = "Add New Account",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = theme.onSurface
                            )
                        }
                    }

                    // Animated Service Name Field
                    AnimatedVisibility(
                        visible = showServiceName,
                        enter = slideInVertically(
                            initialOffsetY = { it / 3 },
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = FastOutSlowInEasing
                            )
                        ) + fadeIn(
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = FastOutSlowInEasing
                            )
                        )
                    ) {
                        OutlinedTextField(
                            value = serviceName,
                            onValueChange = { serviceName = it },
                            label = { Text("Service Name", color = theme.onSurfaceVariant) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = theme.primary,
                                unfocusedBorderColor = theme.onSurfaceVariant.copy(alpha = 0.3f),
                                focusedTextColor = theme.onSurface,
                                unfocusedTextColor = theme.onSurface,
                                focusedLabelColor = theme.primary,
                                unfocusedLabelColor = theme.onSurfaceVariant,
                                cursorColor = theme.primary,
                                focusedContainerColor = theme.surface,
                                unfocusedContainerColor = theme.surface
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // Animated Account Name Field
                    AnimatedVisibility(
                        visible = showAccountName,
                        enter = slideInVertically(
                            initialOffsetY = { it / 3 },
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = FastOutSlowInEasing
                            )
                        ) + fadeIn(
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = FastOutSlowInEasing
                            )
                        )
                    ) {
                        OutlinedTextField(
                            value = accountName,
                            onValueChange = { accountName = it },
                            label = { Text("Account Name/Email", color = theme.onSurfaceVariant) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = theme.primary,
                                unfocusedBorderColor = theme.onSurfaceVariant.copy(alpha = 0.3f),
                                focusedTextColor = theme.onSurface,
                                unfocusedTextColor = theme.onSurface,
                                focusedLabelColor = theme.primary,
                                unfocusedLabelColor = theme.onSurfaceVariant,
                                cursorColor = theme.primary,
                                focusedContainerColor = theme.surface,
                                unfocusedContainerColor = theme.surface
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // Animated Secret Key Field
                    AnimatedVisibility(
                        visible = showSecretKey,
                        enter = slideInVertically(
                            initialOffsetY = { it / 3 },
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = FastOutSlowInEasing
                            )
                        ) + fadeIn(
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = FastOutSlowInEasing
                            )
                        )
                    ) {
                        OutlinedTextField(
                            value = secretKey,
                            onValueChange = { secretKey = it.uppercase().replace(" ", "").replace("-", "") },
                            label = { Text("Secret Key", color = theme.onSurfaceVariant) },
                            placeholder = {
                                Text(
                                    "Enter your 2FA secret key",
                                    color = theme.onSurfaceVariant.copy(alpha = 0.7f)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            isError = secretKey.isNotEmpty() && !isValidSecret,
                            supportingText = {
                                if (secretKey.isNotEmpty() && !isValidSecret) {
                                    Text("Invalid secret key format (should be Base32)", color = theme.error)
                                } else if (secretKey.isEmpty()) {
                                    Text(
                                        "Usually provided as QR code or text by the service",
                                        color = theme.onSurfaceVariant.copy(alpha = 0.7f)
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (isValidSecret && secretKey.isNotEmpty()) theme.success else theme.primary,
                                unfocusedBorderColor = theme.onSurfaceVariant.copy(alpha = 0.3f),
                                focusedTextColor = theme.onSurface,
                                unfocusedTextColor = theme.onSurface,
                                focusedLabelColor = if (isValidSecret && secretKey.isNotEmpty()) theme.success else theme.primary,
                                unfocusedLabelColor = theme.onSurfaceVariant,
                                cursorColor = theme.primary,
                                focusedContainerColor = theme.surface,
                                unfocusedContainerColor = theme.surface,
                                errorBorderColor = theme.error,
                                errorTextColor = theme.onSurface,
                                errorLabelColor = theme.error
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // Animated Issuer Field
                    AnimatedVisibility(
                        visible = showIssuer,
                        enter = slideInVertically(
                            initialOffsetY = { it / 3 },
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = FastOutSlowInEasing
                            )
                        ) + fadeIn(
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = FastOutSlowInEasing
                            )
                        )
                    ) {
                        OutlinedTextField(
                            value = issuer,
                            onValueChange = { issuer = it },
                            label = { Text("Issuer (Optional)", color = theme.onSurfaceVariant) },
                            placeholder = {
                                Text(
                                    "Company or organization name",
                                    color = theme.onSurfaceVariant.copy(alpha = 0.7f)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = theme.primary,
                                unfocusedBorderColor = theme.onSurfaceVariant.copy(alpha = 0.3f),
                                focusedTextColor = theme.onSurface,
                                unfocusedTextColor = theme.onSurface,
                                focusedLabelColor = theme.primary,
                                unfocusedLabelColor = theme.onSurfaceVariant,
                                cursorColor = theme.primary,
                                focusedContainerColor = theme.surface,
                                unfocusedContainerColor = theme.surface
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // Animated Quick Service Selection
                    AnimatedVisibility(
                        visible = showQuickSelect,
                        enter = slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = tween(
                                durationMillis = 700,
                                easing = FastOutSlowInEasing
                            )
                        ) + fadeIn(
                            animationSpec = tween(
                                durationMillis = 700,
                                easing = FastOutSlowInEasing
                            )
                        )
                    ) {
                        Column {
                            Text(
                                text = "Quick Select Service:",
                                style = MaterialTheme.typography.bodyMedium,
                                color = theme.onSurfaceVariant,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                itemsIndexed(predefinedServices) { index, (name, icon, color) ->
                                    val isSelected = serviceName == name

                                    // Staggered animation for service cards
                                    var cardVisible by remember { mutableStateOf(false) }

                                    LaunchedEffect(showQuickSelect) {
                                        if (showQuickSelect) {
                                            delay(index * 50L)
                                            cardVisible = true
                                        } else {
                                            cardVisible = false
                                        }
                                    }

                                    val scale by animateFloatAsState(
                                        targetValue = if (cardVisible) 1f else 0.3f,
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessMedium
                                        )
                                    )

                                    val alpha by animateFloatAsState(
                                        targetValue = if (cardVisible) 1f else 0f,
                                        animationSpec = tween(
                                            durationMillis = 400,
                                            easing = FastOutSlowInEasing
                                        )
                                    )

                                    Card(
                                        modifier = Modifier
                                            .scale(scale)
                                            .alpha(alpha)
                                            .clickable {
                                                serviceName = name
                                                selectedIcon = icon
                                                selectedColor = color
                                            }
                                            .animateContentSize(),
                                        colors = CardDefaults.cardColors(
                                            containerColor = if (isSelected)
                                                theme.primary.copy(alpha = 0.1f)
                                            else
                                                theme.surfaceVariant
                                        ),
                                        shape = RoundedCornerShape(12.dp),
                                        border = if (isSelected)
                                            BorderStroke(2.dp, theme.primary)
                                        else
                                            BorderStroke(1.dp, theme.onSurfaceVariant.copy(alpha = 0.1f))
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(12.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = icon,
                                                style = MaterialTheme.typography.titleMedium,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            )
                                            Text(
                                                text = name,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = if (isSelected) theme.primary else theme.onSurface,
                                                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Animated Action Buttons
                    AnimatedVisibility(
                        visible = showButtons,
                        enter = slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = FastOutSlowInEasing
                            )
                        ) + fadeIn(
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = FastOutSlowInEasing
                            )
                        ) + scaleIn(
                            initialScale = 0.9f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMedium
                            )
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                onClick = onDismiss,
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = theme.onSurfaceVariant
                                ),
                                border = BorderStroke(1.dp, theme.onSurfaceVariant.copy(alpha = 0.3f)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Cancel")
                            }

                            Button(
                                onClick = {
                                    if (serviceName.isNotEmpty() && accountName.isNotEmpty() && isValidSecret) {
                                        val account = AuthAccount(
                                            id = getCurrentTimeSeconds().toString(), // Generate unique ID
                                            serviceName = serviceName,
                                            accountName = accountName,
                                            code = secretKey.uppercase().replace(" ", "").replace("-", ""),
                                            serviceIcon = selectedIcon,
                                            brandColor = selectedColor
                                        )
                                        onSave(account)
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                enabled = serviceName.isNotEmpty() && accountName.isNotEmpty() && isValidSecret,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = theme.primary,
                                    contentColor = theme.onPrimary,
                                    disabledContainerColor = theme.onSurfaceVariant.copy(alpha = 0.3f),
                                    disabledContentColor = theme.onSurfaceVariant.copy(alpha = 0.6f)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Save", fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }
        }
    }
}