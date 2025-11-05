package com.bnguimgo.biblio.biblocentrale.service;

import com.bnguimgo.biblio.biblocentrale.entity.User;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioErrorEnum;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioException;
import com.bnguimgo.biblio.biblocentrale.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
        isolation = Isolation.READ_COMMITTED,
        propagation = Propagation.SUPPORTS,
        readOnly = true,
        timeout = 30)
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ReadOnlyProperty
    public User findByEmail(String email) throws BiblioException {

        return userRepository.findByEmail(email).orElseThrow(() -> new BiblioException(BiblioErrorEnum.USER_NOT_FOUND, HttpStatus.NO_CONTENT, "No User found with email " + email));
    }
}
