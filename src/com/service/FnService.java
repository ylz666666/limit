package com.service;

import com.domain.Fn;

import java.util.List;

public interface FnService {
    List<Fn> findAll();
    void addOne(Fn fn);
    void fnDelete(Integer fno);
    void fnUpdate(Fn fn);
}
