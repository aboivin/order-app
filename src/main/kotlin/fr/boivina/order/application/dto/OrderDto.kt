package fr.boivina.order.application.dto

import fr.boivina.order.domain.model.Item

data class OrderDto(val customerId: Long, var items: List<Item> = listOf())
