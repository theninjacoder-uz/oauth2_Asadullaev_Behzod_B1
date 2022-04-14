package com.example.oauth2_asadullaev_behzod_b1.service;

import com.example.oauth2_asadullaev_behzod_b1.dto.BookDto;
import com.example.oauth2_asadullaev_behzod_b1.entity.BookEntity;
import com.example.oauth2_asadullaev_behzod_b1.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;


    public BookEntity save(BookDto bookDto) {
        BookEntity bookEntity = modelMapper.map(bookDto, BookEntity.class);
        return bookRepository.save(bookEntity);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public BookEntity findById(Long id) {

        return bookRepository.findById(id).orElse(null);
    }

    public Page<BookEntity> findByCondition(Pageable pageable) {
        Page<BookEntity> entityPage = bookRepository.findAll(pageable);
        List<BookEntity> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    public BookEntity update(BookDto bookDto, Long id) {
        BookEntity data = findById(id);
       data.setAuthor(bookDto.getAuthor());
       data.setName(bookDto.getName());
       data.setPrice(bookDto.getPrice());

        return bookRepository.save(data);
    }
}