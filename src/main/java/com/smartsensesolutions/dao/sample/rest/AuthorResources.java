package com.smartsensesolutions.dao.sample.rest;

import com.smartsensesolutions.dao.sample.entity.Author;
import com.smartsensesolutions.dao.sample.model.request.AuthorRequest;
import com.smartsensesolutions.dao.sample.service.AuthorService;
import com.smartsensesolutions.java.commons.FilterRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AuthorResources {

    private final AuthorService authorService;

    @PostMapping("/create/books")
    public List<Author> createBooks(@RequestBody List<AuthorRequest> requests) {
        return authorService.saveAuthorDetails(requests);
    }

    @PostMapping("/search")
    public Page<Author> searchBooks(@RequestBody FilterRequest request) {
        return authorService.searchBasedOnPagination(request);
    }
}
