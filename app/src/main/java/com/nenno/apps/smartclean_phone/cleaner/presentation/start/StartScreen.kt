package com.nenno.apps.smartclean_phone.cleaner.presentation.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.nenno.apps.smartclean_phone.cleaner.presentation.navigation.NavigationScreen
import com.nenno.apps.smartclean_phone.cleaner.util.RobotoFontFamily

@Composable
fun StartScreen(
    startScreenViewModel: StartScreenViewModel? = null,
    navController: NavController? = null
) {

    ConstraintLayout(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        val (
            welcomeText,
            startButton,
            acceptTermsCheckBox,
            acceptTermsText
        ) = createRefs()
        val robotoFamily = RobotoFontFamily.robotoFontFamily
        val themeColors = MaterialTheme.colorScheme

        var checkBoxState by rememberSaveable { mutableStateOf(false) }

        Text(
            text = "Limpe seu telefone de um jeito rápido, simples, seguro, e, de graça!",
            textAlign = TextAlign.Center,
            fontFamily = robotoFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .constrainAs(welcomeText) {
                    start.linkTo(parent.start, 24.dp)
                    end.linkTo(parent.end, 24.dp)
                    bottom.linkTo(startButton.top, 24.dp)
                    width = Dimension.fillToConstraints
                }
        )

        Button(onClick = {
            navController?.navigate(NavigationScreen.Home.route) {
                navController.popBackStack()
            }
                         },
            modifier = Modifier
                .constrainAs(startButton) {
                    start.linkTo(parent.start, 48.dp)
                    end.linkTo(parent.end, 48.dp)
                    bottom.linkTo(acceptTermsCheckBox.top, 12.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(52.dp)
                },
            colors = ButtonDefaults.buttonColors(
                containerColor = themeColors.primary
            )
            ) {
                Text(
                    text = "Experimentar",
                    textAlign = TextAlign.Center,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = themeColors.onPrimary
                )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.constrainAs(acceptTermsCheckBox) {
                start.linkTo(startButton.start)
                bottom.linkTo(parent.bottom, 24.dp)
            }
        ) {
            Checkbox(
                checked = checkBoxState,
                onCheckedChange = {
                    checkBoxState = it
                }
            )

            HyperlinkText(
                fullText = "Eu aceito os Termos de Uso",
                linkText = listOf("Termos de Uso"),
                linkTextDecoration = TextDecoration.Underline,
                hyperlinks = listOf("https://www.google.com")
            )
        }
    }


}

@Composable
fun HyperlinkText(
    modifier: Modifier = Modifier,
    fullText: String,
    linkText: List<String>,
    linkTextColor: Color = Color.Blue,
    linkTextFontWeight: FontWeight = FontWeight.Medium,
    linkTextDecoration: TextDecoration = TextDecoration.Underline,
    hyperlinks: List<String> = listOf("https://stevdza-san.com"),
    fontSize: TextUnit = TextUnit.Unspecified
) {
    val annotatedString = buildAnnotatedString {
        append(fullText)
        linkText.forEachIndexed { index, link ->
            val startIndex = fullText.indexOf(link)
            val endIndex = startIndex + link.length
            addStyle(
                style = SpanStyle(
                    color = linkTextColor,
                    fontSize = fontSize,
                    fontWeight = linkTextFontWeight,
                    textDecoration = linkTextDecoration
                ),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = hyperlinks[index],
                start = startIndex,
                end = endIndex
            )
        }
        addStyle(
            style = SpanStyle(
                fontSize = fontSize
            ),
            start = 0,
            end = fullText.length
        )
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = {
            annotatedString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}