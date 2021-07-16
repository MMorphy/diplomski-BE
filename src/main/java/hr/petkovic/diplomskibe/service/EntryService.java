package hr.petkovic.diplomskibe.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import hr.petkovic.diplomskibe.entity.Entry;
import hr.petkovic.diplomskibe.repository.EntryRepo;
import hr.petkovic.diplomskibe.utility.Dictionary;

@Service
public class EntryService {
	Logger LOGGER = LoggerFactory.getLogger(EntryService.class);

	private EntryRepo entryRepo;
	private UserService userServ;
	private EntryTypeService typeServ;

	public EntryService(EntryRepo e, UserService u, EntryTypeService t) {
		entryRepo = e;
		userServ = u;
		typeServ = t;
	}

	public Entry save(Entry addEntry) {
		try {
			addEntry.setCreatedBy(
					userServ.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
			return entryRepo.save(addEntry);
		} catch (NoSuchElementException ex) {
			LOGGER.error("Adding entry for user that doesn't exist: "
					+ SecurityContextHolder.getContext().getAuthentication().getName());
			return null;
		}
	}

	public List<Entry> getAllEntriesForCurrentUser() {
		return entryRepo.findAllByCreatedBy_username(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	public void deleteEntryById(Long id) {
		try {
			entryRepo.delete(findEntryById(id));
		} catch (NoSuchElementException ex) {
			LOGGER.error("Trying to delete an entry that doesn't exist. Id: " + id);
			throw new NoSuchElementException();
		}
	}

	public Entry findEntryById(Long id) throws NoSuchElementException {
		return entryRepo.findById(id).get();
	}

	public Entry getEmptyEntry() {
		return new Entry();
	}

	public Model fillModelForEdit(Long id, Model model) {
		try {
			model.addAttribute(Dictionary.EDIT_MODEL_OBJECT, findEntryById(id));
			model = fillEntryTypesForEdit(model);
			return model;
		} catch (NoSuchElementException e) {
			LOGGER.error("Trying to edit an entry that doesn't exist. Id: " + id);
			return null;
		}
	}

	public String checkEditEndpoint(Model model) {
		if (model == null)
			return Dictionary.REDIRECT_HOME;
		else if (isEntryIncome((Entry) model.getAttribute("editEntry")))
			return Dictionary.ENTRY_INCOME_EDIT;
		else
			return Dictionary.ENTRY_EXPENSE_EDIT;

	}

	public Entry editEntry(Long id, Entry editEntry) {
		Entry dbEntry = null;
		try {
			dbEntry = findEntryById(id);
			dbEntry = deepCopy(dbEntry, editEntry);
		} catch (Exception e) {
			LOGGER.error("Trying to edit an entry that doesn't exist. Id: " + id);
			return null;
		}
		return entryRepo.save(dbEntry);
	}

	public List<Entry> findEntriesForUser(String username) {
		return entryRepo.findAllByCreatedBy_username(username);
	}

	public Entry saveRest(Entry entry) {
		Entry e = getEmptyEntry();
		try {
			e.setCreatedBy(userServ.findUserById(entry.getCreatedBy().getId()));
			e.setAmount(entry.getAmount());
			e.setDescription(entry.getDescription());
			e.setType(entry.getType());
			return entryRepo.save(e);
		} catch (NoSuchElementException ex) {
			LOGGER.error("Adding entry for user that doesn't exist. Id: " + entry.getCreatedBy().getId());
			return e;
		}
	}

	private Entry deepCopy(Entry db, Entry model) {
		db.setAmount(model.getAmount());
		db.setDescription(model.getDescription());
		db.setType(model.getType());
		return db;
	}

	private boolean isEntryIncome(Entry e) {
		if (e.getType().getMainType().equals(Dictionary.INCOME_TYPE))
			return true;
		else
			return false;
	}

	private Model fillEntryTypesForEdit(Model model) {
		if (isEntryIncome(((Entry) model.getAttribute(Dictionary.EDIT_MODEL_OBJECT)))) {
			model.addAttribute(Dictionary.ENTRIES_TYPE_LIST, typeServ.getAllIncomeTypes());
		} else {
			model.addAttribute(Dictionary.ENTRIES_TYPE_LIST, typeServ.getAllExpenseTypes());
		}
		return model;
	}
}
