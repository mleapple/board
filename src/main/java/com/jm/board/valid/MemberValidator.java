package com.jm.board.valid;

import com.jm.board.entity.Member;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

@Component
public class MemberValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Member member = (Member)target;
        // 아이디
        if(!StringUtils.hasText(member.getMemid())) {
            errors.rejectValue("memid","required");
        }
        if(!StringUtils.hasText(member.getName())) {
            errors.rejectValue("name","required.member.name");
        }
        if(!StringUtils.hasText(member.getPassword())) {
            errors.rejectValue("password","required.member.password");
        }
        if(member.getAge() <1) {
            errors.rejectValue("age","required.member.age", new Object[]{1, 120}, null);
        }
        // 이름

    }
}
