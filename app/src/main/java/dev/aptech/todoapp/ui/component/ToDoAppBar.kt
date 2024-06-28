package dev.aptech.todoapp.ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import dev.aptech.todoapp.R
import dev.aptech.todoapp.ui.apptheme.ToDoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onVisibleClick: () -> Unit,
    isVisibleAll: Boolean,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ToDoTheme.colors.backPrimary,
            titleContentColor = ToDoTheme.colors.labelPrimary,
            scrolledContainerColor = ToDoTheme.colors.backPrimary
        ),
        title = {
            Text(
                stringResource(R.string.toolbar_title),
                overflow = TextOverflow.Ellipsis,
                color = ToDoTheme.colors.labelPrimary,
                style = ToDoTheme.typography.largeTitle

            )
        },
        actions = {
            IconButton(onClick = onVisibleClick) {
                Icon(
                    painter = if (isVisibleAll) painterResource(id = R.drawable.ic_visibility_24)
                    else painterResource(id = R.drawable.ic_visibility_off_24),
                    contentDescription = "Localized description",
                    tint = ToDoTheme.colors.colorBlue
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}