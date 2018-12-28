package pl.solidlabs.salaries.data;

import lombok.Getter;

public enum ContractType {

    UOP("Umowa o pracę"),
    UZ("Umowa zlecenie"),
    UOD("Umowa o dzieło"),
    B2B("B2B");

    @Getter
    private String name;

    ContractType(String name) {
        this.name = name;
    }
}
