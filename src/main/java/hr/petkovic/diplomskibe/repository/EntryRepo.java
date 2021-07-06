package hr.petkovic.diplomskibe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.diplomskibe.entity.Entry;

public interface EntryRepo extends JpaRepository<Entry, Long> {

	public List<Entry> findAllByCreatedBy_username(String username);
}
