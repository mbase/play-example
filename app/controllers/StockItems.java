package controllers;

import java.util.List;

import models.StockItem;

import play.mvc.Controller;
import play.mvc.Result;

public class StockItems extends Controller{

	public static Result index() {
		List<StockItem> items = StockItem.find.findList();
		return ok(items.toString());
	}
}
