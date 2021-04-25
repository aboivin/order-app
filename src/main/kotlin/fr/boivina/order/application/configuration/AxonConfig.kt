package fr.boivina.order.application.configuration

import fr.boivina.order.domain.query.OrderEventListener
import fr.boivina.order.domain.query.repository.NotificationRepository
import fr.boivina.order.domain.query.repository.OrderRepository
import org.axonframework.common.jpa.EntityManagerProvider
import org.axonframework.common.transaction.TransactionManager
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory
import javax.validation.Validation
import javax.validation.ValidatorFactory


@Configuration
class AxonConfig {

    @Bean
    fun validatorFactory(autowireCapableBeanFactory: AutowireCapableBeanFactory): ValidatorFactory {
        return Validation.byDefaultProvider()
            .configure().constraintValidatorFactory(SpringConstraintValidatorFactory(autowireCapableBeanFactory))
            .buildValidatorFactory()
    }

    @Bean
    fun eventStorageEngine(
        entityManagerProvider: EntityManagerProvider,
        transactionManager: TransactionManager
                          ): EventStorageEngine {
        return InMemoryEventStorageEngine()
    }

    @Bean
    fun orderEventListener(
        notificationRepository: NotificationRepository,
        orderRepository: OrderRepository): OrderEventListener {
        return OrderEventListener(notificationRepository, orderRepository)
    }
}
