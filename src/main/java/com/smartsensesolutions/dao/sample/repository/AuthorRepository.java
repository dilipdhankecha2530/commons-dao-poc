package com.smartsensesolutions.dao.sample.repository;

import com.smartsensesolutions.dao.sample.entity.Author;
import com.smartsensesolutions.java.commons.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends BaseRepository<Author, Long> {
}
