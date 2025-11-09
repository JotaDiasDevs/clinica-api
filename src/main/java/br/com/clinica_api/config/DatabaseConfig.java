package br.com.clinica_api.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        
        if (databaseUrl != null && !databaseUrl.isEmpty()) {
            // Converte postgresql:// para jdbc:postgresql://
            String jdbcUrl = databaseUrl.replace("postgresql://", "jdbc:postgresql://");
            dataSource.setJdbcUrl(jdbcUrl);
        } else {
            // Fallback para desenvolvimento local
            dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/clinica");
            dataSource.setUsername("postgres");
            dataSource.setPassword("");
        }
        
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }
}