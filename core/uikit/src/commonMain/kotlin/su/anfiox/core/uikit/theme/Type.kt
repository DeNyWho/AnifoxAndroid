package su.anfiox.core.uikit.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.LineHeightStyle.Alignment
import androidx.compose.ui.text.style.LineHeightStyle.Trim
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import su.anifox.core.uikit.generated.resources.Res
import su.anifox.core.uikit.generated.resources.nunito_bold
import su.anifox.core.uikit.generated.resources.nunito_medium
import su.anifox.core.uikit.generated.resources.nunito_regular


private val NunitoFontFamily: FontFamily
    @Composable get()  = FontFamily(
        Font(resource = Res.font.nunito_regular, FontWeight.Normal, FontStyle.Normal),
        Font(resource = Res.font.nunito_medium, FontWeight.Medium, FontStyle.Normal),
        Font(resource = Res.font.nunito_bold, FontWeight.Bold, FontStyle.Normal)
    )


/**
 * Anifox typography
 */
@Composable
internal fun anifoxTypography(): Typography {
    return Typography(
        titleLarge = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = Alignment.Bottom,
                trim = Trim.LastLineBottom,
            ),
            fontFamily = NunitoFontFamily,
        ),
        titleMedium = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.1.sp,
            fontFamily = NunitoFontFamily,
        ),
        titleSmall = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.1.sp,
            fontFamily = NunitoFontFamily,
        ),
        bodyLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.25.sp,
            fontFamily = NunitoFontFamily,
        ),
        bodyMedium = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.25.sp,
            fontFamily = NunitoFontFamily,
        ),
        bodySmall = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 12.sp,
            letterSpacing = 0.4.sp,
            fontFamily = NunitoFontFamily,
        ),
        // Used for Button
        labelLarge = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.1.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = Alignment.Center,
                trim = Trim.LastLineBottom,
            ),
            fontFamily = NunitoFontFamily,
        ),
        labelSmall = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            lineHeight = 11.sp,
            letterSpacing = 0.1.sp,
            lineHeightStyle = LineHeightStyle(
                alignment = Alignment.Center,
                trim = Trim.LastLineBottom,
            ),
            fontFamily = NunitoFontFamily,
        )
    )
}