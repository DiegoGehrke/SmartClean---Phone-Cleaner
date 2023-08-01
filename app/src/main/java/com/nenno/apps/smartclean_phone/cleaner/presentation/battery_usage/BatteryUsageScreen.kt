package com.nenno.apps.smartclean_phone.cleaner.presentation.battery_usage

import android.app.usage.ExternalStorageStats
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.nenno.apps.smartclean_phone.cleaner.R
import com.nenno.apps.smartclean_phone.cleaner.util.RobotoFontFamily

@Composable
fun BatteryUsageScreen(
    viewModel: BatteryUsageViewModel,
    navController: NavController
) {
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (
            backButton,
            backText,
            listContainer
        ) = createRefs()

        IconButton(onClick = {

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

        Text(text = "Uso de bateria",
            fontFamily = RobotoFontFamily.robotoFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(backText) {
                start.linkTo(backButton.end, 12.dp)
                top.linkTo(backButton.top)
                bottom.linkTo(backButton.bottom)
            })

    }
}
