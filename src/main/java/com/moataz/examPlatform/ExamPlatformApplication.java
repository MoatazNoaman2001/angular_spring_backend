package com.moataz.examPlatform;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@EnableJpaAuditing
@SpringBootApplication
public class ExamPlatformApplication implements CommandLineRunner {
	private DataSource dataSource;
	public ExamPlatformApplication(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public static void main(String[] args) {
		SpringApplication.run(ExamPlatformApplication.class, args);
	}



	@Override
	public void run(String... args) throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// Query to get all tables in the current database
		String sql = "SELECT table_name FROM information_schema.tables " +
				"WHERE table_schema = 'public' AND table_type = 'BASE TABLE'";

		List<Map<String, Object>> tables = jdbcTemplate.queryForList(sql);

		System.out.println("Tables in the database:");
		tables.forEach(table -> System.out.println(table.get("table_name")));

		// For each table, you can query its structure or data
		tables.forEach(table -> {
			String tableName = (String) table.get("table_name");
			System.out.println("\nStructure of table: " + tableName);

			// Get columns information
			String columnsSql = "SELECT column_name, data_type FROM information_schema.columns " +
					"WHERE table_name = ?";
			List<Map<String, Object>> columns = jdbcTemplate.queryForList(columnsSql, tableName);
			columns.forEach(col -> System.out.println(
					"Column: " + col.get("column_name") +
							", Type: " + col.get("data_type")
			));

			// Get sample data (first 5 rows)
			String dataSql = "SELECT * FROM " + tableName + " LIMIT 5";
			try {
				List<Map<String, Object>> data = jdbcTemplate.queryForList(dataSql);
				System.out.println("Sample data:");
				data.forEach(System.out::println);
			} catch (Exception e) {
				System.out.println("Could not query data from table " + tableName);
			}
		});
	}
}
