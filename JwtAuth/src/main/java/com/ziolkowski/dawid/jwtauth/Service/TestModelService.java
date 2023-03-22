package com.ziolkowski.dawid.jwtauth.Service;

import com.ziolkowski.dawid.jwtauth.Model.TestModel;
import com.ziolkowski.dawid.jwtauth.Repo.TestModelRepo;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TestModelService {
    private TestModelRepo repo;

    public List<TestModel> getAll(){
        return repo.findAll();
    }

    @PostConstruct
    public void init(){
        repo.saveAll(List.of(new TestModel("aa"), new TestModel("bb"), new TestModel("cc")));
    }
}
