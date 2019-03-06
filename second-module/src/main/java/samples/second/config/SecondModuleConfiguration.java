package samples.second.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@EnableAutoConfiguration(exclude = { FlywayAutoConfiguration.class })
@Configuration
public class SecondModuleConfiguration {

    @Autowired
    private Environment env;

    @Bean(name = "secondModuleDataSource")
    @ConfigurationProperties(prefix = "second.datasource")
    public DataSource secondDatasource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("second.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("second.datasource.url"));
        dataSource.setPassword(env.getProperty("second.datasource.password"));
        dataSource.setUsername(env.getProperty("second.datasource.username"));
        dataSource.setSchema(env.getProperty("second.datasource.schema"));
        return dataSource;
    }

    @Autowired
    public void migrate(@Qualifier("secondModuleDataSource") DataSource dataSource) {
        final DriverManagerDataSource driverManagerDataSource = (DriverManagerDataSource) dataSource;
        final Flyway flyway = Flyway.configure()
                .dataSource(driverManagerDataSource)
                .schemas(driverManagerDataSource.getSchema())
                .locations("classpath:db.migration.second")
                .load();

        flyway.migrate();
    }

}
