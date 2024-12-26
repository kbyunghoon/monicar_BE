package org.emulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.common.TestBean;

import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = {"org.emulator", "org.common"})
public class EmulatorApplication {

    private final TestBean testBean;

    @Autowired
    public EmulatorApplication(TestBean testBean) {
        this.testBean = testBean;
    }

    @PostConstruct
    public void dependencyTest() {
        testBean.dependencyTest();
    }

    public static void main(String[] args) {
        SpringApplication.run(EmulatorApplication.class, args);
    }
}
