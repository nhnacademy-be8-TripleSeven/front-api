package com.tripleseven.frontapi.domain;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Book {

     long id;

     String title;


     String publisher;

     int regularPrice;


     int salePrice;

     String coverUrl;

    private List<String> creator;
}
