package com.sebok.springClientDemo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEntryRepository extends CrudRepository<LogEntry, Long> {

}
