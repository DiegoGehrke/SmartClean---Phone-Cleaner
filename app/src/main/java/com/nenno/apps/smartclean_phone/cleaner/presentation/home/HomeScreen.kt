package com.nenno.apps.smartclean_phone.cleaner.presentation.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nenno.apps.smartclean_phone.cleaner.util.DeviceMemory
import com.nenno.apps.smartclean_phone.cleaner.util.DeviceMemory.calculateSizeRecursively
import com.nenno.apps.smartclean_phone.cleaner.R
import com.nenno.apps.smartclean_phone.cleaner.presentation.navigation.NavigationScreen
import com.nenno.apps.smartclean_phone.cleaner.util.RobotoFontFamily

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel? = null,
    navController: NavController? = null
) {
    val viewModel: HomeScreenViewModel = viewModel()

    val context = LocalContext.current

    viewModel.checkReadStoragePermission(context)

    val readStoragePermissionGranted: Boolean by viewModel.readStoragePermissionGranted.observeAsState(
        initial = false
    )
    val openDialog = remember { mutableStateOf(false) }

    if (!readStoragePermissionGranted) { openDialog.value = true }

    val freeSpace by viewModel.freeSpace.collectAsState()
    val cache = context.cacheDir.calculateSizeRecursively()

    viewModel.getSmartphoneStorageData()

    val themeColors = MaterialTheme.colorScheme

    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        /*AlertDialog(
            openDialog = openDialog,
            viewModel = viewModel
        )*/
        val (
            arcProgressBar,
            showTrashByteAmountText,
            alertText,
            optimizeTextButton,
            batteryOption,
            virusScanOption,
            cleanTrashOption,
            boostGameOption,
            manageInstalledAppsOption,
        ) = createRefs()

        Text(
            text = "Alguns items podem ser otimizados",
            fontFamily = RobotoFontFamily.robotoFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = themeColors.onBackground,
            modifier = Modifier.constrainAs(alertText) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(arcProgressBar.bottom)
            }
        )

        TextButton(onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(optimizeTextButton) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(alertText.bottom)
            }
        ) {
            Text(
                text = "Otimizar",
                fontFamily = RobotoFontFamily.robotoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = themeColors.primary
            )
        }

        Card(
            modifier = Modifier
                .constrainAs(batteryOption) {
                    start.linkTo(parent.start, 24.dp)
                    end.linkTo(virusScanOption.start)
                    top.linkTo(optimizeTextButton.bottom, 24.dp)
                }
                .size(148.dp)
                .clickable {
                           navController?.navigate(NavigationScreen.ManageBattery.route)
                },
            colors = CardDefaults.cardColors(
                containerColor = themeColors.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.battery_icon
                ),
                contentDescription = "",
                tint = themeColors.onSurfaceVariant,
                modifier = Modifier
                    .padding(
                        start = 12.dp,
                        top = 12.dp
                    )
            )

            Text(
                text = "Bateria",
                fontFamily = RobotoFontFamily.robotoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = themeColors.onBackground,
                modifier = Modifier.padding(start = 12.dp)
            )
        }

        Card(
            modifier = Modifier
                .constrainAs(virusScanOption) {
                    start.linkTo(batteryOption.end)
                    end.linkTo(parent.end, 24.dp)
                    top.linkTo(optimizeTextButton.bottom, 24.dp)
                }
                .size(148.dp),
            colors = CardDefaults.cardColors(
                containerColor = themeColors.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.health_and_safety
                ),
                contentDescription = "",
                tint = themeColors.onSurfaceVariant,
                modifier = Modifier
                    .padding(
                        start = 12.dp,
                        top = 12.dp
                    )
            )

            Text(
                text = "Proteção contra vírus",
                fontFamily = RobotoFontFamily.robotoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = themeColors.onBackground,
                modifier = Modifier.padding(start = 12.dp)
            )
        }

        Card(
            modifier = Modifier
                .constrainAs(cleanTrashOption) {
                    start.linkTo(parent.start, 24.dp)
                    end.linkTo(virusScanOption.start)
                    top.linkTo(batteryOption.bottom, 24.dp)
                }
                .size(148.dp)
                .clickable {
                    DeviceMemory.deleteCacheFiles()
                },
            colors = CardDefaults.cardColors(
                containerColor = themeColors.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {

            Icon(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = "",
                tint = themeColors.onSurfaceVariant,
                modifier = Modifier
                    .padding(
                        start = 12.dp,
                        top = 12.dp
                    )
            )
            Column {
                Text(
                    text = "Limpeza",
                    fontFamily = RobotoFontFamily.robotoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = themeColors.onBackground,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Limpe até $cache KB",
                    fontFamily = RobotoFontFamily.robotoFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = themeColors.onBackground,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }

        Card(
            modifier = Modifier
                .constrainAs(boostGameOption) {
                    start.linkTo(batteryOption.end)
                    end.linkTo(parent.end, 24.dp)
                    top.linkTo(virusScanOption.bottom, 24.dp)
                }
                .size(148.dp),
            colors = CardDefaults.cardColors(
                containerColor = themeColors.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.bolt
                ),
                contentDescription = "",
                tint = themeColors.onSurfaceVariant,
                modifier = Modifier
                    .padding(
                        start = 12.dp,
                        top = 12.dp
                    )
            )

            Text(
                text = "Impulsionar jogos",
                fontFamily = RobotoFontFamily.robotoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = themeColors.onBackground,
                modifier = Modifier.padding(start = 12.dp)
            )
        }

        Card(
            modifier = Modifier
                .constrainAs(manageInstalledAppsOption) {
                    start.linkTo(parent.start, 48.dp)
                    end.linkTo(parent.end, 48.dp)
                    top.linkTo(boostGameOption.bottom, 24.dp)
                    width = Dimension.fillToConstraints
                }
                .clickable {
                    navController?.navigate(NavigationScreen.ManageApps.route)
                },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = themeColors.surfaceVariant
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(12.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.apk_document),
                    contentDescription = "",
                    tint = themeColors.onSurfaceVariant
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Gerenciar aplicativos instalados",
                    fontFamily = RobotoFontFamily.robotoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = themeColors.onBackground
                )
            }
        }

        ArcProgressbar(modifier = Modifier.constrainAs(arcProgressBar) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top, 48.dp)
        },
            remainingData = freeSpace,
            viewModel = viewModel
        )
    }
}

@Composable
fun ArcProgressbar(
    dataTextStyle: TextStyle = TextStyle(
        fontFamily = RobotoFontFamily.robotoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    remainingTextStyle: TextStyle = TextStyle(
        fontFamily = RobotoFontFamily.robotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    size: Dp = 148.dp,
    thickness: Dp = 12.dp,
    animationDuration: Int = 1000,
    startAngle: Float = 150f,
    remainingData: Float,
    viewModel: HomeScreenViewModel,
    modifier: Modifier
) {
    val data by viewModel.occupiedSpace.collectAsState()
    val dataPlanLimit by viewModel.totalSpace.collectAsState()
    // It remembers the number value
    var dataR by remember {
        mutableFloatStateOf(-1f)
    }

    val gapBetweenEnds = (startAngle - 90) * 2

    val remainingSpace = (remainingData / dataPlanLimit) * 100
    val usedSpace = 100 - remainingSpace

    val isDarkTheme = isSystemInDarkTheme()
    val foregroundIndicatorColor: Color = when (usedSpace) {
        in 0f..30f -> if (isDarkTheme) com.nenno.apps.smartclean_phone.cleaner.ui.theme.md_theme_dark_good else com.nenno.apps.smartclean_phone.cleaner.ui.theme.md_theme_light_good
        in 31f..70f -> if (isDarkTheme) com.nenno.apps.smartclean_phone.cleaner.ui.theme.md_theme_dark_attention else com.nenno.apps.smartclean_phone.cleaner.ui.theme.md_theme_light_attention
        else -> MaterialTheme.colorScheme.error
    }
    val backgroundIndicatorColor: Color = MaterialTheme.colorScheme.surfaceVariant

    // Number Animation
    val animateNumber = animateFloatAsState(
        targetValue = dataR,
        animationSpec = tween(
            durationMillis = animationDuration
        ), label = ""
    )

    LaunchedEffect(Unit) {
        dataR = data
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size = size)
    ) {
        Canvas(
            modifier = Modifier
                .size(size = size)
        ) {

            // Background Arc
            drawArc(
                color = backgroundIndicatorColor,
                startAngle = startAngle,
                sweepAngle = 360f - gapBetweenEnds,
                useCenter = false,
                style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round)
            )

            // convert the number to angle
            val sweepAngle = (animateNumber.value / dataPlanLimit) * (360f - gapBetweenEnds)

            // Foreground circle
            drawArc(
                color = foregroundIndicatorColor,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(thickness.toPx(), cap = StrokeCap.Round)
            )
        }

        // Display the data usage value
        DisplayText(
            animateNumber = remainingData.toInt(),
            dataTextStyle = dataTextStyle,
            remainingTextStyle = remainingTextStyle
        )
    }

    Spacer(modifier = Modifier.height(32.dp))

}

@Composable
private fun DisplayText(
    animateNumber: Int,
    dataTextStyle: TextStyle,
    remainingTextStyle: TextStyle,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Text that shows the number inside the circle
        Text(
            text = (animateNumber).toString() + " GB",
            style = dataTextStyle
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Restantes",
            style = remainingTextStyle
        )
    }
}

/*@Composable
fun AlertDialog(
    openDialog: MutableState<Boolean>,
    viewModel: HomeScreenViewModel
) {

    var context = (LocalContext.current as? ComponentActivity)!!

    val activity = LocalContext.current as? ComponentActivity
    val readStoragePermissionGranted by viewModel.readStoragePermissionGranted.observeAsState()

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { },
            containerColor = MaterialTheme.colorScheme.surface,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.folder),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            title = {
                Text(text = "Solicitação de Permissão")
            },
            text = {
                Text(
                    "Para que o aplicativo funcione, você " +
                            "terá que garantir a permissão de o aplicativo acessar o armazenamento de seu celular."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        if (!readStoragePermissionGranted!!) {
                            viewModel.requestReadStoragePermission(activity!!)
                        } else {
                            openDialog.value = false
                        }
                    }
                ) {
                    Text("Permitir")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        activity?.finish()
                    }
                ) {
                    Text("Não Permitir")
                }
            }
        )
    }
}*/



