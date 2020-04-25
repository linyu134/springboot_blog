package com.lrm.service;

import com.lrm.po.Tag;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TagService {
    //新增
    Tag saveTag(Tag tag);
    //查询
    Tag getTag(Long id);
    //分页查询
    Page<Tag> listTag(Pageable pageable);
    //修改对象
    Tag updateTag(Long id,Tag tag);
    //删除
    void deleteTag(Long id);

    Tag getTagByName(String name);

    List<Tag> listTag();

    List<Tag> listTag(String ids);
}