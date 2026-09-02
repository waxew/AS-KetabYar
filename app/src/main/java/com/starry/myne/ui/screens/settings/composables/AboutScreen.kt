/**
 * Copyright (c) [2022 - Present] Stɑrry Shivɑm
 * Licensed under the Apache License, Version 2.0.
 *
 * AS Team customization for AS-KetabYar.
 */
package com.starry.myne.ui.screens.settings.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.starry.myne.BuildConfig
import com.starry.myne.R
import com.starry.myne.helpers.Constants
import com.starry.myne.helpers.Utils
import com.starry.myne.ui.common.CustomTopAppBar
import com.starry.myne.ui.theme.poppinsFont

@Composable
fun AboutScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            CustomTopAppBar(headerText = stringResource(id = R.string.about_header)) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp)
        ) {
            AppInfoCard()
            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = stringResource(id = R.string.developed_by),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_splash_screen),
                            contentDescription = null,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                    Column(modifier = Modifier.padding(start = 14.dp)) {
                        Text(
                            text = stringResource(id = R.string.dev_name),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Develop by AS Team Group",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.clickable {
                                Utils.openEmail(
                                    context = context,
                                    email = Constants.DEV_EMAIL,
                                    subject = "AS-KetabYar Feedback (v${BuildConfig.VERSION_NAME})"
                                )
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(18.dp))
                            Text(
                                text = Constants.DEV_EMAIL,
                                modifier = Modifier.padding(start = 6.dp),
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = stringResource(id = R.string.useful_links),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
            ) {
                SettingItem(
                    icon = ImageVector.vectorResource(id = R.drawable.ic_github_logo),
                    mainText = stringResource(id = R.string.link_readme),
                    subText = stringResource(id = R.string.link_readme_desc),
                    onClick = { Utils.openWebLink(context, Constants.GITHUB_REPO) }
                )
                SettingItem(
                    icon = Icons.AutoMirrored.Filled.Notes,
                    mainText = stringResource(id = R.string.project_contributors),
                    subText = stringResource(id = R.string.project_contributors_desc),
                    onClick = { Utils.openWebLink(context, Constants.PROJECT_CONTRIBUTORS) }
                )
                SettingItem(
                    icon = Icons.Default.PrivacyTip,
                    mainText = stringResource(id = R.string.link_privacy_policy),
                    subText = stringResource(id = R.string.link_privacy_policy_desc),
                    onClick = { Utils.openWebLink(context, Constants.PRIVACY_POLICY) }
                )
                SettingItem(
                    icon = ImageVector.vectorResource(id = R.drawable.ic_github_logo),
                    mainText = stringResource(id = R.string.link_gh_issue),
                    subText = stringResource(id = R.string.link_gh_issue_desc),
                    onClick = { Utils.openWebLink(context, Constants.GITHUB_ISSUE) }
                )
            }
        }
    }
}

@Composable
private fun AppInfoCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_splash_screen),
                contentDescription = null,
                modifier = Modifier.size(112.dp)
            )
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "نسخه ${BuildConfig.VERSION_NAME}",
                fontSize = 14.sp,
                fontFamily = poppinsFont,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.about_desc),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
