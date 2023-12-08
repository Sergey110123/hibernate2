package ru.javarush.satvaldiev.entity;

import static java.util.Objects.isNull;

public enum SpecialFeature {

    TRAILERS("Trailers"),
    COMMENTARIES("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the Scenes");
    private final String value;
    SpecialFeature(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    public static SpecialFeature getSpecialFeatureByValue(String value) {
        if (isNull(value) || value.isEmpty()) {
            return null;
        }
        SpecialFeature[] specialFeatures = SpecialFeature.values();
        for (SpecialFeature sf : specialFeatures) {
            if (sf.value.equals(value)) {
                return sf;
            }
        }
        return null;
    }
}
