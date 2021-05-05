package br.com.texoit.pablohenrique.util;

import br.com.texoit.pablohenrique.exception.ImportarCSVException;
import br.com.texoit.pablohenrique.exception.PabloHenriqueException;
import br.com.texoit.pablohenrique.model.Movie;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsvUtil {

    public static List<Movie> parseCsvFile(InputStream is) throws PabloHenriqueException {
        List<Movie> movies = new ArrayList<>();
        BufferedReader fileReader = null;
        CSVParser csvParser = null;
        SimpleDateFormat formaterData = new SimpleDateFormat("yyyy");

        try {
            fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Movie movie = new Movie();

                String year = csvRecord.get("year");
                if (year != null) {
                    Date data = null;
                    try {
                        data = formaterData.parse(year);
                    } catch (ParseException e) {
                        System.out.println("Error no Layout do CSV, campo year formato de ano invalido, linha : "
                                + csvRecord.getRecordNumber());
                        throw new ImportarCSVException("Error no Layout do CSV, campo year formato de " +
                                "ano invalido, linha : "
                                + csvRecord.getRecordNumber());
                    }
                    movie.setYear(Integer.valueOf(year));
                } else {
                    System.out.println("Error no Layout do CSV, campo year vazio,  linha : "
                            + csvRecord.getRecordNumber());
                    throw new ImportarCSVException("Error no Layout do CSV, campo year vazio,  linha : "
                            + csvRecord.getRecordNumber());
                }

                String title = csvRecord.get("title");
                if (title != null) {
                    movie.setTitle(title.replace("'", "''"));
                } else {
                    System.out.println("Error no Layout do CSV, campo title vazio,  linha : "
                            + csvRecord.getRecordNumber());
                    throw new ImportarCSVException("Error no Layout do CSV, campo title vazio,  linha : "
                            + csvRecord.getRecordNumber());
                }

                String studios = csvRecord.get("studios");
                if (studios != null) {
                    movie.setStudios(studios.replace("'", "''"));
                } else {
                    System.out.println("Error no Layout do CSV, campo studios vazio,  linha : "
                            + csvRecord.getRecordNumber());
                    throw new ImportarCSVException("Error no Layout do CSV, campo studios vazio,  linha : "
                            + csvRecord.getRecordNumber());
                }

                String producers = csvRecord.get("producers");
                if (producers != null) {
                    movie.setProducers(producers.replace("'", "''"));
                } else {
                    System.out.println("Error no Layout do CSV, campo producers vazio,  linha : "
                            + csvRecord.getRecordNumber());
                    throw new ImportarCSVException("Error no Layout do CSV, campo producers vazio,  linha : "
                            + csvRecord.getRecordNumber());
                }

                String winner = csvRecord.get("winner");
                if (winner != null) {
                    if (winner.equals("yes")) {
                        movie.setWinner(true);
                    } else {
                        movie.setWinner(false);
                    }
                } else {
                    movie.setWinner(false);
                }

                movies.add(movie);
            }

            return movies;
        } catch (Exception e) {
            System.out.println("Error leitura CSV!");
            throw new ImportarCSVException("Error leitura CSV!");
        } finally {
            try {
                fileReader.close();
                csvParser.close();
            } catch (IOException e) {
                System.out.println("Error Closer CSV!");
                throw new ImportarCSVException("Error Closer CSV!");
            }
        }
    }
}
