package com.security.course.authentication.interfacedao;

import com.security.course.authentication.AuthenticatingStudent;

import java.util.Optional;

/**
 * Is used for managing of students/users in database
 *
 * Easier to shift implementation of db i.e. from Mongodb to Postgres by just changing one class
 */
public interface StudentDao {
    Optional<AuthenticatingStudent> studentByUserName(String username);
}
