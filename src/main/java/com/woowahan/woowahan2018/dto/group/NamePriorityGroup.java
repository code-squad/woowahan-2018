package com.woowahan.woowahan2018.dto.group;

import com.woowahan.woowahan2018.dto.group.name.NameFirstGroup;
import com.woowahan.woowahan2018.dto.group.name.NameSecondGroup;

import javax.validation.GroupSequence;

@GroupSequence(value = {NameFirstGroup.class, NameSecondGroup.class})
public interface NamePriorityGroup {
}
