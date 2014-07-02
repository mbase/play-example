

import java.util.List;
import java.util.Map;

import models.Tag;
import play.Application;
import play.GlobalSettings;
import play.api.mvc.EssentialFilter;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		InitialData.insert(app);
	}

	static class InitialData {
		public static void insert(Application application) {
			if (Ebean.find(Tag.class).findRowCount() == 0) {
				Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml
						.load("initial-data.yml");
				Ebean.save(all.get("tags"));
			}

		}
	}
	
	@Override
	public <T extends EssentialFilter> Class<T>[] filters() {
//		Class[] filters = {CSRFFilter.class, BasicAutenticationFilter.class};
		Class[] filters = {};
		return filters;
	}
}
