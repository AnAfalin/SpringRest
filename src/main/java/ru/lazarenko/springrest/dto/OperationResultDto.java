package ru.lazarenko.springrest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OperationResultDto {
    Boolean success;
    String message;
}
