package com.s4.conventionalcommits;

public record CommitType(String type, String description) {

    public String toString() {
        return this.type + " - " + this.description;
    }

    public String getType() {
        return this.type;
    }
}
