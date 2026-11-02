package ar.edu.um.catalog;

import ar.edu.um.catalog.adapters.out.persistence.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.time.LocalTime;

@SpringBootApplication
public class CatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(ProfessionalCategoryRepository categoryRepo,
							   ProfessionalRepository profRepo,
							   WeeklyScheduleRepository scheduleRepo) {
		return args -> {
			if (categoryRepo.count() == 0) {
				categoryRepo.save(ProfessionalCategoryEntity.builder()
						.id(1L)
						.name("Cardiología")
						.description("Especialistas en salud cardiovascular")
						.enabled(true)
						.createdAt(Instant.now())
						.updatedAt(Instant.now())
						.build());

				profRepo.save(ProfessionalEntity.builder()
						.id(1L)
						.categoryId(1L)
						.firstName("Juan")
						.lastName("Pérez")
						.enabled(true)
						.createdAt(Instant.now())
						.updatedAt(Instant.now())
						.build());

				scheduleRepo.save(WeeklyScheduleEntity.builder()
						.id(1L)
						.professionalId(1L)
						.dayOfWeek("MONDAY")
						.startTime(LocalTime.of(8, 0))
						.endTime(LocalTime.of(12, 0))
						.slotDurationMinutes(30)
						.enabled(true)
						.createdAt(Instant.now())
						.updatedAt(Instant.now())
						.build());

				System.out.println(">>> ¡Datos de prueba insertados exitosamente!");
			}
		};
	}
}