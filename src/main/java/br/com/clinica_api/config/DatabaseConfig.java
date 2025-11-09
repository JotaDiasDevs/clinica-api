package br.com.clinica_api.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DatabaseConfig {

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        
        if (databaseUrl != null && !databaseUrl.isEmpty()) {
            try {
                // Parse da URL para extrair componentes
                URI dbUri = new URI(databaseUrl.replace("postgresql://", "http://"));
                
                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String host = dbUri.getHost();
                int port = dbUri.getPort();
                String path = dbUri.getPath();
                String dbName = path.startsWith("/") ? path.substring(1) : path;
                
                // Monta a URL JDBC correta
                String jdbcUrl = String.format(
                    "jdbc:postgresql://%s:%d/%s",
                    host, port, dbName
                );
                
                dataSource.setJdbcUrl(jdbcUrl);
                dataSource.setUsername(username);
                dataSource.setPassword(password);
                
                System.out.println("✅ Database configurado: " + host + ":" + port + "/" + dbName);
                
            } catch (Exception e) {
                System.err.println("❌ Erro ao parsear DATABASE_URL: " + e.getMessage());
                e.printStackTrace();
                // Fallback
                dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/clinica");
                dataSource.setUsername("postgres");
                dataSource.setPassword("");
            }
        } else {
            // Fallback para desenvolvimento local
            dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/clinica");
            dataSource.setUsername("postgres");
            dataSource.setPassword("");
        }
        
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(2);
        dataSource.setConnectionTimeout(30000);
        
        return dataSource;
    }
}