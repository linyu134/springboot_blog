package com.lrm.web.admin;

import com.lrm.po.Type;
import com.lrm.service.TypeService;
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
public class TypeController {

    //注入service
    @Autowired
    private TypeService typeService;

    //pageable表示分页,size表示大小,sort = {"id"}表示根据id排序，DESC表示安装倒叙的方式排序
    @GetMapping("/types")
    public String types(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        //分类为空的信息
        model.addAttribute("type",new Type());

        return "admin/types-input";
    }

    //查到要修改的记录,根据id查到type
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    //新增
    //RedirectAttributes表示校验
    @PostMapping("/types")
    public String post(@Valid Type type,BindingResult result, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        //type1 !=null表示数据库里面存在新增的type1的名字
        if(type1 !=null){
            //验证
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
       if(result.hasErrors()){
           return "admin/types-input";
       }
        Type t = typeService.saveType(type);
       if(t == null){
            //新增失败
           attributes.addFlashAttribute("message","新增失败");
       }else{
           attributes.addFlashAttribute("message","新增成功");
       }
       return "redirect:/admin/types";
    }

    //更新
    @PostMapping("/types/{id}")
    public String post(@Valid Type type, BindingResult result,@PathVariable  Long id, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        //type1 !=null表示数据库里面存在新增的type1的名字
        if(type1 !=null){
            //验证
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.updateType(id,type);
        if(t == null){
            //新增失败
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }


    //删除
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
