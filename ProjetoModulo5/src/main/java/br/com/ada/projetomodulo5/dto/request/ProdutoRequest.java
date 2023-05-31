package br.com.ada.projetomodulo5.dto.request;

import lombok.Data;

@Data
public class ProdutoRequest {

    private String uuid;
    private String nome;
    private Integer quantidade;

}
