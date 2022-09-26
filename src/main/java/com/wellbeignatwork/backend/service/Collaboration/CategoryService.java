package com.wellbeignatwork.backend.service.Collaboration;



import com.wellbeignatwork.backend.entity.Collaboration.Category;

import java.util.Set;

public interface CategoryService {

	public Category addCategory(Category category);

	public Category updateCategory(Category category);

	public Set<Category> getCategories();

	public Category getCategory(Long categoryId);

	public void  deleteCategory(Long categoryId);

}
