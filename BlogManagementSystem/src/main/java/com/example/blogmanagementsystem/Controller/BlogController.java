package com.example.blogmanagementsystem.Controller;

import com.example.blogmanagementsystem.Model.Blog;
import com.example.blogmanagementsystem.Model.User;
import com.example.blogmanagementsystem.Service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    // All
    @GetMapping("/get-all")
    public ResponseEntity getAllBlogs(){
        return ResponseEntity.status(200).body(blogService.getAllBlogs());
    }

    // User
    @PostMapping("/add")
    public ResponseEntity addBlog(@AuthenticationPrincipal User auth, @RequestBody @Valid Blog blog){
        blogService.addBlog(auth.getId(), blog);
        return ResponseEntity.status(200).body("Blog added successfully");
    }

    // User
    @PutMapping("/update/{blogId}")
    public ResponseEntity updateBlog(@AuthenticationPrincipal User auth, @PathVariable Integer blogId, @RequestBody @Valid Blog updatedBlog){
        blogService.updateBlog(auth.getId(), blogId, updatedBlog);
        return ResponseEntity.status(200).body("Blog updated successfully");
    }

    // Admin and User
    @DeleteMapping("/delete/{blogId}")
    public ResponseEntity deleteBlog(@AuthenticationPrincipal User auth, @PathVariable Integer blogId){
        blogService.deleteBlog(auth.getId(), blogId);
        return ResponseEntity.status(200).body("Blog deleted successfully");
    }

    // User
    @GetMapping("/get-my-blogs")
    public ResponseEntity getMyBlogs(@AuthenticationPrincipal User auth){
        return ResponseEntity.status(200).body(blogService.getMyBlogs(auth.getId()));
    }

    // All
    @GetMapping("/get-blog/{blogId}")
    public ResponseEntity getBlog(@PathVariable Integer blogId){
        return ResponseEntity.status(200).body(blogService.getBlog(blogId));
    }

    // All
    @GetMapping("/get-by-title/{title}")
    public ResponseEntity getBlogByTitle(@PathVariable String title){
        return ResponseEntity.status(200).body(blogService.getBlogByTitle(title));
    }

}
