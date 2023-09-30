package com.example.lunchtray.model

import androidx.lifecycle.ViewModel
import com.example.lunchtray.data.OrderUiState
import com.example.lunchtray.model.MenuItem.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LunchTrayModel: ViewModel() {
    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()
    fun setEntree(entree: EntreeItem) {
        _uiState.update { currentValue ->
            currentValue.copy(
                entree = entree,
                subTotal = getSubTotal(_uiState.value.entree, _uiState.value.sideDish, _uiState.value.accompaniment),
                tax = getTax(_uiState.value.subTotal),
                total = getTotal(_uiState.value.subTotal, _uiState.value.tax)
            )
        }
    }
    fun setSideDish(sideDishItem: SideDishItem) {
        _uiState.update { currentValue ->
            currentValue.copy(
                sideDish = sideDishItem,
                subTotal = getSubTotal(_uiState.value.entree, _uiState.value.sideDish, _uiState.value.accompaniment),
                tax = getTax(_uiState.value.subTotal),
                total = getTotal(_uiState.value.subTotal, _uiState.value.tax)
            )
        }
    }
    fun resetOrder() {
        _uiState.value = OrderUiState()
    }
    fun setAccompaniment(accompanimentItem: AccompanimentItem) {
        _uiState.update { currentValue ->
            currentValue.copy(
                accompaniment = accompanimentItem,
                subTotal = getSubTotal(_uiState.value.entree, _uiState.value.sideDish, _uiState.value.accompaniment),
                tax = getTax(_uiState.value.subTotal),
                total = getTotal(_uiState.value.subTotal, _uiState.value.tax)
            )
        }
    }
    private fun getSubTotal(
        entree: EntreeItem,
        sideDishItem: SideDishItem,
        accompanimentItem: AccompanimentItem
    ) : Double {
        return entree.price + sideDishItem.price + accompanimentItem.price
    }
    private fun getTax(subTotal: Double) : Double {
        return .2*subTotal
    }
    private fun getTotal(subTotal: Double, tax: Double) : Double {
        return subTotal+tax
    }
}
