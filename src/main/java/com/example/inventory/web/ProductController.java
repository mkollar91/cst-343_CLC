package com.example.inventory.web;

import com.example.inventory.domain.Category;
import com.example.inventory.domain.Product;
import com.example.inventory.repository.CategoryRepository;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("formTitle", "Add Product");
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll(Sort.by("name")));
        return "products/form";
    }

    @PostMapping
    public String create(@ModelAttribute("product") Product product, RedirectAttributes ra) {
        normalizeAndValidateCategory(product);
        productService.create(product);
        ra.addFlashAttribute("msg", "Product created.");
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("formTitle", "Edit Product");
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll(Sort.by("name")));
        return "products/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute("product") Product product, RedirectAttributes ra) {
        product.setId(id);
        normalizeAndValidateCategory(product);
        productService.update(product);
        ra.addFlashAttribute("msg", "Product updated.");
        return "redirect:/products";
    }

    // ProductController.java
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            productService.delete(id);
            ra.addFlashAttribute("msg", "Product deleted.");
        } catch (Exception ex) {
            ra.addFlashAttribute("err", "Could not delete product: " + ex.getMessage());
        }
        return "redirect:/products";
    }

    private void normalizeAndValidateCategory(Product product) {
        // If dropdown left as "-- None --", category.id will be null
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            product.setCategory(null);
        } else {
            Integer catId = product.getCategory().getId();
            Category cat = categoryRepository.findById(catId)
                    .orElseThrow(() -> new IllegalArgumentException("Category not found: " + catId));
            product.setCategory(cat);
        }
    }
}
