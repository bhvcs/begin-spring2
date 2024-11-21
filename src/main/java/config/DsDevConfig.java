package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;



@Configuration
@Profile("dev")
public class DsDevConfig {
    @Bean(destroyMethod = "close")
    public DataSource datasource(){
        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost/springUserfs?characterEncoding=utf-8");
        ds.setUsername("springUser");
        ds.setPassword("spring");
        ds.setInitialSize(2);
        ds.setMaxActive(10);
        ds.setMaxIdle(10);
        ds.setTestWhileIdle(true);
        ds.setMinEvictableIdleTimeMillis(60000 * 3);//eviction: 축출
        ds.setTimeBetweenEvictionRunsMillis(10 * 1000);
        return ds;
    }
}
