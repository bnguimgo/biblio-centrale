package com.bnguimgo.biblio.biblocentrale.service;

import com.bnguimgo.biblio.biblocentrale.dto.UserDTO;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioErrorEnum;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioException;
import com.bnguimgo.biblio.biblocentrale.mapper.DtoMapper;
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
    private DtoMapper mapper;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ReadOnlyProperty
    public UserDTO findByEmail(String email) throws BiblioException {

        //REMARQUE: Ne jamais exposer une entité JPA directement en REST, surtout si cette entité contient une collection
        // --> Eviter de retourner directement le User, il faut plutôt mapper User en UserDTO,
        // Sinon, on aura LazyInitializationException → encapsulée dans HttpMessageNotWritableException
        return userRepository.findByEmail(email).map(mapper::toUserDTO).orElseThrow(() -> new BiblioException(BiblioErrorEnum.USER_NOT_FOUND, HttpStatus.NO_CONTENT, "No User found with email " + email));
    }
}
