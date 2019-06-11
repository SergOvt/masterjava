package ru.javaops.masterjava.service.mail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private Integer id;
    private String email;
    private Boolean success;
    private String couse;
}
