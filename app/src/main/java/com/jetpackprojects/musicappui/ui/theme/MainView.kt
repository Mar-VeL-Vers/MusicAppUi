package com.jetpackprojects.musicappui.ui.theme


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jetpackprojects.musicappui.MainViewModel
import com.jetpackprojects.musicappui.R
import com.jetpackprojects.musicappui.Screen
import com.jetpackprojects.musicappui.bottomScreens
import com.jetpackprojects.musicappui.screensInDrawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainView() {
  val scaffoldState: ScaffoldState = rememberScaffoldState()
  val scope: CoroutineScope = rememberCoroutineScope()
  val viewModel: MainViewModel = viewModel()

  val isSheetFullScreen by remember { mutableStateOf(false) }
  val modifier = if (isSheetFullScreen) Modifier.fillMaxSize() else Modifier.fillMaxWidth()

  //allow us to find out on wich route(screen) we are
  val controller: NavController = rememberNavController()
  val navBackStackEntry by controller.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route

  val dialogOpen = remember {
    mutableStateOf(false)

  }

  val currentScreen = remember {
    viewModel.currentScreen.value
  }

  val title = remember {
    mutableStateOf(currentScreen.title)
  }
  val modalSheetState = androidx.compose.material.rememberModalBottomSheetState(
    initialValue = ModalBottomSheetValue.Hidden,
    confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
  )

  val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 12.dp

  val bottomBar: @Composable () -> Unit = {
    if (currentScreen is Screen.DrawerScreen || currentScreen == Screen.BottomScreen.Home) {
      BottomNavigation(
        modifier = Modifier
          .wrapContentSize()
      ) {
        bottomScreens.forEach { item ->
          val isSelected = currentRoute == item.bottomRoute
          val tint = if (isSelected) Color.White else Color.Black
          BottomNavigationItem(
            selected = currentRoute == item.bottomRoute,
            onClick = {
              controller.navigate(item.bottomRoute)
              title.value = item.bottomTitle
            },
            icon = {
              Icon(contentDescription = item.bottomTitle, painter = painterResource(id = item.icon))
            },
            label = { Text(text = item.bottomTitle, color = tint) },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.Black
          )
        }
      }
    }
  }

  ModalBottomSheetLayout(
    sheetState = modalSheetState,
    sheetShape = RoundedCornerShape(topStart = roundedCornerRadius, topEnd = roundedCornerRadius),
    sheetContent = {
      MoreBottomSheet(modifier = modifier)
    }
  ) {
    Scaffold(
      bottomBar = bottomBar,
      topBar = {
        TopAppBar(
          title = { Text(title.value) },
          actions = {
            IconButton(
              onClick = {
                scope.launch {
                  if (modalSheetState.isVisible) {
                    modalSheetState.hide()
                  } else {
                    modalSheetState.show()
                  }
                }
              }) {
              Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)

            }
          },
          navigationIcon = {
            IconButton(
              onClick = {
                //open the drawer
                scope.launch {
                  scaffoldState.drawerState.open()
                }
              }) {
              Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Menu"
              )
            }
          }
        )
      },
      scaffoldState = scaffoldState,
      drawerContent = {
        LazyColumn(
          Modifier.padding(16.dp)
        ) {
          items(screensInDrawer) { item ->
            DrawerItem(selected = currentRoute == item.drawerRoute, item = item) {
              scope.launch {
                scaffoldState.drawerState.close()
              }
              if (item.drawerRoute == "add_account") {
                //open add account dialog
                dialogOpen.value = true
              } else {
                controller.navigate(item.drawerRoute)
                title.value = item.drawerTitle
              }
            }
          }
        }
      }
    )

    {
      Navigation(navController = controller, viewModel = viewModel, pd = it)
      AccountDialog(dialogOpen = dialogOpen)

    }
  }
}


@Composable
fun MoreBottomSheet(modifier: Modifier) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(300.dp)
      .background(MaterialTheme.colors.primarySurface)

  ) {
    Column(
      modifier = Modifier
        .padding(16.dp),
      verticalArrangement = Arrangement.SpaceBetween,

      ) {
      Row(
        modifier = Modifier
          .padding(16.dp)
      ) {
        Icon(
          modifier = Modifier
            .padding(end = 8.dp),
          painter = painterResource(id = R.drawable.baseline_settings_24),
          contentDescription = "Settings"
        )
        Text(text = "settings", fontSize = 20.sp, color = Color.White)
      }

      Row(
        modifier = Modifier
          .padding(16.dp)
      ) {
        Icon(
          modifier = Modifier
            .padding(end = 8.dp),
          painter = painterResource(id = R.drawable.baseline_share_24),
          contentDescription = "Share"
        )
        Text(text = "Share", fontSize = 20.sp, color = Color.White)
      }

      Row(
        modifier = Modifier
          .padding(16.dp)
      ) {
        Icon(
          modifier = Modifier
            .padding(end = 8.dp),
          painter = painterResource(id = R.drawable.baseline_help_center_24),
          contentDescription = "Help"
        )
        Text(text = "Help", fontSize = 20.sp,color=Color.White)
      }
    }
  }
}

@Composable
fun DrawerItem(
  selected: Boolean,
  item: Screen.DrawerScreen,
  onDrawersItemClicked: () -> Unit

) {
  val background = if (selected) Color.DarkGray else Color.White
  Row(modifier = Modifier
    .fillMaxWidth()
    .padding(horizontal = 8.dp, vertical = 16.dp)
    .background(background)
    .clickable {
      onDrawersItemClicked()
    }) {
    Icon(
      painter = painterResource(id = item.icon),
      contentDescription = item.drawerTitle,
      Modifier.padding(end = 8.dp, top = 4.dp)
    )
    Text(
      text = item.drawerTitle,
      style = MaterialTheme.typography.h5
    )
  }

}

@Composable
fun Navigation(navController: NavController, viewModel: MainViewModel, pd: PaddingValues) {

  NavHost(
    navController = navController as NavHostController,
    startDestination = Screen.DrawerScreen.Account.route,
    modifier = Modifier.padding(pd)
  ) {
    composable(Screen.DrawerScreen.Account.route) {
      AccountView()
    }
    composable(Screen.DrawerScreen.Subscription.route) {
      Subscription()
    }
    composable(Screen.BottomScreen.Home.bottomRoute) {
      Home()
    }
    composable(Screen.BottomScreen.Browse.bottomRoute) {
      // TODO:
      Browse()
    }
    composable(Screen.BottomScreen.Library.bottomRoute) {
      // TODO:
      Library()
    }

  }


}