package hr.petkovic.diplomskibe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.diplomskibe.entity.EntryType;

public interface EntryTypeRepo extends JpaRepository<EntryType, Long> {

	public List<EntryType> findAllByMainType(String mainType);
}
