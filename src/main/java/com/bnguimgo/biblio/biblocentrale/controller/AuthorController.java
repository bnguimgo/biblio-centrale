package com.bnguimgo.biblio.biblocentrale.controller;

import com.bnguimgo.biblio.biblocentrale.dto.AuthorDTO;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioException;
import com.bnguimgo.biblio.biblocentrale.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <h2>Qu'est ce que l'annotation @CrossOrigin dans spring ? </h2>
 * L’annotation @CrossOrigin dans Spring (souvent utilisée avec Spring Web MVC ou Spring Boot) sert à gérer les requêtes CORS (Cross-Origin Resource Sharing), c’est-à-dire les requêtes HTTP venant d’un domaine différent de celui où tourne votre application backend.
 * <p>
 * Contexte du problème
 * Par défaut, pour des raisons de sécurité, les navigateurs bloquent les requêtes AJAX ou fetch venant d’une page web hébergée sur un autre domaine ou port.
 * <p>
 * Exemple :<br />
 * Frontend : <a href="http://localhost:8092">Thymeleaf...</a> (Thymeleaf ou Angular)
 * <p>
 * Backend : <a href="http://localhost:8094">Spring Boot...</a> (Spring Boot)
 * Sans configuration CORS, le navigateur rejettera la requête.
 * <p>
 * Rôle de @CrossOrigin
 * Cette annotation indique à Spring qu’il doit accepter des requêtes venant d’origines spécifiques.
 * On peut la mettre :
 * <p>
 * Au niveau d’un contrôleur → tous les endpoints de ce contrôleur sont autorisés
 * <p>
 * Au niveau d’une méthode → uniquement cet endpoint est autorisé
 */
@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO){
        return new ResponseEntity<>(authorService.createAuthor(authorDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable(value = "id") Long id) {
        return authorService.getAuthorById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{firstName}/{lastName}")
    public ResponseEntity<AuthorDTO> findByFirstNameAndLastName(@PathVariable(value = "firstName") String firstName, @PathVariable(value = "lastName") String lastName) {

        return authorService.findByFirstNameAndLastName(firstName, lastName).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable(value = "id") Long id,@RequestBody AuthorDTO authorDTO) throws BiblioException {
        return new ResponseEntity<>(authorService.updateAuthor(id, authorDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return new ResponseEntity<>(authorService.getAllAuthors(), HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) throws BiblioException {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
