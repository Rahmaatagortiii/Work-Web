package com.wellbeignatwork.backend.controller.Collaboration;


import com.wellbeignatwork.backend.entity.Collaboration.Category;
import com.wellbeignatwork.backend.service.Collaboration.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")

public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //http://localhost:8081/Wellbeignatwork/category/
    @PostMapping("/")
    public ResponseEntity<?> addCategory(@RequestBody Category category){
    	Category category1   = this.categoryService.addCategory(category);
    	return ResponseEntity.ok(category1);
    }


    //http://localhost:8081/Wellbeignatwork/category/
    @PutMapping("/")
    public ResponseEntity<?> updateCategory(@RequestBody Category category){
    	Category category1   = this.categoryService.updateCategory(category);
    	return ResponseEntity.ok(category1);
    }
    
    //http://localhost:8081/Wellbeignatwork/category/1
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Long categoryId) {
        this.categoryService.deleteCategory(categoryId);
    }


    //http://localhost:8081/Wellbeignatwork/category/1
    @GetMapping("/{categoryId}")
    public Category getCategory(@PathVariable("categoryId") Long categoryId) {
        return this.categoryService.getCategory(categoryId);
    }

    //http://localhost:8081/Wellbeignatwork/category/
    @GetMapping("/")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(this.categoryService.getCategories());
    }
}
