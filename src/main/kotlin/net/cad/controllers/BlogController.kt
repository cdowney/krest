package net.cad.controllers

import net.cad.models.BlogPost
import net.cad.repositories.BlogPostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = "\${api.base}", produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
class BlogController {

    @Autowired
    lateinit private var blogPostRepository: BlogPostRepository;

    @RequestMapping("/blogposts", method = arrayOf(RequestMethod.GET))
    fun getBlogPosts(): MutableIterable<BlogPost> {
        return blogPostRepository.findAll()
    }

    @RequestMapping("/blogpost/{id}", method = arrayOf(RequestMethod.GET))
    fun getBlogPost(@PathVariable id: UUID): BlogPost {
        return blogPostRepository.findOne(id);
    }

    @RequestMapping("/blogpost/{id}", method = arrayOf(RequestMethod.DELETE))
    fun deleteBlogPost(@PathVariable id: UUID) {
        blogPostRepository.delete(id);
    }

    @RequestMapping(value = "/blogpost", method = arrayOf(RequestMethod.POST))
    fun postBlogPost(@RequestBody content: String) {
        var blogPost: BlogPost = BlogPost(content = content)
        blogPostRepository.save(blogPost)
    }

}
