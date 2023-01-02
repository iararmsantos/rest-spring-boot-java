package com.iarasantos.services;

import com.iarasantos.data.vo.v1.BookVO;
import com.iarasantos.exceptions.handler.RequiredObjectIsNullException;
import com.iarasantos.mapper.mocks.MockBook;
import com.iarasantos.model.Book;
import com.iarasantos.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest extends Object {

    MockBook input;

    @InjectMocks
    private BookServices service;

    @Mock
    BookRepository repository;

    @BeforeEach
    void setUpMocks() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        //before call repository
        Book entity = input.mockEntity(1);
        //after call repository
        Book persisted = entity;
        entity.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));

        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertEquals(26.66, result.getPrice());
        assertEquals(new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), result.getLaunchDate());
    }

    @Test
    void createWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains((expectedMessage)));
    }

    @Test
    void update() {
        //before call repository
        Book entity = input.mockEntity(1);
        entity.setId(1L);
        //after call repository
        Book persisted = entity;
        entity.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));

        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertEquals(26.66, result.getPrice());
        assertEquals(new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), result.getLaunchDate());
    }

    @Test
    void updateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains((expectedMessage)));
    }

    @Test
    void delete() {
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }

    @Test
    void findById() {
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertEquals(26.66, result.getPrice());
        assertEquals(new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), result.getLaunchDate());
    }

    @Test
    void findAll() {
        List<Book> entityList = input.mockEntityList();

        when(repository.findAll()).thenReturn(entityList);

        var people = service.findAll();
        assertNotNull(people);
        assertEquals(14, people.size());

        var book1 = people.get(1);
        assertNotNull(book1);
        assertNotNull(book1.getKey());
        assertNotNull(book1.getLinks());
        assertTrue(book1.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", book1.getAuthor());
        assertEquals("Title Test1", book1.getTitle());
        assertEquals(26.66, book1.getPrice());
        assertEquals(new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), book1.getLaunchDate());

        var book4 = people.get(4);
        assertNotNull(book4);
        assertNotNull(book4.getKey());
        assertNotNull(book4.getLinks());
        System.out.println(book4.getLinks());
        assertTrue(book4.toString().contains("links: [</api/book/v1/4>;rel=\"self\"]"));
        assertEquals("Author Test4", book4.getAuthor());
        assertEquals("Title Test4", book4.getTitle());
        assertEquals(26.66, book4.getPrice());
        assertEquals(new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), book4.getLaunchDate());

        var book7 = people.get(7);
        assertNotNull(book7);
        assertNotNull(book7.getKey());
        assertNotNull(book7.getLinks());
        assertTrue(book7.toString().contains("links: [</api/book/v1/7>;rel=\"self\"]"));
        assertEquals("Author Test7", book7.getAuthor());
        assertEquals("Title Test7", book7.getTitle());
        assertEquals(26.66, book7.getPrice());
        assertEquals(new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), book7.getLaunchDate());
    }
}