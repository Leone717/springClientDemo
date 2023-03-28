package com.sebok.springClientDemo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Nev")
    private String name;

    @Column(name = "Szuletesnap")
    private LocalDate birthDate;

    public Client() {
    }

    public Client(String name) {
        this.name = name;
    }

    public Client(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
