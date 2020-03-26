package com.climatesystem.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.climatesystem.model.UserModel;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserModel> {

	@Override
	public boolean isValid(UserModel user, ConstraintValidatorContext context) {
		
		String plainPassword = user.getPlainPassword();
		String repeatPassword = user.getRepeatPassword();
		
		if(plainPassword == null || plainPassword.length() == 0) {
			return true;
		}
		
		if(plainPassword == null || !plainPassword.equals(repeatPassword)) {
			return false;
		}
		
		return true;
	}
	
}
