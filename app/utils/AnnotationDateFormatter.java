package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import play.data.format.Formatters;

public class AnnotationDateFormatter extends Formatters.AnnotationFormatter<DateFormat, Date>{

	@Override
	public Date parse(DateFormat annotation, String text, Locale locale)
			throws ParseException {
		
		if (text == null || text.trim().isEmpty()) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(annotation.value(), locale);
		sdf.setLenient(false);
		return sdf.parse(text);
	}
	
	@Override
	public String print(DateFormat annotation, Date value, Locale locale) {
		if (value == null) {
			return "";
		}
		
		return new SimpleDateFormat(annotation.value(), locale).format(value);
	}
}
