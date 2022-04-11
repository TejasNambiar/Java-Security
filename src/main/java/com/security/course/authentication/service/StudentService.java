package com.security.course.authentication.service;

import com.security.course.authentication.interfacedao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StudentService implements UserDetailsService {

    private final StudentDao studentDao;

    /**
     * Using qualifier to signify only one implementation
     * @param studentDao
     */
    @Autowired
    public StudentService(@Qualifier("fake") StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return studentDao.studentByUserName(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s not found",username)));
    }
}
