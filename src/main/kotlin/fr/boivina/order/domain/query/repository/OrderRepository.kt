package fr.boivina.order.domain.query.repository

import fr.boivina.order.domain.query.OrderEntry

interface OrderRepository {
    fun findAllByOrderByIdAsc(): Iterable<OrderEntry>
    fun findById(id: String): OrderEntry?
    fun save(order: OrderEntry)
}
