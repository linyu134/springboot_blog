package com.lrm.service;

import com.lrm.NotFoundException;
import com.lrm.dao.TypeRepository;
import com.lrm.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TypeServiceImpl implements TypeService {

    //注入TypeRepository
    @Autowired
    private TypeRepository typeRepository;

    //Transactional放入事务
    @Transactional
    @Override
    public Type saveType(Type type) {

        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {

        return typeRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {

        return typeRepository.findAll(pageable);
    }


    @Override
    public Type getTypeByName(String name) {

        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        //查询id
        Type t = typeRepository.findById(id).get();
        if(t == null){
            throw new NotFoundException("不存在该分类");
        }
        //把type的值给t
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {

        typeRepository.deleteById(id);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }
}
