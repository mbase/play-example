package models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.db.ebean.Model.Finder;

@Entity
public class Tag {
	
	public static Finder<Long, Tag> find = 
			new Finder<Long, Tag>(Long.class, Tag.class);
	
	@Id
	public Long id;
	
	public String name;
	
	@ManyToMany(mappedBy = "tags")
	public List<Product> products;
	
	public Tag() {
	}

	public Tag(Long id, String name, List<Product> products) {
		this.id = id;
		this.name = name;
		this.products = new LinkedList<Product>();
		
		for (Product product : products) {
			product.tags.add(this);
		}
	}
	
	public static Tag findById(Long id) {
		return find.byId(id);
	}
}
