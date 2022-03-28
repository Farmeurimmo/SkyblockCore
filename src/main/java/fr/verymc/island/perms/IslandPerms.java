package main.java.fr.verymc.island.perms;

public enum IslandPerms {

    PROMOTE("Promouvoir un membre inférieur à lui"),
    DEMOTE("Rétrograder un membre inférieur à lui"),
    INVITE("Inviter un membre"),
    CANCEL_INVITE("Annuler l'invitation d'un membre"),
    KICK("Expulser un membre inférieur à lui"),
    BAN("Bannir un visiteur"),
    UNBAN("Débannir un visiteur"),
    SET_ISLAND_NAME("Définir le nom de l'île"),
    SET_ISLAND_WARP("Définir le warp de l'île"),
    PRIVATE("Définir la visibilité de l'île sur privée"),
    PUBLIC("Définir la visibilité de l'île sur publique"),
    CHANGE_BORDER_COLOR("Changer la couleur de la bordure"),
    CHANGE_ISLAND_BIOME("Changer le biome de l'île");

    private String description;

    IslandPerms(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
