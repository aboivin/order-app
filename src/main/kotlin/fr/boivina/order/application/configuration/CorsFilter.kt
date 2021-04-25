package fr.boivina.order.application.configuration

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CorsFilter : Filter {

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
        LOGGER.info("Cors filter initialization")
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        val requestToUse = servletRequest as HttpServletRequest
        val responseToUse = servletResponse as HttpServletResponse
        if (isRequestValid(requestToUse)) {
            responseToUse.setHeader("Access-Control-Allow-Credentials", "true")
            responseToUse.setHeader("Access-Control-Allow-Headers", "*")
            responseToUse.setHeader("Access-Control-Allow-Methods", "*")
            responseToUse.setHeader("Access-Control-Allow-Origin", requestToUse.getHeader("Origin"))
        }
        filterChain.doFilter(requestToUse, responseToUse)
    }

    private fun isRequestValid(requestToUse: HttpServletRequest) =
        requestToUse.getHeader("Origin") == null || requestToUse.getHeader("Origin") in setOf("http://localhost:8080", "http://localhost:3000")


    override fun destroy() {}

    companion object {
        private val LOGGER = LoggerFactory.getLogger(CorsFilter::class.java)
    }
}
