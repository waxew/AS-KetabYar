/**
 * Copyright (c) [2022 - Present] Stɑrry Shivɑm
 * Licensed under the Apache License, Version 2.0.
 *
 * AS Team customization for AS-KetabYar.
 */
package com.starry.myne.ui.screens.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.starry.myne.MainViewModel
import com.starry.myne.helpers.Constants
import com.starry.myne.helpers.NetworkObserver
import com.starry.myne.ui.navigation.BottomBarScreen
import com.starry.myne.ui.navigation.NavGraph
import com.starry.myne.ui.navigation.Screens
import com.starry.myne.ui.theme.poppinsFont
import kotlinx.coroutines.launch

val bottomNavPadding = 70.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    intent: Intent,
    startDestination: String,
    networkStatus: NetworkObserver.Status,
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // رفتار Back استاندارد AS Team:
    // 1) بستن Drawer، 2) برگشت در Back Stack، 3) برگشت به خانه، 4) انتقال برنامه به پس‌زمینه.
    BackHandler {
        when {
            drawerState.isOpen -> scope.launch { drawerState.close() }
            navController.previousBackStackEntry != null -> navController.popBackStack()
            currentRoute != BottomBarScreen.Home.route -> {
                navController.navigate(BottomBarScreen.Home.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
            else -> activity?.moveTaskToBack(true)
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                KetabYarDrawer(
                    navController = navController,
                    onClose = { scope.launch { drawerState.close() } }
                )
            }
        ) {
            Scaffold(
                bottomBar = { BottomBar(navController = navController) },
                containerColor = MaterialTheme.colorScheme.background,
            ) {
                Box {
                    NavGraph(
                        startDestination = startDestination,
                        navController = navController,
                        networkStatus = networkStatus
                    )

                    IconButton(
                        onClick = { scope.launch { drawerState.open() } },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .windowInsetsPadding(WindowInsets.statusBars)
                            .padding(top = 4.dp, end = 8.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.88f),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "منوی کتاب‌یار"
                        )
                    }
                }

                val shouldHandleShortCut = remember { mutableStateOf(false) }
                LaunchedEffect(Unit) { shouldHandleShortCut.value = true }
                if (shouldHandleShortCut.value) {
                    HandleShortcutIntent(intent, navController)
                }
            }
        }
    }
}

@Composable
private fun KetabYarDrawer(
    navController: NavHostController,
    onClose: () -> Unit,
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val preferences = remember { context.getSharedPreferences("ketabyar_profile", 0) }
    var profileUri by remember {
        mutableStateOf(preferences.getString("profile_uri", null)?.let(Uri::parse))
    }

    val profilePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            runCatching {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            profileUri = uri
            preferences.edit().putString("profile_uri", uri.toString()).apply()
        }
    }

    fun navigate(route: String) {
        navController.navigate(route) { launchSingleTop = true }
        onClose()
    }

    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(92.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { profilePicker.launch(arrayOf("image/*")) },
                contentAlignment = Alignment.Center
            ) {
                if (profileUri != null) {
                    AsyncImage(
                        model = profileUri,
                        contentDescription = "تصویر پروفایل",
                        modifier = Modifier.size(92.dp).clip(CircleShape)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "انتخاب تصویر پروفایل",
                        modifier = Modifier.size(46.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.size(10.dp))
            Text(text = "کتاب‌یار", fontSize = 20.sp)
            Text(
                text = "AS Team Group",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.size(14.dp))

            NavigationDrawerItem(
                label = { Text("تنظیمات") },
                selected = false,
                icon = { Icon(Icons.Default.Settings, null) },
                onClick = { navigate(BottomBarScreen.Settings.route) }
            )
            NavigationDrawerItem(
                label = { Text("اشتراک‌گذاری") },
                selected = false,
                icon = { Icon(Icons.Default.Share, null) },
                onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "کتاب‌یار - https://github.com/waxew/AS-KetabYar"
                        )
                    }
                    context.startActivity(
                        Intent.createChooser(shareIntent, "اشتراک‌گذاری کتاب‌یار")
                    )
                }
            )
            NavigationDrawerItem(
                label = { Text("خانه") },
                selected = false,
                icon = { Icon(Icons.Default.Home, null) },
                onClick = { navigate(BottomBarScreen.Home.route) }
            )
            NavigationDrawerItem(
                label = { Text("درباره نرم‌افزار") },
                selected = false,
                icon = { Icon(Icons.Default.Info, null) },
                onClick = { navigate(Screens.AboutScreen.route) }
            )
            NavigationDrawerItem(
                label = { Text("تماس با ما") },
                selected = false,
                icon = { Icon(Icons.Default.Mail, null) },
                onClick = {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:${Constants.DEV_EMAIL}")
                        putExtra(Intent.EXTRA_SUBJECT, "AS-KetabYar Feedback")
                    }
                    runCatching { context.startActivity(emailIntent) }
                }
            )
            NavigationDrawerItem(
                label = { Text("خروج") },
                selected = false,
                icon = { Icon(Icons.Default.ExitToApp, null) },
                onClick = { activity?.finish() }
            )
        }
    }
}

@Composable
private fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Categories,
        BottomBarScreen.Library,
        BottomBarScreen.Settings,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = screens.any { it.route == currentDestination?.route }

    AnimatedVisibility(
        visible = bottomBarDestination,
        modifier = Modifier.fillMaxWidth(),
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
                .padding(12.dp)
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            screens.forEach { screen ->
                CustomBottomNavigationItem(
                    screen = screen,
                    isSelected = screen.route == currentDestination?.route
                ) {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}

@Composable
private fun CustomBottomNavigationItem(
    screen: BottomBarScreen,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val background = if (isSelected) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    } else {
        Color.Transparent
    }
    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onBackground
    }

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = screen.icon),
                contentDescription = stringResource(id = screen.title),
                tint = contentColor
            )
            AnimatedVisibility(visible = isSelected) {
                Text(
                    text = stringResource(id = screen.title),
                    color = contentColor,
                    fontFamily = poppinsFont,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun HandleShortcutIntent(intent: Intent, navController: NavController) {
    val data = intent.data
    if (data != null && data.scheme == MainViewModel.LAUNCHER_SHORTCUT_SCHEME) {
        val libraryItemId = intent.getIntExtra(MainViewModel.LC_SC_LIBRARY_ITEM_ID, -100)
        if (libraryItemId != -100) {
            navController.navigate(
                Screens.ReaderDetailScreen.withLibraryItemId(libraryItemId.toString())
            )
            return
        }
        if (intent.getBooleanExtra(MainViewModel.LC_SC_BOOK_LIBRARY, false)) {
            navController.navigate(BottomBarScreen.Library.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    }
}
