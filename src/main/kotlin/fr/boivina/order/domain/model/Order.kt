package fr.boivina.order.domain.model

import fr.boivina.order.domain.command.AddItemCommand
import fr.boivina.order.domain.command.CreateOrderCommand
import fr.boivina.order.domain.event.ItemAddedEvent
import fr.boivina.order.domain.event.OrderCreatedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.spring.stereotype.Aggregate
import java.time.LocalDate.now

@Aggregate
class Order {

    @AggregateIdentifier
    internal var id: OrderId? = null
    internal var customerId: CustomerId? = null
    internal var items: MutableList<Item> = mutableListOf()

    private constructor() {}

    @CommandHandler
    constructor(command: CreateOrderCommand) {
        apply(OrderCreatedEvent(command.orderId, command.customerId, command.items))
    }

    @CommandHandler
    fun addItem(command: AddItemCommand) {
        apply(ItemAddedEvent(command.orderId, command.item, now()))
    }

    @EventSourcingHandler
    fun on(event: OrderCreatedEvent) {
        id = event.orderId
        customerId = event.customerId
        items.addAll(event.items)
    }

    @EventSourcingHandler
    fun on(event: ItemAddedEvent) {
        items.add(event.item)
    }
}
