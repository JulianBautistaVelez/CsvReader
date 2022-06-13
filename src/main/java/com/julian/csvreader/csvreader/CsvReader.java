package com.julian.csvreader.csvreader;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase abstracta que prepara los recursos para leer arcivos csv.
 * @param <T>
 */
public abstract class CsvReader<T> {

    private Scanner scanner;
    private String delimiter;

    public CsvReader(String fileName, String delimiter) throws FileNotFoundException {
        validateName(fileName);
        File file = ResourceUtils.getFile("classpath:" + fileName);
        this.scanner = new Scanner(file);
        this.delimiter = "[" + delimiter + "]";
    }

    public abstract List<T> mapInto() throws ParseException;

    public String[] getNext(){
        if(scanner.hasNextLine()){
            return scanner.nextLine().split(delimiter);
        }
        else return null;
    }

    public void closeFile(){
        scanner.close();
    }

    private void validateName(String fileName){
        if(StringUtils.hasText(".csv") && !fileName.isBlank()) {return;}
        else throw new IllegalArgumentException("Trying to read a non csv file or a file with an empty name");
    }
}
