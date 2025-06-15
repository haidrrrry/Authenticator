package org.haidrrrry.auth.screens.splash

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import kotlinx.coroutines.delay

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.ui.input.pointer.pointerInput

import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextOverflow

import org.haidrrrry.auth.classes.AuthAccount
import org.haidrrrry.auth.classes.ViewMode

import org.haidrrrry.auth.repository.AuthViewModel
import org.haidrrrry.auth.screens.ProvideAppTheme
import org.haidrrrry.auth.screens.ThemeOption
import org.haidrrrry.auth.screens.ThemeSettingsDialog

import org.haidrrrry.auth.screens.appTheme
import org.haidrrrry.auth.utils.AddAccountDialog
import org.haidrrrry.auth.utils.AppColors

import org.haidrrrry.auth.utils.TOTPGenerator
import org.haidrrrry.auth.utils.ThemeManager

import org.koin.compose.viewmodel.koinViewModel



@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AuthenticatorApp() {
    // Theme Manager
    val themeManager = remember { ThemeManager() }

    ProvideAppTheme(themeManager = themeManager) {
        val theme = appTheme()

        // Get ViewModel from Koin DI
        val viewModel: AuthViewModel = koinViewModel()

        // Initialize sample data on first launch
        LaunchedEffect(Unit) {
            viewModel.initializeSampleData()
        }

        var viewMode by remember { mutableStateOf(ViewMode.LIST) }
        var showCodes by remember { mutableStateOf(true) }
        var timeRemaining by remember { mutableStateOf(30) }
        var currentCodes by remember { mutableStateOf(mapOf<String, String>()) }
        var showAddDialog by remember { mutableStateOf(false) }
        var showThemeDialog by remember { mutableStateOf(false) }

        // Collect accounts from database
        val accounts by viewModel.accounts.collectAsState(initial = emptyList())
        val searchQuery by viewModel.searchQuery.collectAsState()
        val currentThemeMode by themeManager.themeMode.collectAsState()

        // Generate TOTP codes
        LaunchedEffect(timeRemaining, accounts) {
            if (accounts.isNotEmpty()) {
                val newCodes = accounts.associate { account ->
                    account.id to TOTPGenerator.generateTOTP(account.code)
                }
                currentCodes = newCodes
            }
        }

        // Timer effect
        LaunchedEffect(Unit) {
            while (true) {
                delay(1000)
                timeRemaining = if (timeRemaining > 0) timeRemaining - 1 else 30
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            theme.background,
                            theme.backgroundSecondary,
                            theme.surfaceVariant
                        )
                    )
                )
        ) {
            // Top App Bar with Theme Toggle
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.animateContentSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = theme.primary,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = "Authenticator",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = theme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                actions = {
                    // Theme Toggle Button
                    IconButton(
                        onClick = { showThemeDialog = true },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Icon(
                            imageVector = if (theme.isLight) Icons.Default.DarkMode else Icons.Default.LightMode,
                            contentDescription = "Toggle theme",
                            tint = theme.onSurfaceVariant
                        )
                    }

                    // Timer indicator
                    TimerIndicator(timeRemaining = timeRemaining)

                    IconButton(
                        onClick = { showCodes = !showCodes },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Icon(
                            imageVector = if (showCodes) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "Toggle codes visibility",
                            tint = theme.onSurfaceVariant
                        )
                    }

                    IconButton(
                        onClick = { viewMode = if (viewMode == ViewMode.LIST) ViewMode.GRID else ViewMode.LIST }
                    ) {
                        Icon(
                            imageVector = if (viewMode == ViewMode.LIST) Icons.Default.GridView else Icons.Default.List,
                            contentDescription = "Switch view mode",
                            tint = theme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = theme.surface.copy(alpha = 0.95f)
                )
            )

            // Search Bar
            SearchBar(
                query = searchQuery,
                onQueryChange = viewModel::updateSearchQuery,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Content with updated theme colors
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                AnimatedContent(
                    targetState = viewMode,
                    transitionSpec = {
                        slideInHorizontally(
                            initialOffsetX = { if (targetState == ViewMode.GRID) 300 else -300 },
                            animationSpec = tween(300, easing = FastOutSlowInEasing)
                        ) with slideOutHorizontally(
                            targetOffsetX = { if (targetState == ViewMode.GRID) -300 else 300 },
                            animationSpec = tween(300, easing = FastOutSlowInEasing)
                        )
                    }
                ) { mode ->
                    when (mode) {
                        ViewMode.LIST -> {
                            AccountsList(
                                accounts = accounts,
                                showCodes = showCodes,
                                timeRemaining = timeRemaining,
                                currentCodes = currentCodes
                            )
                        }
                        ViewMode.GRID -> {
                            AccountsGrid(
                                accounts = accounts,
                                showCodes = showCodes,
                                timeRemaining = timeRemaining,
                                currentCodes = currentCodes
                            )
                        }
                    }
                }

                // Floating Action Button
                FloatingActionButton(
                    onClick = { showAddDialog = true },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = theme.primary,
                    contentColor = theme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add account"
                    )
                }
            }

            // Dialogs
            AddAccountDialog(
                isVisible = showAddDialog,
                onDismiss = { showAddDialog = false },
                onSave = { account ->
                    viewModel.addAccount(account)
                    showAddDialog = false
                }
            )

            ThemeSettingsDialog(
                isVisible = showThemeDialog,
                currentTheme = currentThemeMode,
                onThemeSelected = { mode ->
                    themeManager.setThemeMode(mode)
                    showThemeDialog = false
                },
                onDismiss = { showThemeDialog = false }
            )
        }
    }
}

// Updated Components with Theme Support
@Composable
fun TimerIndicator(timeRemaining: Int) {
    val theme = appTheme()
    val progress = timeRemaining / 30f
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000, easing = LinearEasing)
    )

    Box(
        modifier = Modifier
            .size(40.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier.fillMaxSize(),
            color = when {
                timeRemaining > 20 -> theme.timerGood
                timeRemaining > 10 -> theme.timerWarning
                else -> theme.timerCritical
            },
            strokeWidth = 3.dp
        )
        Text(
            text = timeRemaining.toString(),
            style = MaterialTheme.typography.labelSmall,
            color = theme.onSurface,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = appTheme()

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = {
            Text(
                text = "Search accounts...",
                color = theme.onSurfaceVariant
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = theme.onSurfaceVariant
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = theme.primary,
            unfocusedBorderColor = theme.onSurfaceVariant.copy(alpha = 0.3f),
            focusedTextColor = theme.onSurface,
            unfocusedTextColor = theme.onSurface,
            cursorColor = theme.primary,
            focusedContainerColor = theme.surface,
            unfocusedContainerColor = theme.surface
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
    )
}


@Composable
fun AccountsList(
    accounts: List<AuthAccount>,
    showCodes: Boolean,
    timeRemaining: Int,
    currentCodes: Map<String, String>
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(accounts) {
        isVisible = false
        delay(100)
        isVisible = true
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        itemsIndexed(accounts) { index, account ->
            AnimatedListItem(
                account = account,
                showCode = showCodes,
                timeRemaining = timeRemaining,
                index = index,
                isVisible = isVisible,
                currentCode = currentCodes[account.id] ?: "------"
            )
        }
    }
}

@Composable
fun AnimatedListItem(
    account: AuthAccount,
    showCode: Boolean,
    timeRemaining: Int,
    index: Int,
    isVisible: Boolean,
    currentCode: String
) {
    val animationDelay = index * 100
    var itemVisible by remember { mutableStateOf(false) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(animationDelay.toLong())
            itemVisible = true
        } else {
            itemVisible = false
        }
    }

    AnimatedVisibility(
        visible = itemVisible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(300)
        ) + fadeOut(
            animationSpec = tween(300)
        )
    ) {
        AccountListItem(
            account = account,
            showCode = showCode,
            timeRemaining = timeRemaining,
            currentCode = currentCode
        )
    }
}

@Composable
fun AccountsGrid(
    accounts: List<AuthAccount>,
    showCodes: Boolean,
    timeRemaining: Int,
    currentCodes: Map<String, String>
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(accounts) {
        isVisible = false
        delay(100)
        isVisible = true
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        itemsIndexed(accounts) { index, account ->
            AnimatedGridItem(
                account = account,
                showCode = showCodes,
                timeRemaining = timeRemaining,
                index = index,
                isVisible = isVisible,
                currentCode = currentCodes[account.id] ?: "------"
            )
        }
    }
}

@Composable
fun AnimatedGridItem(
    account: AuthAccount,
    showCode: Boolean,
    timeRemaining: Int,
    index: Int,
    isVisible: Boolean,
    currentCode: String
) {
    val row = index / 2
    val col = index % 2
    val animationDelay = (row * 150) + (col * 75)

    var itemVisible by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (itemVisible) 1f else 0.3f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val alpha by animateFloatAsState(
        targetValue = if (itemVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    )

    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(animationDelay.toLong())
            itemVisible = true
        } else {
            itemVisible = false
        }
    }

    Box(
        modifier = Modifier
            .scale(scale)
            .alpha(alpha)
    ) {
        AccountGridItem(
            account = account,
            showCode = showCode,
            timeRemaining = timeRemaining,
            currentCode = currentCode
        )
    }
}



@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AccountGridItem(
    account: AuthAccount,
    showCode: Boolean,
    timeRemaining: Int,
    currentCode: String,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    var isHovered by remember { mutableStateOf(false) }
    val theme = appTheme()
    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.88f
            isHovered -> 1.05f
            else -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    val elevation by animateDpAsState(
        targetValue = when {
            isPressed -> 8.dp
            isHovered -> 4.dp
            else -> 2.dp
        },
        animationSpec = tween(200)
    )

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = theme.cardBackground
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Service Icon
            val iconScale by animateFloatAsState(
                targetValue = if (isPressed) 1.2f else 1f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
            )

            val iconRotation by animateFloatAsState(
                targetValue = if (isPressed) 5f else 0f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
            )

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .scale(iconScale)
                    .rotate(iconRotation)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                account.brandColor.copy(alpha = 0.15f),
                                account.brandColor.copy(alpha = 0.05f),
                                Color.Transparent
                            ),
                            radius = 100f
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = account.serviceIcon,
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            // Service Name
            AnimatedContent(
                targetState = account.serviceName,
                transitionSpec = {
                    (slideInVertically { -it } + fadeIn()) with
                            (slideOutVertically { it } + fadeOut())
                }
            ) { serviceName ->
                Text(
                    text = serviceName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = theme.onSurface,
                    textAlign = TextAlign.Center
                )
            }

            // Code Display
            AnimatedVisibility(
                visible = showCode,
                enter = fadeIn(tween(500)) + scaleIn(tween(500)) + expandVertically(tween(500)),
                exit = fadeOut(tween(200)) + scaleOut(tween(200)) + shrinkVertically(tween(200))
            ) {
                val codeScale by animateFloatAsState(
                    targetValue = if (timeRemaining < 10) 1.1f else 1f,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                )

                val codeColor by animateColorAsState(
                    targetValue = when {
                        timeRemaining < 5 -> theme.timerCritical
                        timeRemaining < 10 -> theme.timerWarning
                        else -> theme.primary
                    },
                    animationSpec = tween(300)
                )

                Text(
                    text = currentCode,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = codeColor,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.scale(codeScale)
                )
            }

            // Progress bar
            val progressHeight by animateDpAsState(
                targetValue = if (timeRemaining < 10) 6.dp else 4.dp,
                animationSpec = tween(300)
            )

            val progressColor by animateColorAsState(
                targetValue = when {
                    timeRemaining > 20 -> theme.timerGood
                    timeRemaining > 10 -> theme.timerWarning
                    else -> theme.timerCritical
                },
                animationSpec = tween(500)
            )

            LinearProgressIndicator(
                progress = timeRemaining / 30f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(progressHeight)
                    .clip(RoundedCornerShape(progressHeight / 2)),
                color = progressColor,
                trackColor = theme.onSurfaceVariant.copy(alpha = 0.1f)
            )
        }
    }
}

@Composable
fun TimerProgressIndicator(
    timeRemaining: Int,
    brandColor: Color,
    isPressed: Boolean
) {
    val progress = timeRemaining / 30f
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000, easing = LinearEasing)
    )

    val theme = appTheme()

    val indicatorColor by animateColorAsState(
        targetValue = when {
            timeRemaining > 20 -> theme.timerGood
            timeRemaining > 10 -> theme.timerWarning
            else -> theme.timerCritical
        },
        animationSpec = tween(500)
    )

    val size by animateDpAsState(
        targetValue = if (isPressed) 45.dp else 40.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier.fillMaxSize(),
            color = indicatorColor,
            strokeWidth = 3.dp,
            strokeCap = StrokeCap.Round
        )

        val textScale by animateFloatAsState(
            targetValue = if (timeRemaining < 10) 1.2f else 1f,
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
        )

        Text(
            text = timeRemaining.toString(),
            style = MaterialTheme.typography.labelSmall,
            color = theme.onSurface,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.scale(textScale)
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AccountListItem(
    account: AuthAccount,
    showCode: Boolean,
    timeRemaining: Int,
    currentCode: String,
    modifier: Modifier = Modifier
) {
    val theme = appTheme()
    var isPressed by remember { mutableStateOf(false) }
    var isLongPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = when {
            isLongPressed -> 0.92f
            isPressed -> 0.95f
            else -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    val elevation by animateDpAsState(
        targetValue = if (isPressed || isLongPressed) 8.dp else 2.dp,
        animationSpec = tween(200)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        val released = tryAwaitRelease()
                        isPressed = false
                        if (released) {
                            // Handle tap - copy code
                        }
                    },
                    onLongPress = {
                        isLongPressed = true
                        // Handle long press - show options
                    }
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = theme.cardBackground
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Service Icon
            val iconScale by animateFloatAsState(
                targetValue = if (isPressed) 1.1f else 1f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
            )

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .scale(iconScale)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                account.brandColor.copy(alpha = 0.15f),
                                account.brandColor.copy(alpha = 0.05f)
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = account.serviceIcon,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Account Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                AnimatedContent(
                    targetState = account.serviceName,
                    transitionSpec = {
                        fadeIn(tween(300)) with fadeOut(tween(300))
                    }
                ) { serviceName ->
                    Text(
                        text = serviceName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = theme.onSurface
                    )
                }

                Text(
                    text = account.accountName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = theme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Code Display
                AnimatedVisibility(
                    visible = showCode,
                    enter = fadeIn(tween(400)) + expandVertically(tween(400)) + scaleIn(tween(400)),
                    exit = fadeOut(tween(200)) + shrinkVertically(tween(200)) + scaleOut(tween(200))
                ) {
                    val codeAlpha by animateFloatAsState(
                        targetValue = if (timeRemaining < 10) 0.8f else 1f,
                        animationSpec = tween(300)
                    )

                    Text(
                        text = currentCode,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = theme.primary.copy(alpha = codeAlpha),
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.animateContentSize()
                    )
                }
            }

            // Progress indicator
            TimerProgressIndicator(
                timeRemaining = timeRemaining,
                brandColor = account.brandColor,
                isPressed = isPressed
            )
        }
    }

    LaunchedEffect(isLongPressed) {
        if (isLongPressed) {
            delay(100)
            isLongPressed = false
        }
    }
}