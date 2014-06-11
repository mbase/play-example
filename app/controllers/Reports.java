package controllers;

import java.util.List;

import models.Report;
import play.libs.F.Function;
import play.libs.F.Function0;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.report;

public class Reports extends Controller {

	public static Promise<Result> index() {

		Promise<Report> promiseOfKPIReport = Promise
				.promise(new Function0<Report>() {
					public Report apply() {
						return intensiveKPIReport();
					}
				});

		Promise<Report> promiseOfETAReport = Promise
				.promise(new Function0<Report>() {
					public Report apply() {
						return intensiveETAReport();
					}
				});

		Promise<List<Report>> promises = Promise.sequence(promiseOfKPIReport,
				promiseOfETAReport);

		return promises.map(new Function<List<Report>, Result>() {
			public Result apply(List<Report> reports) {
				return ok(report.render(reports));
			}
		});

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
