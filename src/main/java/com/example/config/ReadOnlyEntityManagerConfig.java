package com.example.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.example",
    includeFilters = @ComponentScan.Filter(ReadOnlyRepository.class),
    entityManagerFactoryRef = "readEntityManagerFactory"
)
public class ReadOnlyEntityManagerConfig {

  @Value("${spring.datasource.username}")
  private String username;
  @Value("${spring.datasource.password}")
  private String password;
  @Value("${spring.datasource.read-url}")
  private String readUrl;

  @Bean
  public DataSource readDataSource(){
    return DataSourceBuilder.create()
        .url(readUrl)
        .username(username)
        .password(password)
        .driverClassName("org.postgresql.Driver")
        .build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("readDataSource") DataSource dataSource){
    return builder.dataSource(dataSource)
                  .packages("com.example")
                  .persistenceUnit("read")
                  .build();
  }


}
