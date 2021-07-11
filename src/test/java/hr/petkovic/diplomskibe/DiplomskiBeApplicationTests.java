package hr.petkovic.diplomskibe;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hr.petkovic.diplomskibe.entity.EntryType;
import hr.petkovic.diplomskibe.entity.User;
import hr.petkovic.diplomskibe.repository.EntryTypeRepo;
import hr.petkovic.diplomskibe.repository.UserRepo;
import hr.petkovic.diplomskibe.utility.Dictionary;

@SpringBootTest
@ActiveProfiles("test")
class DiplomskiBeApplicationTests {

	@Autowired
	private UserRepo uRepo;
	@Autowired
	private EntryTypeRepo tRepo;

	@BeforeAll
	public void initDB() {
		if (!uRepo.findByUsername("test").isPresent()) {
			uRepo.save(generateUser());
		}
		if (tRepo.findAllByMainType(Dictionary.INCOME_TYPE).size() == 0) {
			for (EntryType t : generateIncomeTypes()) {
				tRepo.save(t);
			}
		}
		if (tRepo.findAllByMainType(Dictionary.EXPENSE_TYPE).size() == 0) {
			for (EntryType t : generateExpenseTypes()) {
				tRepo.save(t);
			}
		}
	}

	private User generateUser() {
		User u = new User();
		u.setEnabled(true);
		u.setPassword("test");
		u.setUsername("test");
		u.setTransactions(new ArrayList<>());
		return u;
	}

	private List<EntryType> generateIncomeTypes() {
		List<EntryType> list = new  ArrayList<>();
		list.add(new EntryType(Dictionary.INCOME_TYPE, "Pay", new ArrayList<>()));
		list.add(new EntryType(Dictionary.INCOME_TYPE, "Side Job", new ArrayList<>()));
		return list;
	}

	private List<EntryType> generateExpenseTypes() {
		List<EntryType> list = new  ArrayList<>();
		list.add(new EntryType(Dictionary.EXPENSE_TYPE, "Life", new ArrayList<>()));
		list.add(new EntryType(Dictionary.EXPENSE_TYPE, "Family", new ArrayList<>()));
		return list;
	}
}
