package com.lzh.security.validator;

import com.lzh.security.domain.dto.UserDTO;
import com.lzh.security.validator.annotation.PasswordMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserDTO> {

    @Override
    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext constraintValidatorContext) {
        return userDTO.getPassword().equals(userDTO.getMatchingPassword());
    }
}
