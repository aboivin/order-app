package fr.boivina.order.infrastructure

import fr.boivina.order.domain.query.OrderEntry
import fr.boivina.order.domain.query.repository.OrderRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InMemoryOrderRepository : OrderRepository {

    val db: MutableMap<String, OrderEntry> = HashMap()

    override fun findAllByOrderByIdAsc(): Iterable<OrderEntry> = db.values

    override fun findById(id: String): OrderEntry? = db[id]

    override fun save(order: OrderEntry) {
        db[order.orderId] = order
    }
}
