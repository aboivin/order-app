package fr.boivina.order.domain.query.repository

import fr.boivina.order.domain.query.OrderEntry

interface NotificationRepository {
    fun notifyOrderUpdate(orders: Iterable<OrderEntry>)
}
