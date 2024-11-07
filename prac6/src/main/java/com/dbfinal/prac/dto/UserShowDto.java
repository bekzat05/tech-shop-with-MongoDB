package com.dbfinal.prac.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserShowDto {
    private String id;
    private String name;
    private int age;
    private String gender;
    private String email;
    private double balance;
}
