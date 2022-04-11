package com.security.course.authentication.service;

import com.google.common.collect.Lists;
import com.security.course.authentication.AuthenticatingStudent;
import com.security.course.authentication.interfacedao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.security.course.security.UserRole.*;

@Repository("fake")
public class FakeStudentDaoApp implements StudentDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeStudentDaoApp(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<AuthenticatingStudent> studentByUserName(String username) {
        return getAuthenticatingUserts()
                .stream()
                .filter(student -> username.equalsIgnoreCase(student.getUsername()))
                .findFirst();
    }

    private List<AuthenticatingStudent> getAuthenticatingUserts(){

        /**
         * returns a list of predefined users, can be replaced with db option
         */
        return Lists.newArrayList(
                new AuthenticatingStudent(
                        STUDENT.getGrantedAuthorities(),
                        passwordEncoder.encode("password"),
                        "Harley",
                        true,
                        true,
                        true,
                        true
                ),
                new AuthenticatingStudent(
                        ADMIN.getGrantedAuthorities(),
                        passwordEncoder.encode("password"),
                        "linda",
                        true,
                        true,
                        true,
                        true
                ),
                new AuthenticatingStudent(
                        ADMIN_TRAINEE.getGrantedAuthorities(),
                        passwordEncoder.encode("password"),
                        "tom",
                        true,
                        true,
                        true,
                        true
                )
        );
    }
}
