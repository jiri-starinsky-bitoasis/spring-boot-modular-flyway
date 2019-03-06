package samples;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import samples.first.config.FirstModuleConfiguration;
import samples.first.entities.FirstEntity;
import samples.first.repositories.FirstEntityRepository;
import samples.second.config.SecondModuleConfiguration;
import samples.second.entities.SecondEntity;
import samples.second.repositories.SecondEntityRepository;

@SpringBootApplication
@Import({FirstModuleConfiguration.class, SecondModuleConfiguration.class})

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner runner(FirstEntityRepository firstEntityRepository, SecondEntityRepository secondEntityRepository) {
        return args -> {
            final FirstEntity firstEntity = new FirstEntity();
            firstEntity.setId(1);
            firstEntity.setFirstName("john");
            firstEntityRepository.save(firstEntity);

            final SecondEntity secondEntity = new SecondEntity();
            secondEntity.setId(1);
            secondEntity.setSecondName("doe");
            secondEntityRepository.save(secondEntity);

        };
    }
}
