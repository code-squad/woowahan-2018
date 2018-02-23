package com.woowahan.woowahan2018.dto.group;

import com.woowahan.woowahan2018.dto.group.email.EmailFirstGroup;
import com.woowahan.woowahan2018.dto.group.email.EmailSecondGroup;
import com.woowahan.woowahan2018.dto.group.email.EmailThirdGroup;

import javax.validation.GroupSequence;

@GroupSequence(value = {EmailFirstGroup.class, EmailSecondGroup.class, EmailThirdGroup.class})
public interface EmailPriorityGroup {
}
