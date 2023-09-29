package com.myapp.blog.blogappapi.controllers;

import com.myapp.blog.blogappapi.payloads.ApiResponse;
import com.myapp.blog.blogappapi.payloads.CategoryDto;
import com.myapp.blog.blogappapi.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createCategory= this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);

    }

    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId){
        CategoryDto updatedCategory= this.categoryService.updateCategory(categoryDto, catId);
        return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);

    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory( @PathVariable Integer catId){
        this.categoryService.deleteCategory(catId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted successfully !!",true), HttpStatus.OK);


    }



    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategory( @PathVariable Integer catId){
        CategoryDto categoryDto = this.categoryService.getCategory(catId);
        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);

    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories(){
        List<CategoryDto> categories = this.categoryService.getCategories();
        return ResponseEntity.ok(categories);

    }






}
