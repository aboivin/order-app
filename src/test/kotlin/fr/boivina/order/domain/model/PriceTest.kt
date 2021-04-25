package fr.boivina.order.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.lang.IllegalArgumentException

class PriceTest {

    @ParameterizedTest
    @ValueSource(doubles = [1.0, 3.0, 5.0, 15.0])
    fun shouldAcceptPositivePrice(value: Double) {
        val throwable = catchThrowable { Price.of(value) }
        assertThat(throwable).isNull()
    }

    @ParameterizedTest
    @ValueSource(doubles = [-1.0, -3.0, 0.0])
    fun shouldRejectNegativePrice(value: Double) {
        val throwable = catchThrowable { Price.of(value) }
        assertThat(throwable).isInstanceOf(IllegalArgumentException::class.java)
    }
}
