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
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lunchtray.R

@Composable
fun OrderCheckoutScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onNextClick: () -> Unit,
    onCancelClick: () -> Unit,
    subTotal: Double = 0.0,
    tax: Double = 0.0,
    total: Double = 0.0,
    list: List<Pair<String, Double>> = listOf()
) {
    Column (
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.order_summary), fontWeight = FontWeight.SemiBold, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(8.dp))

        ParameterRow(parameter = list[0].first, price = list[0].second.toString(), modifier = Modifier)
        ParameterRow(parameter = list[1].first, price = list[1].second.toString(), modifier = Modifier)
        ParameterRow(parameter = list[2].first, price = list[2].second.toString(), modifier = Modifier)
        Divider(Modifier.padding(vertical = 16.dp))
        Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End){
            Text(text = stringResource(id = R.string.subtotal) + subTotal, fontSize = 16.sp)
            Text(text = stringResource(id = R.string.tax) + tax, fontSize = 16.sp)
            Text(text = stringResource(id = R.string.total) + total, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row (Modifier.fillMaxWidth()){
            OutlinedButton(onClick = { onCancelClick() }, modifier = Modifier.weight(1f)) {
                Text(text = stringResource(id = R.string.cancel))
            }
            Spacer(modifier = Modifier.width(6.dp))
            Button(onClick = { onNextClick() }, Modifier.weight(1f)) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
}

@Composable
fun ParameterRow(parameter: String, price: String, modifier: Modifier = Modifier) {
    Row (modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = parameter, fontSize = 18.sp)
        Text(text = price)
    }
}
@Preview
@Composable
fun OrderCheckoutScreenPreview() {
    OrderCheckoutScreen(onNextClick = {}, onCancelClick = {})
}