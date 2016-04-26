package net.cad.config

import org.h2.server.web.WebServlet
import org.h2.tools.Server
import org.springframework.boot.context.embedded.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


/**
 * Create an in memory H2 DataSource.
 */
@Configuration
@Profile(Constants.DEV, Constants.TEST)
@EnableJpaRepositories("net.cad.repositories")
open class InMemoryDataSource {

    /**
     * Open the TCP port for the H2 database, so it is available remotely.
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    open fun h2TCPServer(): Server? {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers")
    }

    /**
     * Register the servlet for the H2 database console.
     *
     * open localhost:8080/console
     *
     * Then select 'Generic H2 (Embedded)'
     *
     * Click connect
     */
    @Bean
    open fun h2ConsoleServletRegistration(): ServletRegistrationBean {
        val h2ConsoleBean = ServletRegistrationBean(WebServlet())
        h2ConsoleBean.addUrlMappings("/console/*")
        return h2ConsoleBean;
    }

    /*
    @Bean
    open fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        var entityManagerFactory = LocalContainerEntityManagerFactoryBean()
        var p = Properties()
        p.put("spring.jpa.hibernate.ddl-auto", "create-drop")
        entityManagerFactory.setJpaProperties(p)
        return entityManagerFactory
    }
    */
}