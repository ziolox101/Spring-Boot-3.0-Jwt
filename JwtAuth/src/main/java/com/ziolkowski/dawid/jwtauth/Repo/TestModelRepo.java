package com.ziolkowski.dawid.jwtauth.Repo;

import com.ziolkowski.dawid.jwtauth.Model.TestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestModelRepo extends JpaRepository<TestModel, Long> {
}
