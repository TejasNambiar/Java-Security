package com.security.course.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private int id;
    private String name;
    private String email;
}
