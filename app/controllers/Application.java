package controllers;

import models.ExpeditedOrders;
import models.User;
import play.data.Form;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import static play.data.Form.form;
import views.html.*;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render("live stream"));
	}

	public static WebSocket<String> liveUpdate() {
		return new WebSocket<String>() {
			@Override
			public void onReady(final In<String> in, final Out<String> out) {

				in.onMessage(new Callback<String>() {
					@Override
					public void invoke(String a) throws Throwable {
						ExpeditedOrders.notifyOthers(out, a + " is being processed");
					}
				});
				
				in.onClose(new Callback0() {
					@Override
					public void invoke() throws Throwable {
						ExpeditedOrders.unregister(out);
						System.out.println("Disconnect");
					}
				});
				
				ExpeditedOrders.register(out);
			}
		};
	}

	public static WebSocket<String> hello() {
		System.out.println("hello");

		return new WebSocket<String>() {

			@Override
			public void onReady(In<String> in, Out<String> out) {

				in.onMessage(new Callback<String>() {

					@Override
					public void invoke(String a) throws Throwable {

						System.out.println(a);
					}
				});

				in.onClose(new Callback0() {

					@Override
					public void invoke() throws Throwable {

						System.out.println("Disconnect");
					}
				});

				out.write("Hello client");
			}
		};
	}
	
	public static Result login() {
		return ok(login.render(form(Login.class)));
	}
	
	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		
		String email = loginForm.get().email;
		String password = loginForm.get().password;
		
		if (User.authenticate(email, password) == null) {
			return forbidden("invalid password");
		}
		
		session().clear();
		session("email", email);
		
		return redirect(routes.Products.index());
	}
	
	public static class Login {
		public String email;
		public String password;
	}
}
