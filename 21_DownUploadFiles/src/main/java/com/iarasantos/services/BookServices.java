package com.iarasantos.services;

import com.iarasantos.controllers.BookController;
import com.iarasantos.controllers.PersonController;
import com.iarasantos.data.vo.v1.BookVO;
import com.iarasantos.data.vo.v1.PersonVO;
import com.iarasantos.exceptions.RequiredObjectIsNullException;
import com.iarasantos.exceptions.ResourceNotFoundException;
import com.iarasantos.mapper.DozerMapper;
import com.iarasantos.model.Book;
import com.iarasantos.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {
    private Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository repository;

    @Autowired
    PagedResourcesAssembler<BookVO> assembler;


    /**
     * Convert object to entity then save it and convert back to object
     * @param bookVO object to be converted, saved, then converted back
     * */
    public BookVO create(BookVO bookVO) {
        if(bookVO == null) throw new RequiredObjectIsNullException();
        logger.info("Creating one book!");
        Book entity = DozerMapper.parseObject(bookVO, Book.class);
        var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO bookVO) {
        if(bookVO == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one book!");
        Book entity = repository.findById(bookVO.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));

        entity.setAuthor(bookVO.getAuthor());
        entity.setTitle(bookVO.getTitle());
        entity.setPrice(bookVO.getPrice());
        entity.setLaunchDate(bookVO.getLaunchDate());

        var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);

        vo.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one book!");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
        repository.delete(entity);
    }

    /**
     * Find object then convert to entity to return it
     * @param id  of the object to be found
     * */
    public BookVO findById(Long id) {
        logger.info("Searching one book!");

        Book  entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    /**
     * Find all objects then convert to entity's list to return it
     * */
    public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {
        logger.info("Searching all people!");

        var bookPage = repository.findAll(pageable);
        var bookVOPage = bookPage.map(b -> DozerMapper.parseObject(b, BookVO.class));

                bookVOPage.map(b -> b.add(
                        linkTo(methodOn(BookController.class)
                                .findById(b.getKey())).withSelfRel()));


        Link link = linkTo(methodOn(BookController.class)
                .findAll(pageable.getPageNumber(),
                        pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(bookVOPage, link);
    }
}