package com.smartsensesolutions.dao.sample.repository;

import com.smartsensesolutions.dao.sample.entity.Books;
import com.smartsensesolutions.java.commons.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends BaseRepository<Books, Long> {
}
