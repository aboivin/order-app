package fr.boivina.order.domain.query

import java.math.BigDecimal

data class OrderEntry(val orderId: String, val customerId: Long, var itemCount: Int, var amount: BigDecimal, val items: MutableList<ItemEntry>)
