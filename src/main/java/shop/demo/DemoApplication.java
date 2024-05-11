package shop.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class DemoApplication implements CommandLineRunner {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Checking database connection...");
		try {
			// 데이터베이스에서 간단한 쿼리 실행하여 연결 확인
			jdbcTemplate.queryForObject("SELECT 1", Integer.class);
			System.out.println("Database connected successfully!");
		} catch (Exception e) {
			System.err.println("Failed to connect to database: " + e.getMessage());
		}
	}
}
