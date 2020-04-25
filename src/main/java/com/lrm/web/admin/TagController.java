package com.lrm.web.admin;

import com.lrm.po.Tag;
import com.lrm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    //注入service
    @Autowired
    private TagService tagService;

    //pageable表示分页,size表示大小,sort = {"id"}表示根据id排序，DESC表示安装倒叙的方式排序
    @GetMapping("/tags")
    public String types(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        //分类为空的信息
        model.addAttribute("tag",new Tag());

        return "admin/tags-input";
    }

    //查到要修改的记录,根据id查到type
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }

    //新增
    //RedirectAttributes表示校验
    @PostMapping("/tags")
    public String post(@Valid Tag tag,BindingResult result, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        //type1 !=null表示数据库里面存在新增的type1的名字
        if(tag1 !=null){
            //验证
            result.rejectValue("name","nameError","不能添加重复的标签");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if(t == null){
            //新增失败
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/tags";
    }

    //更新
    @PostMapping("/tags/{id}")
    public String post(@Valid Tag tag, BindingResult result, @PathVariable  Long id, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        //tag1 !=null表示数据库里面存在新增的tag1的名字
        if(tag1 !=null){
            //验证
            result.rejectValue("name","nameError","不能添加重复的标签");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id,tag);
        if(t == null){
            //新增失败
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags";
    }


    //删除
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }
}
