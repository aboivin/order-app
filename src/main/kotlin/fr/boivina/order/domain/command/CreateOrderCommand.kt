package fr.boivina.order.domain.command

import fr.boivina.order.domain.model.Item
import fr.boivina.order.domain.model.OrderId
import fr.boivina.order.domain.model.CustomerId
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CreateOrderCommand(@TargetAggregateIdentifier val orderId: OrderId, val customerId: CustomerId, val items: List<Item>)
