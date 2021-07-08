package hr.petkovic.diplomskibe.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hr.petkovic.diplomskibe.entity.EntryType;
import hr.petkovic.diplomskibe.service.EntryTypeService;

@RestController
@RequestMapping("/api/types")
public class EntryTypeRest {
	Logger LOGGER = LoggerFactory.getLogger(EntryTypeRest.class);
	EntryTypeService typeServ;
	
	public EntryTypeRest(EntryTypeService t) {
		typeServ = t;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/income")
	public List<EntryType> getIncomeTypes(){
		return typeServ.getAllIncomeTypes();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/expense")
	public List<EntryType> getExpenseTypes(){
		return typeServ.getAllExpenseTypes();
	}
}
