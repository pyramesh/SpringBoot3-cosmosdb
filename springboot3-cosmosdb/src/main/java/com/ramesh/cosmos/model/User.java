package com.ramesh.cosmos.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Container(containerName = "users")
@Getter
@Setter
public class User {

    @Id
    private String id;
    @PartitionKey
    private String firstName;
    private String lastName;
    private String email;
}
