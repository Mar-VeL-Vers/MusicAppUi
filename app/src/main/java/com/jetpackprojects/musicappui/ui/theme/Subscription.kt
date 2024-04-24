package com.jetpackprojects.musicappui.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Subscription() {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(text = "Manage Subscription")
    Card(
      modifier = Modifier.padding(8.dp),
      elevation = 4.dp
    ) {
      Column(
        modifier = Modifier
          .padding(8.dp)
      ) {
        Column {
          Text(text = "Musical")
          Text(text = "Free Tier")
          Row(
            modifier = Modifier
              .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
          ) {
            TextButton(onClick = { /*TODO*/ }) {
              Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "See All Plans")
                Icon(
                  imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                  contentDescription = "See All Plans"
                )
              }
            }
          }
        }
        Divider(
          modifier = Modifier
            .padding(horizontal = 8.dp),
          thickness = 2.dp
        )
        Row(
          modifier = Modifier
            .padding(16.dp)
        ) {
          Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Get a plan")
          Text(text = "Get a plan")
        }


      }

    }
  }

}