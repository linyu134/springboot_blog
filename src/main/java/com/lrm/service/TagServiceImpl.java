package com.lrm.service;

import com.lrm.NotFoundException;
import com.lrm.dao.TagRepository;
import com.lrm.po.Tag;
import com.lrm.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements  TagService {

    //注入TagRepository
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {

        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag(){
        return tagRepository.findAll();
    }


    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        //查询id
        Tag t = tagRepository.findById(id).get();
        if(t == null){
            throw new NotFoundException("不存在该标签");
        }
        //把type的值给t
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {

        tagRepository.deleteById(id);
    }


    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }

    //把字符串转化成数组
    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
}
