package site.dungang.test;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages= {"site.dungang"})
public class AppTest {

	public static void main(String[] args) {
		SpringApplication.run(AppTest.class, args);
	}

}
