package com.nenno.apps.smartclean_phone.cleaner.presentation.manage_apps

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.nenno.apps.smartclean_phone.cleaner.R
import com.nenno.apps.smartclean_phone.cleaner.model.AppInfo
import com.nenno.apps.smartclean_phone.cleaner.presentation.navigation.NavigationScreen
import com.nenno.apps.smartclean_phone.cleaner.util.RobotoFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageAppsScreen(viewModel: ManageAppsViewModel, navController: NavController) {

    val installedAppsList: List<AppInfo> by viewModel.installedApps.observeAsState(emptyList())
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.loadInstalledApps(context = context)
    }
    
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (backButton,
            backText,
            listContainer
        ) = createRefs()

        IconButton(
            onClick = {
                navController.navigate(NavigationScreen.Home.route) {
                    navController.popBackStack()
                }
            },
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_icon),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "back")
            },
            modifier = Modifier
                .constrainAs(backButton) {
                start.linkTo(parent.start, 12.dp)
                top.linkTo(parent.top, 24.dp)
            }
        )

        Text(
            text = "Gerenciar aplicativos",
            fontFamily = RobotoFontFamily.robotoFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(backText) {
                start.linkTo(backButton.end, 12.dp)
                top.linkTo(backButton.top)
                bottom.linkTo(backButton.bottom)
            }
        )

        Box(
            modifier = Modifier
                .constrainAs(listContainer) {
                    top.linkTo(backButton.bottom, 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxSize() // Preenche o espaço disponível dentro do Container
        ) {
            LazyColumn {
                itemsIndexed(installedAppsList) { index, appInfo ->
                    ListItem(
                        headlineText = {
                            Text(text = appInfo.appName)
                        },
                        supportingText = {
                            Text(text = "${ viewModel.formatFileSize(appInfo.appSize) }")
                        },
                        modifier = Modifier.clickable {
                            openAppSettings(context, appInfo.packageName)
                        }
                    )
                }
            }
        }
    }
}

fun openAppSettings(context: Context, packageName: String) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    context.startActivity(intent)
}
