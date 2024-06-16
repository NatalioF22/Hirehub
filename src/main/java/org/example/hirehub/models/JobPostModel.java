package org.example.hirehub.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Getter
@Setter
@AllArgsConstructor
@Entity
@Scope("prototype")
public class JobPostModel {
    private static int nextId = 1;

    @Id
    private int id;
    private String name;
    private String description;
    private String jobProfile;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccountModel user;

    private String location;

    @Enumerated(EnumType.STRING)
    private JobLocation jobLocation;

    @Enumerated(EnumType.STRING)
    private JobLevel jobLevel;

    private String techStacks;

    private BigDecimal salary;

    public JobPostModel() {
        System.out.println(this.getClass().getSimpleName() + " class called!");
        this.id = nextId++;
    }
}