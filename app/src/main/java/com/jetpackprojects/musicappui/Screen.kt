package com.jetpackprojects.musicappui

import androidx.annotation.DrawableRes

sealed class Screen(
  val title: String,
  val route: String
) {

  sealed class BottomScreen(
    val bottomTitle:String,
    val bottomRoute:String,
    @DrawableRes val icon:Int
  ):Screen(bottomTitle,bottomRoute)
  {
    object Home: BottomScreen("home","home",R.drawable.baseline_music_video_24)
    object Library:BottomScreen("library","library",R.drawable.baseline_video_library_24)
    object Browse:BottomScreen("browse","browse",R.drawable.baseline_browse_gallery_24)

  }



  sealed class DrawerScreen(
    val drawerTitle: String,
    val drawerRoute: String,
    @DrawableRes val icon: Int
  ) : Screen(drawerTitle, drawerRoute) {
    object Account : DrawerScreen(
      "Account",
      "account",
      R.drawable.baseline_account_circle_24
    )

    object Subscription : DrawerScreen(
      "Subscription",
      "subscribe",
      R.drawable.baseline_subscriptions_24
    )

    object AddAccount : DrawerScreen(
      "Add account",
      "add_account",
      R.drawable.baseline_person_add_alt_1_24

    )

  }

}
val bottomScreens=listOf(
  Screen.BottomScreen.Home,
  Screen.BottomScreen.Browse,
  Screen.BottomScreen.Library
)
val screensInDrawer = listOf(
  Screen.DrawerScreen.Account,
  Screen.DrawerScreen.Subscription,
  Screen.DrawerScreen.AddAccount
)

