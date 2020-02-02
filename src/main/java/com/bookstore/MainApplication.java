package com.bookstore;

import com.bookstore.ds1.AuthorService;
import com.bookstore.ds2.BookService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {

    private final AuthorService authorService;
    private final BookService bookService;

    public MainApplication(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {

            System.out.println("\n Saving an author (check the MySQL database) ...");
            authorService.persistAuthor();

            System.out.println("\n Saving a book (check the PostgreSQL database) ...");
            bookService.persistBook();
        };
    }
}
/*
 * 
 * How To Auto-Create And Migrate Schemas For Two Data Sources (MySQL and PostgreSQL) Using Flyway

Note: For production don't rely on hibernate.ddl-auto to create your schema. Remove (disable) hibernate.ddl-auto or set it to validate. Rely on Flyway or Liquibase.

Description: This application is an example of auto-creating and migrating schemas for MySQL and PostgreSQL. In addition, each data source uses its own HikariCP connection pool. In case of MySQL, where schema=database, we auto-create the schema (authorsdb) based on createDatabaseIfNotExist=true. In case of PostgreSQL, where a database can have multiple schemas, we use the default postgres database and auto-create in it the schema, booksdb. For this we rely on Flyway, which is capable to create a missing schema.

Key points:

for Maven, in pom.xml, add the Flyway dependency
remove (disable) spring.jpa.hibernate.ddl-auto or set it to validate
in application.properties, configure the JDBC URL for MySQL as, jdbc:mysql://localhost:3306/authorsdb?createDatabaseIfNotExist=true and for PostgreSQL as, jdbc:postgresql://localhost:5432/postgres?currentSchema=booksdb
in application.properties, set spring.flyway.enabled=false to disable default behavior
programmatically create a DataSource for MySQL and one for PostgreSQL
programmatically create a FlywayDataSource for MySQL and one for PostgreSQL
programmatically create an EntityManagerFactory for MySQL and one for PostgreSQL
for MySQL, place the migration SQLs files in db\migration\mysql
for PostgreSQL, place the migration SQLs files in db\migration\postgresql
 */
