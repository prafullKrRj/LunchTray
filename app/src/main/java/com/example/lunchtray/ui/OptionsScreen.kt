package com.example.lunchtray.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lunchtray.R
import com.example.lunchtray.model.MenuItem

@Composable
fun OptionsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    options: List<MenuItem>,
    onNextClick: (Int) -> Unit,
    onCancelClick: () -> Unit
) {
    var selectedValue by rememberSaveable { mutableStateOf(0) }
    Column (
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Column (Modifier.fillMaxWidth()){
            options.forEach { item ->
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .selectable(
                            selected = selectedValue == getIndex(item, options),
                            onClick = {
                                selectedValue = getIndex(item, options)
                            }
                        )
                        .padding(vertical = 4.dp, horizontal = 4.dp)
                ){
                    RadioButton(selected = selectedValue == getIndex(item, options), onClick = {
                        selectedValue = getIndex(item, options)
                    })
                    Column {
                        Text(text = item.name, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = item.description, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = item.getFormattedPrice(), fontSize = 10.sp)
                    }
                }
                Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row (Modifier.fillMaxWidth()){
                OutlinedButton(onClick = { onCancelClick() }, modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                Spacer(modifier = Modifier.width(6.dp))
                Button(onClick = { onNextClick(selectedValue) }, Modifier.weight(1f)) {
                    Text(text = stringResource(id = R.string.next))
                }
            }
        }
        
    }
}
private fun getIndex(item: MenuItem, options: List<MenuItem>): Int {
    return options.indexOf(item)
}