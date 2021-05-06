package br.com.texoit.pablohenrique.config;

import br.com.texoit.pablohenrique.bean.MaxBean;
import br.com.texoit.pablohenrique.bean.MinBean;
import br.com.texoit.pablohenrique.exception.PabloHenriqueException;
import br.com.texoit.pablohenrique.model.Movie;
import br.com.texoit.pablohenrique.model.Producer;
import br.com.texoit.pablohenrique.repository.jdbc.dao.MovieDAO;
import br.com.texoit.pablohenrique.repository.jdbc.dao.ProducerDAO;
import br.com.texoit.pablohenrique.service.ProducerService;
import br.com.texoit.pablohenrique.util.CsvUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {ScanConfig.PACKAGE})
public class Config {

    @Value("classpath:movielist.csv")
    Resource resourceFile;
    @Autowired
    private DataSource dataSource;
    @Autowired
    MovieDAO movieDAO;
    @Autowired
    ProducerService producerService;

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.HSQL).addScript("classpath:table.sql").build();

        return db;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        return jdbcTemplate;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    void initBD() throws Exception {
        List<Movie> movies = CsvUtil.parseCsvFile(resourceFile.getInputStream());
        movieDAO.inserir(movies);
    }
}
