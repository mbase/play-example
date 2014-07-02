package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Product;
import models.Tag;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.With;
import views.html.products.details;

import securesocial.core.java.SecureSocial;

import com.avaje.ebean.Page;

@With(CatchAction.class)
@Security.Authenticated(Secured.class)
public class Products extends Controller {
	
	private static final Form<Product> productForm = Form.form(Product.class);

	public static Result list(Integer page) {
		Page<Product> products = Product.find(page);
		return ok(views.html.catalog.render(products));
	}
	
	@SecureSocial.SecuredAction
	public static Result newProduct() {
		return ok(details.render(productForm));
	}
	
	public static Result details(Product product) {
//		final Product product = Product.findByEan(ean);
//		if (product == null) {
//			return notFound(String.format("Product %s does not exist.", ean));
//		}
		
		Form<Product> filledForm = productForm.fill(product);
		return ok(details.render(filledForm));
	}
	
	public static Result save() {
		MultipartFormData body = request().body().asMultipartFormData();
		Form<Product> boundForm = productForm.bindFromRequest();
		if (boundForm.hasErrors()) {
			flash("error", "Please correct the form bellow.");
			return badRequest(details.render(boundForm));
		}
	
		Product product = boundForm.get();
		MultipartFormData.FilePart part = body.getFile("picture");
		
		if (part != null) {
			File file = part.getFile();
			try {
				product.picture = com.google.common.io.Files.toByteArray(file);
			} catch (IOException e) {
				return internalServerError("Error reading file upload");
			}
		}
		
		List<Tag> tags = new ArrayList<Tag>();
		for (Tag tag : product.tags) {
			if (tag.id != null) {
				tags.add(Tag.findById(tag.id));
			}
		}
		product.tags = tags;
		if (product.id == null) {
			product.save();
		} else {
			product.update();
		}
		
		flash("success", String.format("Successfully added product %s", product));
		return redirect(routes.Products.list(1));
	}
	
	public static Result delete(String ean) {
		final Product product = Product.findByEan(ean);
		if (product == null) {
			return notFound(String.format("Product %s does not exist.", ean));
		}
		
		product.delete();
		return redirect(routes.Products.list(1));
	}
	
	public static Result index() {
		return redirect(routes.Products.list(0));
	}
	
	public static Result picture(String ean) {
		final Product product = Product.findByEan(ean);
		if(product == null) return notFound();
		return ok(product.picture);
	}

}
