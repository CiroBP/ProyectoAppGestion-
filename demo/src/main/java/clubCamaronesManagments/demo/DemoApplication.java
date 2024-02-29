package clubCamaronesManagments.demo;

import clubCamaronesManagments.demo.Models.Gasto;
import clubCamaronesManagments.demo.Models.Proveedor;
import clubCamaronesManagments.demo.Repositorys.SocioRepository;
import clubCamaronesManagments.demo.Services.SocioService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}

}



