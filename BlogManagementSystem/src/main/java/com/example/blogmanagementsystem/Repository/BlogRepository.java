package com.example.blogmanagementsystem.Repository;

import com.example.blogmanagementsystem.Model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {

    Blog findBlogById(Integer id);

    List<Blog> findBlogByTitle(String title);
}
