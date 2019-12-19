package com.example.validation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class ApplicationIntegrationSpec extends Specification {

    @Autowired
    ApplicationContext applicationContext

    def "should start Spring context"() {
       expect:
       applicationContext != null
    }
}
