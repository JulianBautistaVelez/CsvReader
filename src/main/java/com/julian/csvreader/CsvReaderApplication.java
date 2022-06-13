package com.julian.csvreader;

import com.julian.csvreader.csvreader.PersonaCsvReader;
import com.julian.csvreader.data.PersonaEntity;
import com.julian.csvreader.data.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class CsvReaderApplication {

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		SpringApplication.run(CsvReaderApplication.class, args);
		ApplicationContext applicationContext = SpringApplication.run(CsvReaderApplication.class, args);

		//Se lee un archivo CSV
		final PersonaCsvReader reader = new PersonaCsvReader("data.csv", ";");
		final List<PersonaEntity> personas = reader.mapInto();
		reader.closeFile();
		System.out.println("Filas en archivo csv:");
		System.out.println(personas);

		//Se filtran los resultados
		final Date dateToFilter = new SimpleDateFormat("dd/mm/yyyy").parse("01/01/1980");
		final List<PersonaEntity> personasBornBefore = personas.stream().filter(personaEntity -> personaEntity.getFechaNacimiento().before(dateToFilter)).collect(Collectors.toList());
		System.out.println("Personas nacidas antes de 1980:");
		System.out.println(personasBornBefore);

		//Se guardan en base de datos
		PersonaRepository repository = applicationContext.getBean(PersonaRepository.class);
		repository.saveAll(personasBornBefore);


	}

}
