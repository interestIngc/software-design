package app.config;

import app.dao.JdbcTaskDao;
import app.dao.TaskDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JdbcTaskDaoContextConfiguration {
    @Bean
    public TaskDao taskDao(DataSource dataSource) {
        return new JdbcTaskDao(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:tasks.db");
        dataSource.setUsername("");
        dataSource.setPassword("");
        return dataSource;
    }
}
