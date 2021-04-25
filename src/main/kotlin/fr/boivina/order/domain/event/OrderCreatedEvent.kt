package fr.boivina.order.domain.event

import fr.boivina.order.domain.model.Item
import fr.boivina.order.domain.model.OrderId
import fr.boivina.order.domain.model.CustomerId

data class OrderCreatedEvent(val orderId: OrderId, val customerId: CustomerId, val items: List<Item>)
