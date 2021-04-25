package fr.boivina.order.domain.model

import java.math.BigDecimal
import java.math.BigDecimal.ZERO

data class Price(val value: BigDecimal) {

    init {
        require(value > ZERO) { "Price must be positive" }
    }

    companion object {
        fun of(price: Double) = Price(BigDecimal.valueOf(price))
    }

    operator fun plus(that: Price) = Price(this.value.add(that.value))
}
