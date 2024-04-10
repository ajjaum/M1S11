package br.com.fullStack.education.M1S11.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cadernos")
public class Caderno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    private String nome;

}
