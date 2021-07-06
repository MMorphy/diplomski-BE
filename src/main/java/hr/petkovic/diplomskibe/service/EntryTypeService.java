package hr.petkovic.diplomskibe.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import hr.petkovic.diplomskibe.entity.EntryType;
import hr.petkovic.diplomskibe.repository.EntryTypeRepo;
import hr.petkovic.diplomskibe.utility.Dictionary;

@Service
public class EntryTypeService {

	Logger LOGGER = LoggerFactory.getLogger(EntryTypeService.class);

	private EntryTypeRepo typeRepo;

	public EntryTypeService(EntryTypeRepo e) {
		typeRepo = e;
	}

	public List<EntryType> getAllIncomeTypes() {
		return typeRepo.findAllByMainType(Dictionary.INCOME_TYPE);
	}

	public List<EntryType> getAllExpenseTypes() {
		return typeRepo.findAllByMainType(Dictionary.EXPENSE_TYPE);
	}
}
