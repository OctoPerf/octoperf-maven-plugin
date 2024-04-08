package com.octoperf.task.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME)
public interface Task {

}
