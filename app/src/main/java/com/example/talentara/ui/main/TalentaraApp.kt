package com.example.talentara.ui.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.talentara.R
import com.example.talentara.R.drawable.activity_selected
import com.example.talentara.R.drawable.activity_unselected
import com.example.talentara.R.drawable.home_selected
import com.example.talentara.R.drawable.home_unselected
import com.example.talentara.R.drawable.order_selected
import com.example.talentara.R.drawable.order_unselected
import com.example.talentara.R.drawable.profile_selected
import com.example.talentara.R.drawable.profile_unselected
import com.example.talentara.ui.navigation.NavigationItem
import com.example.talentara.ui.navigation.Screen.Activity
import com.example.talentara.ui.navigation.Screen.Home
import com.example.talentara.ui.navigation.Screen.Order
import com.example.talentara.ui.navigation.Screen.Profile
import com.example.talentara.ui.screen.activity.NotificationScreen
import com.example.talentara.ui.screen.home.HomeScreen
import com.example.talentara.ui.screen.order.OrderScreen
import com.example.talentara.ui.screen.profile.ProfileScreen
import com.example.talentara.ui.theme.Greylight
import com.example.talentara.ui.theme.TalentaraTheme
import com.example.talentara.ui.theme.White

@Composable
fun TalentaraApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = { BottomBar(navController) },
        modifier = modifier,
        containerColor = Greylight
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Home.route,
            modifier = Modifier
                .padding(innerPadding)
        ) {
            composable(Home.route) {
                HomeScreen()
            }
            composable(Order.route) {
                OrderScreen()
            }
            composable(Activity.route) {
                NotificationScreen()
            }
            composable(Profile.route) {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                24.dp,
                RoundedCornerShape(
                    topStart = 26.dp,
                    topEnd = 26.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .height(65.dp)
    ) {
        NavigationBar(
            containerColor = White,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val navigationItems = listOf(
                NavigationItem(
                    title = stringResource(id = R.string.home),
                    icon = painterResource(id = home_unselected),
                    screen = Home,
                    selectedIcon = painterResource(id = home_selected),
                ),
                NavigationItem(
                    title = stringResource(id = R.string.orders),
                    icon = painterResource(id = order_unselected),
                    screen = Order,
                    selectedIcon = painterResource(id = order_selected),
                ),
                NavigationItem(
                    title = stringResource(id = R.string.activity),
                    icon = painterResource(id = activity_unselected),
                    screen = Activity,
                    selectedIcon = painterResource(id = activity_selected)
                ),
                NavigationItem(
                    title = stringResource(id = R.string.profile),
                    icon = painterResource(id = profile_unselected),
                    screen = Profile,
                    selectedIcon = painterResource(id = profile_selected)
                ),
            )
            navigationItems.map { item ->
                NavigationBarItem(
                    selected = currentRoute == item.screen.route,
                    label = {
                        Text(
                            item.title,
                            fontWeight = if (currentRoute == item.screen.route) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 10.sp
                        )
                    },
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = if (currentRoute == item.screen.route) item.selectedIcon else item.icon,
                            contentDescription = item.title,
                            modifier = modifier
                                .size(20.dp)
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    TalentaraTheme {
        val navController = rememberNavController()
        BottomBar(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun TalentaraAppPreview() {
    TalentaraTheme {
        TalentaraApp()
    }
}