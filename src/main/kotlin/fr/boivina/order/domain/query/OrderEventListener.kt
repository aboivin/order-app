package fr.boivina.order.domain.query

import fr.boivina.order.domain.event.OrderCreatedEvent
import fr.boivina.order.domain.event.ItemAddedEvent
import fr.boivina.order.domain.query.repository.NotificationRepository
import fr.boivina.order.domain.query.repository.OrderRepository
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

class OrderEventListener constructor(
    private val notificationRepository: NotificationRepository,
    private val orderRepository: OrderRepository) {

    @EventHandler
    fun on(event: OrderCreatedEvent) {
        val order = OrderEntry(event.orderId.toString(),
                               event.customerId.value,
                               event.items.size,
                               event.items.sumOf { it.price.value },
                               mutableListOf())
        orderRepository.save(order)
        broadcastUpdates()
    }

    @EventHandler
    fun on(event: ItemAddedEvent) {
        val order = orderRepository.findById(event.id.toString())
        order!!.itemCount++
        order.amount += event.item.price.value
        order.items += ItemEntry(event.item.name, event.item.price.value.toDouble(), event.date)
        orderRepository.save(order)
        broadcastUpdates()
    }

    private fun broadcastUpdates() {
        val orders: Iterable<OrderEntry> = orderRepository.findAllByOrderByIdAsc()
        notificationRepository.notifyOrderUpdate(orders)
    }
}
