package com.lrm.web.admin;

import com.lrm.po.Blog;
import com.lrm.po.User;
import com.lrm.service.BlogService;
import com.lrm.service.TagService;
import com.lrm.service.TypeService;
import com.lrm.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:admin/blogs";
    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;
    @Autowired
    private TypeService typeService;
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2,sort = {"updateTime"},direction =  Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable, blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2,sort = {"updateTime"},direction =  Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable, blog));

        //返回blogList片段，局部渲染
        return "admin/blogs :: blogList";
    }

    //新增页面
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("blog",new Blog());
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){

        //拿到当前的用户
        blog.setUser((User) session.getAttribute("user")) ;
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b = blogService.saveBlog(blog);
        if(b == null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }
}
