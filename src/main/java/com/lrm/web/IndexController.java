package com.lrm.web;

import com.lrm.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {

       /* String blog = null;
        if (blog == null) {
            //抛出自定义的异常new NotFoundException
            throw new NotFoundException("博客不存在");

       }*/
        return "index";
    }

    @GetMapping("/blog")
    public String blog() {

        return "blog";
    }


}
