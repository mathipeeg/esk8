package dk.mathi.esk8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class Esk8Application {

    public static void main(String[] args) {
        SpringApplication.run(Esk8Application.class, args);
    }

}
