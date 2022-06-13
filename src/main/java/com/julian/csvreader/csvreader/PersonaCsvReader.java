package com.julian.csvreader.csvreader;

import com.julian.csvreader.data.PersonaEntity;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que extiende de {@link AbstractCsvReader} para mapear {@link PersonaEntity}.
 */
public class PersonaCsvReader extends AbstractCsvReader<PersonaEntity> {

    private SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
    public PersonaCsvReader(String fileName, String delimiter) throws FileNotFoundException {
        super(fileName, delimiter);
    }

    /**
     * Mapea las filas del archivo csv con el que se inicializo la clase a una lista de {@link PersonaEntity}.
     * @return una lista de PersonaEntity.
     * @throws ParseException en caso de errores parseando los campos del csv a los atributos de la clase PersonaEntity.
     */
    @Override
    public List<PersonaEntity> mapInto() throws ParseException {

        final List<PersonaEntity> personas = new ArrayList<>();
        String[] row;

        do {
            row = this.getNext();
            if ( row != null){
                final PersonaEntity persona = new PersonaEntity();
                persona.setNombre(row[0]);
                persona.setApellidos(row[1]);
                persona.setFechaNacimiento(formatter.parse(row[2]));
                persona.setCiudad(row[3]);
                personas.add(persona);
            }
        } while (row != null);

        return personas;
    }
}
