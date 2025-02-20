package net.rewerk.servlets.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Book {
    private UUID id;
    private String title;
    private String author;
    private int year;
    private int pages;
}
