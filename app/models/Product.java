package models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.PathBindable;
import utils.EanValidator;

import com.avaje.ebean.Page;

@Entity
public class Product extends Model implements PathBindable<Product> {

	private static final long serialVersionUID = 1L;

	public static Finder<Long, Product> find = 
			new Finder<Long, Product>(Long.class, Product.class);
	
	@Id
	public Long id;
	
	@Constraints.Required
	@Constraints.ValidateWith(EanValidator.class)
	public String ean;
	
	@Constraints.Required
	public String name;
	
	public String description;
	
	@ManyToMany
	public List<Tag> tags = new LinkedList<Tag>();
	
	public byte[] picture;
	
	@OneToMany(mappedBy = "product")
	public List<StockItem> stockItems;
	
	public Product() {
	}
	
	public Product(String ean, String name, String description) {
		this.ean = ean;
		this.name = name;
		this.description = description;
	}
	
	public static Product findById(Long id) {
		return find.byId(id);
	}
	
	public static Page<Product> find(int page) {
		return
				find.where()
					.orderBy("id asc")
					.findPagingList(10)
					.setFetchAhead(false)
					.getPage(page);
	}
	
	public static Product findByEan(String ean) {
		return find.where().eq("ean", ean).findUnique();
	}
	
	public static List<Product> findByName(String name) {
		return find.where().eq("name", name).findList();
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", ean, name);
	}
	
	@Override
	public Product bind(String key, String value) {
		Product product = findByEan(value); 
		return product = (product != null) ? product : (new Product());
	}
	
	@Override
	public String unbind(String key) {
		return this.ean;
	}
	
	@Override
	public String javascriptUnbind() {
		return this.ean;
	}

}
