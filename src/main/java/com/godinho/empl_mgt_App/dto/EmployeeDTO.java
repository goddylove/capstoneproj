package com.godinho.empl_mgt_App.dto;

import lombok.Data;

@Data
public class EmployeeDTO {
    private Long id;
    private String name;
    private String email;
    private String departmentName;

    // Getters and setters
}

