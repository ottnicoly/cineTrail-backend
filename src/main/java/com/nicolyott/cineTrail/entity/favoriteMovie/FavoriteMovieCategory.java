package com.nicolyott.cineTrail.entity.favoriteMovie;

public enum FavoriteMovieCategory {
    WATCHED("Ja assistido"),
    TO_WATCH("Assistir mais tarde"),
    GENERAL_FAVORITES("Favoritos gerais");

    private final String description;


    FavoriteMovieCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static FavoriteMovieCategory fromDescription(String description) {
        for (FavoriteMovieCategory category : FavoriteMovieCategory.values()) {
            if (category.getDescription().equalsIgnoreCase(description)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Categoria inválida: " + description);
    }

}
