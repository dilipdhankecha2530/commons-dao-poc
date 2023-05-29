# Commons-Dao Sample

This is a sample repository that offers a simplified implementation of
the [smartsensesolutions-commons-dao](https://github.com/smartSenseSolutions/smartsense-java-commons) repository.
It contains implementation for Books and Authors entities to showcase how
the [smartsensesolutions-commons-dao](https://github.com/smartSenseSolutions/smartsense-java-commons) can facilitate the
search functionality.
With the `SpecificationUtils` provided in the library, implementing pagination in a query and performing various types of 
queries becomes easy.

Here, we use Gradle as the build tool and add below dependency to our build.gradle file.

```
implementation group: 'com.smartsensesolutions', name: 'commons-dao', version: '0.0.2'
```

The `BaseEntity`, `BaseRepository` and `BaseService` classes from the above package must be extended to `Entity`, 
`Repository` and `Service` classes, respectively .

## Project Description

This project contains a few endpoints that provide a mechanism to save new objects and query them using 
the common-dao package.

1. Add new data:

```
curl --location '::8080/create/books' \
--header 'Content-Type: application/json' \
--data '[
    {
        "authorName": "AuthorName",
        "age": 25,
        "books": [
            {
                "bookName": "Spring Automation 2022",
                "description": "Basic Automation setup from Zero to Hero with SpringBoot 2022."
            }
        ]
    }
]'
```

2. Filter the data with different criteria where `column` indicates the variable name from your entity class and the `operator`
   value comes from 
    [here](https://github.com/smartSenseSolutions/smartsense-java-commons/blob/master/commons-dao/src/main/java/com/smartsensesolutions/java/commons/operator/Operator.java).
   The `values` parameter contains a list of search parameter in String format.
   To search, use below POST endpoint with the mentioned request bodies based on the different scenarios:
    ```bash
    ::8080/search
    ```

    1. Get Paginated response:
         ```json
         {
           "page": 0,
           "size": 10
         }
         ```

    2. Search author based on the author name (equals query):
         ```json
         {
           "page": 0,
           "size": 10,
           "criteria": [
             {
               "column": "authorName",
               "operator": "=",
               "values": [
                 "Ziemer Miller"
               ]
             }
           ]
         }
         ```
       Prepared Query by SpecificationUtils:
       ```
       select a1_0.id,a1_0.age,a1_0.author_name,a1_0.created_at 
       from public.author a1_0 
       where a1_0.author_name=? offset ? rows fetch first ? rows only
       ```

    3. Search author based on the author name which contains author name (like query):
         ```json
         {
           "page": 0,
           "size": 10,
           "criteria": [
             {
               "column": "authorName",
               "operator": "like",
               "values": [
                 "Miller"
               ]
             }
           ]
         }
         ```
       Prepared Query by SpecificationUtils:
       ```
       select a1_0.id,a1_0.age,a1_0.author_name,a1_0.created_at 
       from public.author a1_0 
       where (cast(a1_0.author_name as text) like ? escape '') offset ? rows fetch first ? rows only
       ```

    4. Search author based on age:
         ```json
         {
           "page": 0,
           "size": 10,
           "criteria": [
             {
               "column": "age",
               "operator": "=",
               "values": [
                 "25"
               ]
             }
           ]
         }
         ``` 
       Prepared Query by SpecificationUtils:
       ```
       select a1_0.id,a1_0.age,a1_0.author_name,a1_0.created_at 
       from public.author a1_0 
       where a1_0.age=? offset ? rows fetch first ? rows only
       ```

    5. Search based on the book's name and author's name (like with join-equals):
        ```json
        {
          "page": 0,
          "size": 10,
          "criteria": [
            {
              "column": "authorName",
              "operator": "like",
              "values": [
                "Thomson"
              ]
            },
            {
              "column": "books_bookName",
              "operator": "join_eq",
              "values": [
                "Microservice with spring boot"
              ]
            }
          ]
        }
        ```
       Prepared Query by SpecificationUtils:
       ```
       select a1_0.id,a1_0.age,a1_0.author_name,a1_0.created_at 
       from public.author a1_0 
       join (public.author_books_mapping b1_0 join public.books b1_1 
            on b1_1.id=b1_0.book_id) on a1_0.id=b1_0.author_id 
       where (cast(a1_0.author_name as text) like ? escape '') and b1_1.book_name=? offset ? rows fetch first ? rows only
       ```

    6. Search based on the boolean valued from the table:
       ```json
        {
          "page": 0,
          "size": 10,
          "criteria": [
            {
              "column": "active",
              "operator": "is_true"
            }
          ]
        }
        ```
       Prepared Query by SpecificationUtils:
       ```
       select a1_0.id,a1_0.active,a1_0.age,a1_0.author_name,a1_0.created_at 
       from public.author a1_0 
       where a1_0.active offset ? rows fetch first ? rows only
       ```
    7. Search Books based on the BookName with active author with `OR` criteriaOperator. (Default criteriaOperator is
       AND)
       ```json
        {
          "page": 0,
          "size": 10,
          "criteriaOperator": "OR",
          "criteria": [
            {
              "column": "active",
              "operator": "is_true"
            },
            {
              "column": "books_bookName",
              "operator": "join_like",
              "values": [
                "Microservice"
              ]
            }
          ]
        }
        ```
       Prepared Query by SpecificationUtils:
       ```
       select a1_0.id,a1_0.active,a1_0.age,a1_0.author_name,a1_0.created_at 
       from public.author a1_0 
       left join (public.author_books_mapping b1_0 join public.books b1_1 on b1_1.id=b1_0.book_id) 
       on a1_0.id=b1_0.author_id 
       where a1_0.active or cast(b1_1.book_name as text) like ? escape '' offset ? rows fetch first ? rows only
       ```