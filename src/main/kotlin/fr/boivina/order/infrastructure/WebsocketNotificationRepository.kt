package fr.boivina.order.infrastructure

import fr.boivina.order.domain.query.OrderEntry
import fr.boivina.order.domain.query.repository.NotificationRepository
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Repository

@Repository
class WebsocketNotificationRepository(private val messagingTemplate: SimpMessageSendingOperations) : NotificationRepository {

    override fun notifyOrderUpdate(orders: Iterable<OrderEntry>) {
        messagingTemplate.convertAndSend("/topic/orders.updates", orders)
    }
}
