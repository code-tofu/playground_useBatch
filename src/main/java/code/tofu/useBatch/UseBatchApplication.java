package code.tofu.useBatch;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class UseBatchApplication {

	public static void main(String[] args) {
		log.info(Arrays.toString(args));
		SpringApplication.run(UseBatchApplication.class, args);
	}

}
