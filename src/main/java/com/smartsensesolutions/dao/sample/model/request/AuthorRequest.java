package com.smartsensesolutions.dao.sample.model.request;

import java.util.List;

public record AuthorRequest(String authorName, Long age, List<BookRequest> books) {
}
