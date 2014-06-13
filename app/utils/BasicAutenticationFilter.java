package utils;

import java.util.ArrayList;
import java.util.List;

import static play.mvc.Results.*;
import play.api.libs.iteratee.*;
import play.api.libs.iteratee.Done;
import play.api.mvc.*;
import play.libs.Scala;
import scala.Option;
import scala.Tuple2;
import scala.collection.Seq;
import scala.runtime.AbstractFunction1;
import sun.misc.BASE64Decoder;

public class BasicAutenticationFilter implements EssentialFilter {

	public BasicAutenticationFilter() {
	}

	@Override
	public EssentialAction apply(final EssentialAction next) {
		return new JavaEssentialAction() {

			@Override
			public Iteratee<byte[], SimpleResult> apply(RequestHeader arg0) {
				Option<String> authorization = arg0.headers().get(
						"Authorization");
				if (!authorization.isEmpty()) {
					String auth = authorization.get();
					BASE64Decoder decoder = new BASE64Decoder();
					String passAndUser = auth.split(" ")[1];
					try {
						String[] pass = new String(
								decoder.decodeBuffer(passAndUser)).split(":");
						String username = pass[0];
						String password = pass[1];

						if ("nicolas".equals(username)
								&& "nicolas".equals(password)) {
							return next.apply(arg0);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				List<Tuple2<String, String>> list = new ArrayList<Tuple2<String, String>>();
				Tuple2<String, String> t = new Tuple2<String, String>(
						"WWW-Authenticate", "Basic realm=\"warehouse app\"");
				list.add(t);

				Seq<Tuple2<String, String>> seq = Scala.toSeq(list);

				return play.api.libs.iteratee.Done.apply(
						unauthorized("Forbidden access to the warehouse app")
								.getWrappedSimpleResult().withHeaders(seq),
						null);
			}

			@Override
			public EssentialAction apply() {
				return next.apply();
			}
		};
	}

	public abstract class JavaEssentialAction extends
			AbstractFunction1<RequestHeader, Iteratee<byte[], SimpleResult>>
			implements EssentialAction {
	}
}
