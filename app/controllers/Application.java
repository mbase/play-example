package controllers;

import models.ExpeditedOrders;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.index;

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
}
