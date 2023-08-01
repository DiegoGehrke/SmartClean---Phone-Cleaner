package com.nenno.apps.smartclean_phone.cleaner.presentation.battery

import android.content.Intent
import android.provider.Settings
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.nenno.apps.smartclean_phone.cleaner.R
import com.nenno.apps.smartclean_phone.cleaner.presentation.navigation.NavigationScreen
import com.nenno.apps.smartclean_phone.cleaner.util.RobotoFontFamily

@Composable
fun ManageBatteryScreen(
    viewModel: ManageBatteryViewModel? = null,
    navController: NavController? = null,
) {

    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (
            backButton,
            backText,
            batteryProgress,
            showBatteryHealthCard,
            showRemainingChargeTimeCard,
            showTemperatureCard,
            goToActivateBatteryCard,
            showBatteryCapacityCard,
            goToBatteryUsageScreen
        ) = createRefs()
        IconButton(onClick = {
            navController?.navigate(NavigationScreen.Home.route) {
                navController.popBackStack()
            }
        }, content = {
            Icon(
                painter = painterResource(id = R.drawable.arrow_icon),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = "back"
            )
        }, modifier = Modifier.constrainAs(backButton) {
            start.linkTo(parent.start, 12.dp)
            top.linkTo(parent.top, 24.dp)
        })

        Text(text = "Manage Battery",
            fontFamily = RobotoFontFamily.robotoFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.constrainAs(backText) {
                start.linkTo(backButton.end, 12.dp)
                top.linkTo(backButton.top)
                bottom.linkTo(backButton.bottom)
            })

        Card(
            modifier = Modifier
            .constrainAs(showBatteryHealthCard) {
                start.linkTo(parent.start, 24.dp)
                end.linkTo(parent.end, 24.dp)
                top.linkTo(batteryProgress.bottom, 24.dp)
                width = Dimension.fillToConstraints
                height = Dimension.value(64.dp)
            }
        ) {
            Column {
                Text(
                    text = "Battery Status",
                    modifier = Modifier
                        .padding(top = 8.dp, start = 8.dp),
                    fontFamily = RobotoFontFamily.robotoFontFamily,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = viewModel?.getBatteryHealth(context).toString(),
                    modifier = Modifier
                        .padding(start = 8.dp, top = 4.dp),
                    fontFamily = RobotoFontFamily.robotoFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Card(
            modifier = Modifier
                .constrainAs(showRemainingChargeTimeCard) {
                    start.linkTo(parent.start, 24.dp)
                    end.linkTo(parent.end, 24.dp)
                    top.linkTo(showBatteryHealthCard.bottom, 24.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(64.dp)
                }
        ) {
            Column {
                Text(
                    text = "Remaining Charge Time",
                    modifier = Modifier
                        .padding(top = 8.dp, start = 8.dp),
                    fontFamily = RobotoFontFamily.robotoFontFamily,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = viewModel?.estimateRemainingTimeUntilChargeLevelGoToZero(context).toString(),
                    modifier = Modifier
                        .padding(start = 8.dp, top = 4.dp),
                    fontFamily = RobotoFontFamily.robotoFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Card(
            modifier = Modifier
                .constrainAs(showTemperatureCard) {
                    start.linkTo(parent.start, 24.dp)
                    end.linkTo(parent.end, 24.dp)
                    top.linkTo(showRemainingChargeTimeCard.bottom, 24.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(64.dp)
                }
        ) {
            Column {
                Text(
                    text = "Temperature",
                    modifier = Modifier
                        .padding(top = 8.dp, start = 8.dp),
                    fontFamily = RobotoFontFamily.robotoFontFamily,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${viewModel?.getCurrentBatteryTemperature(context).toString()}°C",
                    modifier = Modifier
                        .padding(start = 8.dp, top = 4.dp),
                    fontFamily = RobotoFontFamily.robotoFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Card(
            modifier = Modifier
                .constrainAs(showBatteryCapacityCard) {
                    start.linkTo(parent.start, 24.dp)
                    end.linkTo(parent.end, 24.dp)
                    top.linkTo(showTemperatureCard.bottom, 24.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(64.dp)
                }
        ) {
            Column {
                Text(
                    text = "Capacity in mAh",
                    modifier = Modifier
                        .padding(top = 8.dp, start = 8.dp),
                    fontFamily = RobotoFontFamily.robotoFontFamily,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${ viewModel?.getBatteryCapacity(context) }",
                    modifier = Modifier
                        .padding(start = 8.dp, top = 4.dp),
                    fontFamily = RobotoFontFamily.robotoFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Card(
            modifier = Modifier
                .constrainAs(goToBatteryUsageScreen) {
                    start.linkTo(parent.start, 24.dp)
                    end.linkTo(parent.end, 24.dp)
                    top.linkTo(showBatteryCapacityCard.bottom, 24.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(64.dp)
                }
                .clickable {
                    navController?.navigate(NavigationScreen.BatteryUsage.route)
                },
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column {
                Text(
                    text = "Consumo da bateria",
                    modifier = Modifier
                        .padding(top = 8.dp, start = 8.dp),
                    fontFamily = RobotoFontFamily.robotoFontFamily,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Ver uso de bateria dos apps",
                    modifier = Modifier
                        .padding(start = 8.dp, top = 4.dp),
                    fontFamily = RobotoFontFamily.robotoFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Card(
            modifier = Modifier
                .constrainAs(goToActivateBatteryCard) {
                    start.linkTo(parent.start, 24.dp)
                    end.linkTo(parent.end, 24.dp)
                    top.linkTo(goToBatteryUsageScreen.bottom, 24.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(64.dp)
                }
                .clickable {
                    viewModel?.openBatterySettings(context)
                },
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column {
                Text(
                    text = "Activate energy saver",
                    modifier = Modifier
                        .padding(top = 8.dp, start = 8.dp),
                    fontFamily = RobotoFontFamily.robotoFontFamily,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Go to settings",
                    modifier = Modifier
                        .padding(start = 8.dp, top = 4.dp),
                    fontFamily = RobotoFontFamily.robotoFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        ArcProgressbar(currentProgress = viewModel?.getBatteryChargeLevel(context) ?: 0f,
            isCharging = viewModel?.checkIfIsChargingOrNot(context) ?: false,
            modifier = Modifier.constrainAs(batteryProgress) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(backButton.bottom, 24.dp)
            })
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
    currentProgress: Float,
    isCharging: Boolean = false,
    modifier: Modifier,
) {
    val data = 100f
    val dataPlanLimit = 100f
    // It remembers the number value
    var dataR by remember {
        mutableFloatStateOf(-1f)
    }

    val isDarkTheme = isSystemInDarkTheme()
    val foregroundIndicatorColor: Color = when (currentProgress) {
        in 0f..20f -> MaterialTheme.colorScheme.error
        in 21f..60f -> if (isDarkTheme) com.nenno.apps.smartclean_phone.cleaner.ui.theme.md_theme_dark_attention else com.nenno.apps.smartclean_phone.cleaner.ui.theme.md_theme_light_attention
        else -> if (isDarkTheme) com.nenno.apps.smartclean_phone.cleaner.ui.theme.md_theme_dark_good else com.nenno.apps.smartclean_phone.cleaner.ui.theme.md_theme_light_good
    }


    val backgroundIndicatorColor: Color = MaterialTheme.colorScheme.surfaceVariant

    val gapBetweenEnds = (startAngle - 90) * 2

    // Number Animation
    val animateNumber = animateFloatAsState(
        targetValue = currentProgress, animationSpec = tween(
            durationMillis = animationDuration
        ), label = ""
    )

    LaunchedEffect(Unit) {
        dataR = data
    }

    Box(
        contentAlignment = Alignment.Center, modifier = modifier.size(size = size)
    ) {
        Canvas(
            modifier = Modifier.size(size = size)
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
            animateNumber = currentProgress.toLong(),
            dataTextStyle = dataTextStyle,
            remainingTextStyle = remainingTextStyle,
            isCharging = isCharging
        )
    }

    Spacer(modifier = Modifier.height(32.dp))

}

@Composable
private fun DisplayText(
    animateNumber: Long,
    dataTextStyle: TextStyle,
    remainingTextStyle: TextStyle,
    isCharging: Boolean = false,
) {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Text that shows the number inside the circle
        Text(
            text = (animateNumber).toInt().toString() + "%",
            style = dataTextStyle,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = if (isCharging) "Charging" else "Discharging",
            style = remainingTextStyle,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

/*@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
    AppTheme {
        ManageBatteryScreen()
    }
}*/