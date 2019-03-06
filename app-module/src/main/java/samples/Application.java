package samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import samples.first.config.FirstModuleConfiguration;
import samples.second.config.SecondModuleConfiguration;

@SpringBootApplication
@Import({FirstModuleConfiguration.class, SecondModuleConfiguration.class})

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
