package fr.boivina.order.domain.query

import java.time.LocalDate

data class ItemEntry(val name: String, val price: Double, val date: LocalDate)
