package org.example.hirehub.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String pictureUrl;
    private String sex;
    private String address;

    @Transient
    private MultipartFile pictureFile;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = true)
    private AccountModel user;


    public int getAge() {
        if (dateOfBirth != null) {
            return Period.between(dateOfBirth, LocalDate.now()).getYears();
        }
        return 0;
    }
}
