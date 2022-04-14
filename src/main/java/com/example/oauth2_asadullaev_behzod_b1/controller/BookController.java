package com.example.oauth2_asadullaev_behzod_b1.controller;

import com.example.oauth2_asadullaev_behzod_b1.dto.BookDto;
import com.example.oauth2_asadullaev_behzod_b1.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated BookDto bookDto) {
        bookService.save(bookDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<?>> pageQuery(@PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(bookService.findByCondition(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated BookDto bookDto, @PathVariable("id") Long id) {
        bookService.update(bookDto, id);
        return ResponseEntity.ok().build();
    }
}