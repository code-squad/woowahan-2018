package com.woowahan.woowahan2018.dto.group;

import com.woowahan.woowahan2018.dto.group.password.PasswordFirstGroup;
import com.woowahan.woowahan2018.dto.group.password.PasswordSecondGroup;
import com.woowahan.woowahan2018.dto.group.password.PasswordThirdGroup;

import javax.validation.GroupSequence;

@GroupSequence(value = {PasswordFirstGroup.class, PasswordSecondGroup.class, PasswordThirdGroup.class})
public interface PasswordPriorityGroup {
}
