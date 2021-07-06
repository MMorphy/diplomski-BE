package hr.petkovic.diplomskibe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.petkovic.diplomskibe.entity.Entry;
import hr.petkovic.diplomskibe.service.EntryService;
import hr.petkovic.diplomskibe.service.EntryTypeService;
import hr.petkovic.diplomskibe.utility.Dictionary;

@Controller
@RequestMapping("/entry")
public class EntryController {

	Logger LOGGER = LoggerFactory.getLogger(EntryController.class);

	private EntryService entryServ;
	private EntryTypeService typeServ;

	public EntryController(EntryService e, EntryTypeService t) {
		entryServ = e;
		typeServ = t;
	}

	@GetMapping("/income/add")
	public String getIncomeAdding(Model model) {
		model.addAttribute(Dictionary.ADD_MODEL_OBJECT, entryServ.getEmptyEntry());
		model.addAttribute(Dictionary.ENTRIES_TYPE_LIST, typeServ.getAllIncomeTypes());
		return Dictionary.ENRTY_INCOME_ADD;
	}

	@GetMapping("/expense/add")
	public String getExpenseAdding(Model model) {
		model.addAttribute(Dictionary.ADD_MODEL_OBJECT, entryServ.getEmptyEntry());
		model.addAttribute(Dictionary.ENTRIES_TYPE_LIST, typeServ.getAllExpenseTypes());
		return Dictionary.ENRTY_EXPENSE_ADD;
	}

	@PostMapping({ "/income/add", "/expense/add" })
	public String addIncome(Entry addEntry) {
		entryServ.save(addEntry);
		return Dictionary.REDIRECT_HOME;
	}

	@GetMapping()
	public String getUserEntries(Model model) {
		model.addAttribute(Dictionary.ENTRIES_LIST, entryServ.getAllEntriesForCurrentUser());
		return Dictionary.ENRTY_ENTRIES_LIST;
	}

	@PostMapping("/delete/{id}")
	public String deleteEntry(@PathVariable("id") Long id) {
		entryServ.deleteEntryById(id);
		return Dictionary.REDIRECT_ENTRIES_LIST;
	}

	@GetMapping("/edit/{id}")
	public String getEntryEditing(@PathVariable("id") Long id, Model model) {
		model = entryServ.fillModelForEdit(id, model);
		return entryServ.checkEditEndpoint(model);
	}

	@PostMapping("/edit/{id}")
	public String editEntry(@PathVariable("id") Long id, Entry editEntry) {
		entryServ.editEntry(id, editEntry);
		return Dictionary.REDIRECT_ENTRIES_LIST;
	}
}
