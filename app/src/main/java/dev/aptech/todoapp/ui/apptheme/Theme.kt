package dev.aptech.todoapp.ui.apptheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    val colors = when (darkTheme) {
        true -> baseDarkColors
        false -> baseLightColors
    }
    val typography = ToDoTypography(
        largeTitle = TextStyle(
            fontSize = 32.sp,
            lineHeight = 38.sp,
            fontWeight = FontWeight.Medium
        ),
        title = TextStyle(
            fontSize = 20.sp,
            lineHeight = 32.sp,
            fontWeight = FontWeight.Medium
        ),
        button = TextStyle(
            fontSize = 14.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Medium
        ),
        body = TextStyle(
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Normal
        ),
        subhead = TextStyle(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Normal
        )
    )
    val shape = ToDoShape(
        paddingSmall = 8.dp,
        paddingMedium = 16.dp
    )
    
    CompositionLocalProvider(
        LocalToDoColors provides colors,
        LocalToDoTypography provides typography,
        LocalToDoShape provides shape,
        content = content
    )
}