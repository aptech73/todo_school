package dev.aptech.todoapp.ui.apptheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import dagger.Component

data class ToDoColors(
    val supportSeparator: Color,
    val supportOverlay: Color,
    val labelPrimary: Color,
    val labelSecondary: Color,
    val labelTertiary: Color,
    val labelDisable: Color,
    val colorRed: Color,
    val colorGreen: Color,
    val colorBlue: Color,
    val colorGray: Color,
    val colorGrayLight: Color,
    val colorWhite: Color,
    val backPrimary: Color,
    val backSecondary: Color,
    val backElevated: Color
)

data class ToDoTypography(
    val largeTitle: TextStyle,
    val title: TextStyle,
    val button: TextStyle,
    val body: TextStyle,
    val subhead: TextStyle
)

data class ToDoShape(
    val paddingSmall: Dp,
    val paddingMedium: Dp
)

object ToDoTheme {
    val colors: ToDoColors
        @Composable
        get() = LocalToDoColors.current

    val typography: ToDoTypography
        @Composable
        get() = LocalToDoTypography.current

    val shape: ToDoShape
        @Composable
        get() = LocalToDoShape.current
}

val LocalToDoColors = staticCompositionLocalOf<ToDoColors> {
    error("No colors provider")
}

val LocalToDoTypography = staticCompositionLocalOf<ToDoTypography> {
    error("No font provider")
}

val LocalToDoShape = staticCompositionLocalOf<ToDoShape> {
    error("No shapes provider")
}