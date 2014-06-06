package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class StockItem extends Model {

	private static final long serialVersionUID = 1L;

	public static Finder<Long, StockItem> find = new Finder<Long, StockItem>(
			Long.class, StockItem.class);

	@Id
	public Long id;

	@ManyToOne
	public Warehouse warehouse;

	@ManyToOne
	public Product product;

	public Long quantity;

	public static StockItem findById(Long id) {
		return find.byId(id);
	}

	@Override
	public String toString() {
		return String.format("StockItem %d - %dx product %s", id, quantity,
				product == null ? null : product.id);
	}

}
