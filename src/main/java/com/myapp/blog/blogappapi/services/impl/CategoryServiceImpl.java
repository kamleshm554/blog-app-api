package com.myapp.blog.blogappapi.services.impl;

import com.myapp.blog.blogappapi.entities.Category;
import com.myapp.blog.blogappapi.exceptions.ResourceNotFoundException;
import com.myapp.blog.blogappapi.payloads.CategoryDto;
import com.myapp.blog.blogappapi.repositories.CategoryRepo;
import com.myapp.blog.blogappapi.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat= this.modelMapper.map(categoryDto, Category.class);
        Category addedCat=this.categoryRepo.save(cat);
        return this.modelMapper.map(addedCat, CategoryDto.class);


    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        Category updatedCat= this.categoryRepo.save(cat);
        return this.modelMapper.map(updatedCat, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
        this.categoryRepo.delete(cat);

    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category cat= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));

        return this.modelMapper.map(cat,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories= this.categoryRepo.findAll();
        List<CategoryDto> catDto= categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());

        return catDto;
    }
}
