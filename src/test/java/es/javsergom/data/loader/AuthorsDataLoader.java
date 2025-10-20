package es.javsergom.data.loader;

import es.javierserrano.domain.data.mapper.AuthorMapper;
import es.javierserrano.domain.model.Author;
import es.javierserrano.domain.repository.entity.AuthorEntity;
import es.javierserrano.domain.service.dto.AuthorDto;
import org.apache.commons.csv.CSVRecord;

import java.util.List;

public class AuthorsDataLoader extends ResourceDataLoader {

    private final List<CSVRecord> authorRawRecords;

    public AuthorsDataLoader() {
        super("authors.csv");
        this.authorRawRecords = loadDataFromCsv();
    }

    public List<AuthorDto> loadAuthorDtosFromCsv() {
        return authorRawRecords.stream()
                .map(AuthorMapper::toAuthorDto)
                .toList();
    }

    public List<Author> loadAuthorsFromCSV() {
        return authorRawRecords
                .stream()
                .map(AuthorMapper::toAuthor)
                .toList();
    }

    public List<AuthorEntity> loadAuthorEntitiesFromCSV() {
        return authorRawRecords
                .stream()
                .map(AuthorMapper::toAuthorEntity)
                .toList();
    }
}
