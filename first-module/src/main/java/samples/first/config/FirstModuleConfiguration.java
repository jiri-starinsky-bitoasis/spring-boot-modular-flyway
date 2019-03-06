package samples.first.config;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@EnableAutoConfiguration(exclude = { FlywayAutoConfiguration.class })
@Configuration
public class FirstModuleConfiguration {

    @Autowired
    private Environment env;

    @Bean(name = "firstModuleDataSource")
    @ConfigurationProperties(prefix = "first.datasource")
    public DataSource firstDatasource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("first.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("first.datasource.url"));
        dataSource.setPassword(env.getProperty("first.datasource.password"));
        dataSource.setUsername(env.getProperty("first.datasource.username"));
        dataSource.setSchema(env.getProperty("first.datasource.schema"));
        return dataSource;
    }

    @Autowired
    public void migrate(@Qualifier("firstModuleDataSource") DataSource dataSource) {
        final DriverManagerDataSource driverManagerDataSource = (DriverManagerDataSource) dataSource;
        final Flyway flyway = Flyway.configure()
                .dataSource(driverManagerDataSource)
                .schemas(driverManagerDataSource.getSchema())
                .load();

        flyway.migrate();
    }

//    @Bean
//    public FlywayConfigurationCustomizer firstModuleFlywayConfigurationCustomizer(@Qualifier("firstModuleDataSource")DataSource dataSource) {
//        final DriverManagerDataSource driverManagerDataSource = (DriverManagerDataSource) dataSource;
//        return configuration -> {
//            configuration.schemas(driverManagerDataSource.getSchema())
//                    .dataSource(driverManagerDataSource);
//        };
//    }

//    @Bean(name = "firstModuleFlywayMigrationStrategy")
//    public FlywayMigrationStrategy flywayMigrationStrategy(@Qualifier("firstModuleDataSource") DataSource dataSource) {
//        final DriverManagerDataSource driverManagerDataSource = (DriverManagerDataSource) dataSource;
//        return flyway -> {
//            flyway.setDataSource(driverManagerDataSource);
//            flyway.setSchemas(
//                    driverManagerDataSource.getSchema()
//            );
//            flyway.migrate();
//        };
//    }

}
