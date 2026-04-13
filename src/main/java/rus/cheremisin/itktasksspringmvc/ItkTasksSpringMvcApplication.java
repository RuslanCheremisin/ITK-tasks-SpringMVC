package rus.cheremisin.itktasksspringmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class ItkTasksSpringMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItkTasksSpringMvcApplication.class, args);
		System.out.println("hi");
	}

}
