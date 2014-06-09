package controllers;

import java.util.List;
import java.util.concurrent.Callable;

import models.Report;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Reports extends Controller {

	public static Result index() {
		Promise<Report> promiseOfKPIReport = play.libs.Akka
				.future(new Callable<Report>() {
					public Report call() {
						return intensiveKPIReport();
					}
				});

		Promise<Report> promiseOfETAReport = play.libs.Akka
				.future(new Callable<Report>() {
					public Report call() {
						return intensiveETAReport();
					}
				});
		
		Promise<List<Report>> promises = Promise.waitAll(promiseOfKPIReport, promiseOfETAReport);
		
		return async(
				promises.map(
						new Function<List<Report>, Result>() {
							public Result apply(List<Report> reports) {
								return ok(report.render(reports));
							}
						})
		);

	}

	public static Report intensiveKPIReport() {
		Report report = new Report("KPI Report");
		report.execute();
		return report;
	}

	public static Report intensiveETAReport() {
		Report report = new Report("ETA Report");
		report.execute();
		return report;
	}
}