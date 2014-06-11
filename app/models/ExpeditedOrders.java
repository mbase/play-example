package models;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.ArrayList;
import java.util.List;

import play.libs.Akka;
import play.mvc.WebSocket.Out;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class ExpeditedOrders extends UntypedActor {

	static List<Out<String>> members = new ArrayList<Out<String>>();

	static ActorRef defaultActor = Akka.system().actorOf(
			new Props(ExpeditedOrders.class));

	static {
		Akka.system().scheduler().schedule(
				Duration.create(4, SECONDS),
				Duration.create(5, SECONDS), 
				defaultActor, 
				new Order(),
				Akka.system().dispatcher(), 
				null);
	}

	public static void register(Out<String> out) {
		members.add(out);
	}
	
	public static void unregister(Out<String> out) {
		members.remove(out);
	}
	
	public static void notifyOthers(Out<String> me, String event) {
		for (Out<String> out : members) {
			if (!out.equals(me)) {
				out.write(event);
			}
		}
	}
	
	public static void notifyAll(String event) {
		for (Out<String> out : members) {
			out.write(event);
		}
	}
	
	@Override
	public void onReceive(Object arg0) throws Exception {
		Order order = (Order) arg0;
		notifyAll(order.toString());
	}
}