package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.jdoodle.controllers" })
public class JDoodleOrchestrator {
    public static void main(String[] args)
    {
        SpringApplication.run(JDoodleOrchestrator.class,args);
    }

}
