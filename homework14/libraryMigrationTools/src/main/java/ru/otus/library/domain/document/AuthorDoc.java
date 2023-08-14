package ru.otus.library.domain.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "authors")
public class AuthorDoc {
    @Id
    private String id;

    private String name;

    private String surname;

    private Long previousId;

    public AuthorDoc() {

    }

    public AuthorDoc(String name,
                  String surname) {
        this.name = name;
        this.surname = surname;
    }

    public AuthorDoc(String id,
                  String name,
                  String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public AuthorDoc(String name,
                     String surname,
                     Long previousId) {
        this.name = name;
        this.surname = surname;
        this.previousId = previousId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPreviousId(Long previousId) {
        this.previousId = previousId;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    public String getInfo() {
        return String.format("[id=%s][Name=%s][Surname=%s]", id, name, surname);
    }
}
