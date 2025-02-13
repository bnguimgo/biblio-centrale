package com.bnguimgo.biblio.biblocentrale.service;

import com.bnguimgo.biblio.biblocentrale.dto.AuthorDTO;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioException;
import com.bnguimgo.biblio.biblocentrale.mapper.DtoMapper;
import com.bnguimgo.biblio.biblocentrale.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static com.bnguimgo.biblio.biblocentrale.exception.BiblioErrorEnum.AUTHOR_NOT_FOUND;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    private DtoMapper mapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDTO> getAllAuthors() {

        return authorRepository.findAll().stream()
                .map(mapper::mapToAuthorDTO).collect(Collectors.toList());
    }

    public Optional<AuthorDTO> getAuthorById(Long id) {

        return authorRepository.findById(id).map(mapper::mapToAuthorDTO);
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {

        Date now = Date.from(Instant.now());
        authorDTO.setCreatedDate(now);
        authorDTO.setModifiedDate(now);
        return mapper.mapToAuthorDTO(authorRepository.save(mapper.mapToAuthor(authorDTO)));
    }

    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) throws BiblioException {

        return authorRepository.findById(id).map(author -> {

            Date now = Date.from(Instant.now());
            author.setModifiedDate(now);
            author.setFirstName(authorDTO.getFirstName());
            author.setLastName(authorDTO.getLastName());

            return mapper.mapToAuthorDTO(authorRepository.save(author));

        }).orElseThrow(() -> new BiblioException(AUTHOR_NOT_FOUND, "Author not found with id: " + id));

    }

    public void deleteAuthor(Long id) throws BiblioException {

        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        } else {
            throw new BiblioException(AUTHOR_NOT_FOUND, "Author not found with id: " + id);
        }
    }
    
}
