package com.inventorysystem.Backend.mapper;

import com.inventorysystem.Backend.dto.category.CategoryDTO;
import com.inventorysystem.Backend.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CategoryDTO categoryToDTO(Category category) {
        if (category == null) {
            return null; // Handle null category
        }
        return modelMapper.map(category, CategoryDTO.class);
    }
}
