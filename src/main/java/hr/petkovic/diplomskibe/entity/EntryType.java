package hr.petkovic.diplomskibe.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "entry_types", uniqueConstraints = { @UniqueConstraint(columnNames = { "mainType", "subType" }) })
public class EntryType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String mainType;

	@Column(nullable = true)
	private String subType;

	@JsonIgnore
	@OneToMany(mappedBy = "type")
	private List<Entry> transactions = new ArrayList<Entry>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMainType() {
		return mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public List<Entry> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Entry> transactions) {
		this.transactions = transactions;
	}

}
