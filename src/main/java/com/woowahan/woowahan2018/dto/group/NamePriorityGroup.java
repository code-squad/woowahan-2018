package com.woowahan.woowahan2018.dto.group;

import com.woowahan.woowahan2018.dto.group.name.NameFirstGroup;

import javax.validation.GroupSequence;

@GroupSequence(value = {NameFirstGroup.class})
public interface NamePriorityGroup {
}
