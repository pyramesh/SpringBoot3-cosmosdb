package com.ramesh.cosmos.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.ramesh.cosmos.model.User;

public interface UserRepository extends CosmosRepository<User, String> {

}
