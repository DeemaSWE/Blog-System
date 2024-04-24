package com.example.blogmanagementsystem.Service;

import com.example.blogmanagementsystem.Api.ApiException;
import com.example.blogmanagementsystem.Model.Blog;
import com.example.blogmanagementsystem.Model.User;
import com.example.blogmanagementsystem.Repository.AuthRepository;
import com.example.blogmanagementsystem.Repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final AuthRepository authRepository;

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public void addBlog(Integer authId, Blog blog) {
        User authUser = authRepository.findUserById(authId);
        blog.setUser(authUser);
        blogRepository.save(blog);
    }

    public void updateBlog(Integer authId, Integer blogId, Blog updatedBlog) {
        Blog blog = blogRepository.findBlogById(blogId);

        if(blog == null)
            throw new ApiException("Blog not found");

        if(blog.getUser().getId() != authId)
            throw new ApiException("You are not authorized to update this blog");

        blog.setTitle(updatedBlog.getTitle());
        blog.setBody(updatedBlog.getBody());

        blogRepository.save(blog);
    }

    public void deleteBlog(Integer authId, Integer blogId) {
        Blog blog = blogRepository.findBlogById(blogId);
        User authUser = authRepository.findUserById(authId);

        if(blog == null)
            throw new ApiException("Blog not found");

        if(authUser.getRole().equals("USER") && blog.getUser().getId() != authId)
            throw new ApiException("You are not authorized to delete this blog");

        blogRepository.delete(blog);
    }

    public Set<Blog> getMyBlogs(Integer authId) {
        User authUser = authRepository.findUserById(authId);
        return authUser.getBlogs();
    }

    public Blog getBlog(Integer blogId) {
        Blog blog = blogRepository.findBlogById(blogId);

        if(blog == null)
            throw new ApiException("Blog not found");

        return blog;
    }

    public List<Blog> getBlogByTitle(String title) {
        return blogRepository.findBlogByTitle(title);
    }
}
