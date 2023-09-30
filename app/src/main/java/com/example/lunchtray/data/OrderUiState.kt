package com.example.lunchtray.data

import com.example.lunchtray.model.MenuItem

data class OrderUiState(
    val entree: MenuItem.EntreeItem = MenuItem.EntreeItem("", "", 0.0),
    val sideDish: MenuItem.SideDishItem = MenuItem.SideDishItem("", "", 0.0),
    val accompaniment: MenuItem.AccompanimentItem = MenuItem.AccompanimentItem("", "", 0.0),
    val subTotal: Double = 0.0,
    val tax: Double = 0.0,
    val total: Double = 0.0,
    val options: List<DataSource> = listOf()
)