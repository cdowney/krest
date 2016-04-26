package net.cad.config

import org.slf4j.LoggerFactory
import org.springframework.boot.bind.RelaxedPropertyResolver
import org.springframework.context.EnvironmentAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.util.StopWatch
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.Contact
import springfox.documentation.service.SecurityScheme
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.ApiKeyVehicle
import springfox.documentation.swagger.web.SecurityConfiguration
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

@Configuration
@EnableSwagger2
open class SwaggerConfig : EnvironmentAware {

    private lateinit var propertyResolver: RelaxedPropertyResolver;

    private fun apiInfo(): ApiInfo {
        val contactInfo = Contact(
                propertyResolver.getProperty("contact.name"),
                propertyResolver.getProperty("contact.url"),
                propertyResolver.getProperty("contact.email"))

        return ApiInfo(
                propertyResolver.getProperty("api.title"),
                propertyResolver.getProperty("api.description"),
                propertyResolver.getProperty("api.version"),
                propertyResolver.getProperty("api.termsofserviceurl"),
                contactInfo,
                propertyResolver.getProperty("api.license"),
                propertyResolver.getProperty("api.licenseurl"))
    }

    override fun setEnvironment(environment: Environment) {
        this.propertyResolver = RelaxedPropertyResolver(environment, "swagger.")
    }

    @Bean
    open fun api(): Docket {
        log.info("Starting Swagger")
        val watch = StopWatch()
        watch.start()
        val docket = Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.cad.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .forCodeGeneration(true)
                .directModelSubstitute(java.time.LocalDate::class.java, String::class.java)
                .directModelSubstitute(java.time.ZonedDateTime::class.java, Date::class.java)
                .directModelSubstitute(java.time.LocalDateTime::class.java, Date::class.java)
        watch.stop()
        log.info("Started Swagger in {} ms", watch.totalTimeMillis)
        return docket
    }

    @Bean
    open fun apiKey(): SecurityScheme {
        return ApiKey("myKey", "Authorization", "header")
    }

    @Bean
    open fun security(): SecurityConfiguration {
        return SecurityConfiguration(
                "", // clientId
                "", // clientSecret
                "", // realm
                "Salesforce Integration API", // appName
                "api_key", // apiKeyValue
                ApiKeyVehicle.HEADER, //apiKeyVechicle
                "Authorization", // apiKeyName
                ",")                         // scopeSeparator
    }

    companion object {
        private val log = LoggerFactory.getLogger(SwaggerConfig::class.java)
    }
}
