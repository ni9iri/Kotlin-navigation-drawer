package com.example.drawer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drawer.ui.theme.DrawerTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DrawerApp()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DrawerApp() {
    val scState = rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
    val coroutineScope = rememberCoroutineScope()
    val items = listOf("Page 1", "Page 2")
    Scaffold(
        scaffoldState = scState,
        topBar = {MyTopBar{coroutineScope.launch { scState.drawerState.open() }}},
        content = { Text(text = "Home screen")},
        drawerContent = { MyDrawer(items)}
    )
}

@Composable
fun MyTopBar(onMenuIconClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "My app")},
        navigationIcon = {
            IconButton(onClick = { onMenuIconClick }
            ) {
                Icon(Icons.Filled.Menu, contentDescription =null )
            }
        }
    )
}

@Composable
fun MyDrawer(items: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.baseline_ac_unit_24) ,
            contentDescription = null
        )
        Text(
            text = "Drawer sample",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )
        Divider()
        items.forEach { item ->
            Row(
                modifier = Modifier
                    .clickable(
                        indication = rememberRipple(
                            color = MaterialTheme.colors.primary,
                            bounded = true
                        ),
                        interactionSource = remember {
                            MutableInteractionSource()
                        }
                    ) {}
                    .padding(top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    Icons.Filled.Favorite, contentDescription = null,
                    modifier = Modifier.weight(2f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item,
                    modifier = Modifier.weight(8f)
                )
            }
        }
    }
}