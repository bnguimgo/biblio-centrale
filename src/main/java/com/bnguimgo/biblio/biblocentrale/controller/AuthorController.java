package com.bnguimgo.biblio.biblocentrale.controller;

import com.bnguimgo.biblio.biblocentrale.dto.AuthorDTO;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioErrorEnum;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioException;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioRuntimeException;
import com.bnguimgo.biblio.biblocentrale.service.AuthorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import java.util.function.Supplier;

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

/*    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable(value = "id") Long id) {
        return authorService.getAuthorById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }*/

/*    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        String idToken = request.getHeader("Authorization");
        if(null != idToken) {
            idToken = idToken.substring(7);
        }
        return authorService.getAuthorById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }*/

    /**
     * Source : <a href="https://www.baeldung.com/spring-extract-custom-header-request">spring-extract-custom-header-request</a>
     * NB : Ne pas utiliser directement HttpServletRequest, car on n'a pas besoin de toutes ses méthodes
     * En lieu et place, il faut utiliser @RequestHeader(name="header_property_name")
     * On peut également utiliser HandlerInterceptor
     * @param id author id
     * @param idToken token identify
     * @return return serialized Author
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable(value = "id") Long id, @RequestHeader(name = "Authorization") String idToken, Authentication authentication) throws BiblioException {
        if(null != idToken) {
            idToken = idToken.substring(7);
        }
        String name = authentication.getName();
        //return authorService.getAuthorById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
        //return authorService.getAuthorById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        //return new ResponseEntity<>(authorService.getAuthorById(id).orElseGet(AuthorDTO::new), HttpStatus.FOUND);
        return new ResponseEntity<>(authorService.getAuthorById(id).orElseThrow(() -> new BiblioException(BiblioErrorEnum.AUTHOR_NOT_FOUND, HttpStatus.NO_CONTENT, "No Author found with id " +id)), HttpStatus.FOUND);
    }

    @GetMapping("/{firstName}/{lastName}")
    public ResponseEntity<AuthorDTO> findByFirstNameAndLastName(@PathVariable(value = "firstName") String firstName, @PathVariable(value = "lastName") String lastName) {

        return authorService.findByFirstNameAndLastName(firstName, lastName).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
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
