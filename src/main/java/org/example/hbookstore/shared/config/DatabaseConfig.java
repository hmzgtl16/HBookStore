package org.example.hbookstore.shared.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJdbcRepositories(basePackages = "org.example.hbookstore")
@EnableTransactionManagement
public class DatabaseConfig extends AbstractJdbcConfiguration {


}
