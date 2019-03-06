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
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import samples.first.entities.FirstEntity;
import samples.first.repositories.FirstEntityRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Properties;

@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "firstModuleEntityManagerFactory",
        transactionManagerRef = "firstModuleTransactionManager",
        basePackageClasses = {FirstEntityRepository.class}
)
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
                .locations("classpath:db.migration.first")
                .load();

        flyway.migrate();
    }


    @Bean(name = "firstModuleEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("firstModuleDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("samples.first.entities")
                .persistenceUnit("firstEntity")
                .build();
    }

    @Bean(name = "firstModuleTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("firstModuleEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

//    @Bean
//    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
//        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
//    }


}
