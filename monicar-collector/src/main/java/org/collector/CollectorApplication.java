package org.collector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.common.TestBean;

import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = {"org.collector", "org.common"})
public class CollectorApplication {

    private final TestBean testBean;

    @Autowired
    public CollectorApplication(TestBean testBean) {
        this.testBean = testBean;
    }

    @PostConstruct
    public void dependencyTest() {
        testBean.dependencyTest();
    }

    public static void main(String[] args) {

        SpringApplication.run(CollectorApplication.class, args);
    }
}
