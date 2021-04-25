package fr.boivina.order.application.domain.model

import fr.boivina.order.domain.command.AddItemCommand
import fr.boivina.order.domain.command.CreateOrderCommand
import fr.boivina.order.domain.event.ItemAddedEvent
import fr.boivina.order.domain.event.OrderCreatedEvent
import fr.boivina.order.domain.model.*
import org.assertj.core.api.Assertions.assertThat
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate.now
import java.util.*

class OrderTest {
    private var fixture: FixtureConfiguration<Order> = AggregateTestFixture(Order::class.java)

    @BeforeEach
    fun setUp() {
        fixture = AggregateTestFixture(Order::class.java)
    }

    @Test
    fun shouldCreateAnOrder() {
        val randomUUID = UUID.randomUUID()
        fixture.given()
            .`when`(CreateOrderCommand(OrderId(randomUUID), CustomerId(777), listOf()))
            .expectSuccessfulHandlerExecution()
            .expectEvents(OrderCreatedEvent(OrderId(randomUUID), CustomerId(777), listOf()))
            .expectState {
                assertThat(it.id).isEqualTo(OrderId(randomUUID))
                assertThat(it.customerId).isEqualTo(CustomerId(777))
                assertThat(it.items).isEmpty()
            }
    }

    @Test
    fun shouldAddAnItem() {
        val randomUUID = UUID.randomUUID()
        fixture.given(OrderCreatedEvent(OrderId(randomUUID), CustomerId(777), listOf()))
            .`when`(AddItemCommand(OrderId(randomUUID), Item("ITEM_1", Price.of(7.0))))
            .expectSuccessfulHandlerExecution()
            .expectEvents(ItemAddedEvent(OrderId(randomUUID), Item("ITEM_1", Price.of(7.0)), now()))
            .expectState {
                assertThat(it.id).isEqualTo(OrderId(randomUUID))
                assertThat(it.customerId).isEqualTo(CustomerId(777))
                assertThat(it.items).containsExactlyInAnyOrder(Item("ITEM_1", Price.of(7.0)))
            }
    }
}
