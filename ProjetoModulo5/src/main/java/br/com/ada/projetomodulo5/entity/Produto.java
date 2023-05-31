package br.com.ada.projetomodulo5.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer quantidade;

}
