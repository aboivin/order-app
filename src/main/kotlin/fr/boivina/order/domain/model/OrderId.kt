package fr.boivina.order.domain.model

import java.util.*

data class OrderId(val value: UUID) {

    override fun toString(): String {
        return value.toString()
    }
}
