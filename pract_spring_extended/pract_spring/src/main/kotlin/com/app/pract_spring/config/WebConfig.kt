package com.app.pract_spring.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Suppress("HttpUrlsUsage")
@EnableWebMvc
@Configuration
class WebConfig: WebMvcConfigurer {

    @Value("\${app.auth.server}")
    val authServer: String? = null

    @Value("\${app.auth.port}")
    val authPort: Int? = null

    /**
     * Adds CORS mappings for the entire application, allows all headers and credentials.
     * The maximum age for pre-flight requests - 1800 seconds.
     *
     * @param registry the CorsRegistry to add mappings to.
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/api/**")
            .allowedOrigins("http://$authServer:$authPort")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowCredentials(true)
            .allowedHeaders("*")
            .maxAge(1800)
    }
}