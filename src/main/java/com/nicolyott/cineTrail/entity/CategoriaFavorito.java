package com.nicolyott.cineTrail.entity;

public enum CategoriaFavorito {
    JA_ASSISTIDOS("Ja assistido"),
    PARA_ASSISTIR("Assistir mais tarde"),
    FAVORITOS_GERAIS("Favoritos gerais");

    private final String descricao;


    CategoriaFavorito(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static CategoriaFavorito fromDescricao(String descricao) {
        for (CategoriaFavorito categoria : CategoriaFavorito.values()) {
            if (categoria.getDescricao().equalsIgnoreCase(descricao)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Categoria inválida: " + descricao);
    }

}
