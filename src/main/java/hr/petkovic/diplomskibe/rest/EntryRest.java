package hr.petkovic.diplomskibe.rest;

import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hr.petkovic.diplomskibe.entity.Entry;
import hr.petkovic.diplomskibe.service.EntryService;

@RestController
@RequestMapping("/api/entry")
public class EntryRest {
	Logger LOGGER = LoggerFactory.getLogger(EntryRest.class);
	EntryService entryServ;

	public EntryRest(EntryService e) {
		entryServ = e;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{username}")
	public List<Entry> getAllEntriesForUser(@PathVariable String username) {
		return entryServ.findEntriesForUser(username);
	}

	@PostMapping()
	public Entry saveEntry(@RequestBody Entry entry, HttpServletResponse response) {
		Entry e = entryServ.saveRest(entry);
		if (e.getId() == null) {
			response.setStatus(400);
			return null;
		}
		return e;
	}

	@PutMapping("/{id}")
	public void updateEntry(@PathVariable("id") Long id, @RequestBody Entry entry, HttpServletResponse response) {
		Entry e = entryServ.editEntry(id, entry);
		if (e.getId() == null) {
			response.setStatus(400);
		}
	}

	@DeleteMapping("{id}")
	public void deleteEntry(@PathVariable("id") Long id, HttpServletResponse response) {
		try {
			entryServ.deleteEntryById(id);
		} catch (NoSuchElementException e) {
			response.setStatus(400);
		}
	}
}
