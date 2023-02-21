package app.config;

import app.dao.InMemoryTaskDao;
import app.dao.TaskDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class InMemoryDaoContextConfiguration {
    @Bean
    public TaskDao taskDao() {
        return new InMemoryTaskDao();
    }
}
