package ru.otus.library.domain.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "genres")
public class GenreDoc {
    @Id
    private String id;

    private String name;

    private Long previousId;

    public GenreDoc() {

    }

    public GenreDoc(String name) {
        this.name = name;
    }

    public GenreDoc(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return String.format("[id=%s][Genre=%s]", id, name);
    }
}
