package code.tofu.useBatch;

import code.tofu.useBatch.migrate.FlywayMigrateApplication;
import code.tofu.useBatch.migrate.FlywayService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class UseBatchApplication {

	public static void main(String[] args) {
		String migrateFlag = System.getProperty("flywayMigrate");
		if (migrateFlag != null && migrateFlag.equalsIgnoreCase("true")) {
			log.info("Flyway Migrate Flag Active, Running Flyway migrate");
			ApplicationContext applicationContext = SpringApplication.run(FlywayMigrateApplication.class, args);
			FlywayService flywayService = applicationContext.getBean(FlywayService.class);
			flywayService.migrateFlywayWithRepair();
			System.exit(0);
		} else {
			SpringApplication.run(UseBatchApplication.class, args);
		}
	}

}
