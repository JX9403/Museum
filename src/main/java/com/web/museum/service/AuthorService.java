package com.web.museum.service;
import com.web.museum.dto.AuthorResponseDTO;
import com.web.museum.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {
    public Page<AuthorResponseDTO> getAllAuthors(String name, String type, String sort, Pageable pageable);
    AuthorResponseDTO getAuthorById(int id);
    AuthorResponseDTO createAuthor(Author Author);
    AuthorResponseDTO updateAuthor(int id, Author Author);
    void deleteAuthor( int id);
}