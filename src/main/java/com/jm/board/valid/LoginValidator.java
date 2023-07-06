package com.jm.board.valid;

import com.jm.board.entity.LoginForm;
import com.jm.board.entity.Member;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LoginValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return LoginForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginForm loginForm = (LoginForm)target;
        // 아이디
        if(!StringUtils.hasText(loginForm.getMemid())) {
            errors.rejectValue("memid","required.loginForm.memid");
        }
        if(!StringUtils.hasText(loginForm.getPassword())) {
            errors.rejectValue("password","required.loginForm.password");
        }
    }
}
