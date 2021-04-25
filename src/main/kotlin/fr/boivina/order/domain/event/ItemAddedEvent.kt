package fr.boivina.order.domain.event

import fr.boivina.order.domain.model.Item
import fr.boivina.order.domain.model.OrderId
import java.time.LocalDate

data class ItemAddedEvent(val id: OrderId, val item: Item, val date: LocalDate)
