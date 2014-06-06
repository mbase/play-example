package utils;

import play.data.validation.Constraints;
import play.libs.F.Tuple;

public class EanValidator extends Constraints.Validator<String>{

	@Override
	public boolean isValid(String object) {
		String pattern = "^[0-9]{13}$";
		return object != null && object.matches(pattern);
	}
	
	@Override
	public Tuple<String, Object[]> getErrorMessageKey() {
		return new Tuple<String, Object[]>("error.invalid.ean", new Object[]{});
	}
}
