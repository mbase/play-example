package controllers;

import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.SimpleResult;
import play.mvc.Http.Context;
import utils.ExceptionMailer;

public class CatchAction extends Action.Simple {

	@Override
	public Promise<SimpleResult> call(Context ctx) throws Throwable {
		try {
			return delegate.call(ctx);
		} catch (Exception e) {
			ExceptionMailer.send(e);
			throw new RuntimeException();
		}
	}
}
