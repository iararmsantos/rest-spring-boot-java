package com.iarasantos.controllers;

import com.iarasantos.data.vo.v1.BookVO;
import com.iarasantos.services.BookServices;
import com.iarasantos.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/book/v1")
@Tag(name = "Book", description = "Endpoints for Managing Books")
public class BookController {

    @Autowired
    private BookServices service;

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds all Books", description = "Finds all Books", tags = {"books"},responses = {
            @ApiResponse(description = "Success", responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BookVO.class))
                    )
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    public List<BookVO> findAll() {
        return service.findAll();
    }

    @GetMapping(value="/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds a book", description = "Finds a book", tags = {"books"},responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = BookVO.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    public BookVO findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Adds a new book", description = "Adds a new book by passing a JSON, XML, or YML representation of the book", tags = {"books"},responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = BookVO.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    public BookVO create(@RequestBody BookVO book) {
        return service.create(book);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Updates a book", description = "Updates a book by passing a JSON, XML, or YML representation of the book", tags = {"books"},responses = {
            @ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = BookVO.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    public BookVO update(@RequestBody BookVO book) {
        return service.update(book);
    }

    @DeleteMapping(value="/{id}")
    @Operation(summary = "Deletes a book", description = "Deletes a book by passing a JSON, XML, or YML representation of the book", tags = {"books"},responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
