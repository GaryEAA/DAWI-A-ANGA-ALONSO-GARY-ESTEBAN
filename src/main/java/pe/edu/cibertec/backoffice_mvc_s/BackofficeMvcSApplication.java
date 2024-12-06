package pe.edu.cibertec.backoffice_mvc_s;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pe.edu.cibertec.backoffice_mvc_s.entity.Film;
import pe.edu.cibertec.backoffice_mvc_s.repository.FilmRepository;

@SpringBootApplication
public class BackofficeMvcSApplication implements CommandLineRunner {
	@Autowired
	FilmRepository filmRepository;
	public static void main(String[] args) {
		SpringApplication.run(BackofficeMvcSApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		/**
		 * findAll - Caching
		 */
		System.out.println("-----------------------------");
		System.out.println("findAll() - 1ra llamada MySQL");
		System.out.println("-----------------------------");
		Iterable<Film> iterable = filmRepository.findAll();
		iterable.forEach((film) -> {
			String message = String.format("%s:%s", film.getFilmId(), film.getTitle());
			System.out.println(message);
		});
		/**
		 * findAll - Caching
		 */
		System.out.println("-----------------------------");
		System.out.println("findAll() - 2da llamada Cache");
		System.out.println("-----------------------------");
		Iterable<Film> iterable2 = filmRepository.findAll();
		iterable2.forEach((film) -> {
			String message = String.format("%s:%s", film.getFilmId(), film.getTitle());
			System.out.println(message);
		});
	}
}
