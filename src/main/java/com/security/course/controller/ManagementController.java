package com.security.course.controller;

import com.security.course.dto.StudentDto;
import com.security.course.model.Student;
import com.security.course.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("management/student")
public class ManagementController {

    @Autowired
    StudentRepository repository;

    @GetMapping("")
    /*
     *Replaces
     * .antMatchers(API).hasAnyRole(ROLES)
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    @PostMapping("/register")

    /*
     *Replaces
     * .antMatchers(HttpMethod.METHOD, API).hasAuthority(PERMISSION_TYPE.getPermission())
     */
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody StudentDto student) {
        log.info("Retrieved Data: " + student);
    }

    @DeleteMapping("/delete/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable int studentId) {
        log.info("Id provided:: " + studentId);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudentById(@PathVariable int id, @RequestBody StudentDto student) {
        log.info("Id provided:: " + id);
        log.info("Data: " + student);
    }

}
