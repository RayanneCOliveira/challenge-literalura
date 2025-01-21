package br.com.alura.literalura;

import br.com.alura.literalura.model.LiterAluraApplication;
import br.com.alura.literalura.repository.AuthorRepository;
import br.com.alura.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {

	@Autowired
	private BookRepository repository;

	@Autowired
	private AuthorRepository authorsRepository;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LiterAluraApplication application = new LiterAluraApplication(repository, authorsRepository);
		application.showMenu();
	}
}
