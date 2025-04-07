package com.web.museum.controller;

import com.web.museum.dao.AuthorRepository;
import com.web.museum.dto.AuthorResponseDTO;
import com.web.museum.dto.PagedResponse;
import com.web.museum.entity.Author;
import com.web.museum.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<PagedResponse<AuthorResponseDTO>> getAllAuthors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "asc") String sort
    ) {
        Sort sortBy = sort.equalsIgnoreCase("desc") ?
                Sort.by("name").descending() :
                Sort.by("name").ascending();

        Pageable pageable = PageRequest.of(page, size, sortBy);

        Page<AuthorResponseDTO> authors = authorService.getAllAuthors(name, type, sort, pageable);

        PagedResponse<AuthorResponseDTO> response = new PagedResponse<>(
                authors.getContent(),
                authors.getNumber(),
                authors.getSize(),
                authors.getTotalElements(),
                authors.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(@PathVariable  int id){
        AuthorResponseDTO author = authorService.getAuthorById(id);
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDTO> createAuthor(@RequestBody Author reqAuthor){
        AuthorResponseDTO author = authorService.createAuthor(reqAuthor);
        return ResponseEntity.ok(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> updateAuthor(@PathVariable int id, @RequestBody Author reqAuthor){
        AuthorResponseDTO author = authorService.updateAuthor(id,reqAuthor);
        return ResponseEntity.ok(author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable int id ){
        authorService.deleteAuthor(id);
        return ResponseEntity.ok("Success delete author id = " + id);
    }

}
