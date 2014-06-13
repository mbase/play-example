package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class User extends Model {

	private static final long serialVersionUID = 1L;
	
	@Id
	public Long id;
	@Constraints.Required
	public String email;
	@Constraints.Required
	public String password;

	public static Finder<Long, User> finder = new Finder<Long, User>(
			Long.class, User.class);

	public User() {
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public static User authenticate(String email, String password) {
		
		// Should be something like
		// return finder.where().eq("email", email)
		// .eq("password", password).findUnique();
		if ("nicolas@google.com".equals(email) && "nicolas".equals(password))
			return new User("nicolas@google.com", "nicolas");
		else
			return null;
	}

}
