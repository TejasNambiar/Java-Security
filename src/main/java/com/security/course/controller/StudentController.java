package com.security.course.controller;

import com.security.course.model.Student;
import com.security.course.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {

    @Autowired
    StudentRepository repository;

    @GetMapping("")
    public List<Student> getALlStudents(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        if (repository.findById(id).isPresent())
            return repository.findById(id).get();
        throw new IllegalStateException("Student with id:: " + id + " NOT FOUND!");

    }
}
