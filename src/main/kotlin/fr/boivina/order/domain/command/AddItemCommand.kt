package fr.boivina.order.domain.command

import fr.boivina.order.domain.model.Item
import fr.boivina.order.domain.model.OrderId
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class AddItemCommand(@TargetAggregateIdentifier val orderId: OrderId, val item: Item)
