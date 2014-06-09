package models;

public class Report {

	public String name;

	public Report(String name) {
		this.name = name;
	}

	public void execute() {
		long start = System.currentTimeMillis();
		play.Logger.info("starting intensive " + name + " report at " + start);
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			
		}
		long end = System.currentTimeMillis();
		play.Logger.info("done with intensive " + name + " report at " + end);
		play.Logger.info("took "
				+ ((end - start) / 1000) + "s");
	}

	@Override
	public String toString() {
		return name;
	}
}
