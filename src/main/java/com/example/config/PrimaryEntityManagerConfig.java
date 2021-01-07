package com.example.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.example",
    excludeFilters = @ComponentScan.Filter(ReadOnlyRepository.class),
    entityManagerFactoryRef = "entityManagerFactory"
)
public class PrimaryEntityManagerConfig {

  @Value("${spring.datasource.username}")
  private String username;
  @Value("${spring.datasource.password}")
  private String password;
  @Value("${spring.datasource.url}")
  private String url;

  @Bean
  @Primary
  public DataSource dataSource(){
    return DataSourceBuilder.create()
        .url(url)
        .username(username)
        .password(password)
        .driverClassName("org.postgresql.Driver")
        .build();
  }

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("dataSource") DataSource dataSource){
    return builder.dataSource(dataSource)
                  .packages("com.example")
                  .persistenceUnit("main")
                  .build();
  }


}
