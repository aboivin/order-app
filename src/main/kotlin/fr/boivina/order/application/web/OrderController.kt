package fr.boivina.order.application.web

import fr.boivina.order.application.dto.OrderDto
import fr.boivina.order.application.dto.AddItemDto
import fr.boivina.order.domain.command.CreateOrderCommand
import fr.boivina.order.domain.command.AddItemCommand
import fr.boivina.order.domain.model.*
import fr.boivina.order.domain.query.OrderEntry
import fr.boivina.order.domain.query.repository.OrderRepository
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.annotation.SendToUser
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.stereotype.Controller
import java.util.*


@Controller
@MessageMapping("/orders")
class OrderController(private val commandGateway: CommandGateway,
                      private val orderRepository: OrderRepository) {

    @SubscribeMapping
    fun all(): Iterable<OrderEntry> {
        return orderRepository.findAllByOrderByIdAsc()
    }

    @SubscribeMapping("/{id}")
    operator fun get(@DestinationVariable id: String): OrderEntry {
        return orderRepository.findById(id) ?: throw IllegalArgumentException("Not found")
    }

    @MessageMapping("/create")
    fun create(orderDto: OrderDto) {
        val id = OrderId(UUID.randomUUID())
        val customerId = CustomerId(orderDto.customerId)
        val command = CreateOrderCommand(id, customerId, orderDto.items)
        commandGateway.send<Any>(command)
    }

    @MessageMapping("/addItem")
    fun addItem(addItemDto: AddItemDto) {
        val orderId = OrderId(UUID.fromString(addItemDto.orderId))
        val command = AddItemCommand(orderId, Item(addItemDto.name, Price.of(addItemDto.price)))
        commandGateway.send<Any>(command)
    }

    @MessageExceptionHandler
    @SendToUser("/queue/error")
    fun handleException(exception: Throwable): String? {
        return exception.message
    }
}
