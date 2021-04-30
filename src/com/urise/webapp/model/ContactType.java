package com.urise.webapp.model;

public enum ContactType {
    TELEPHONE("Телефон"),
    SKYPE("Скайп"),
    EMAIL("Почта"),
    LINKED_IN("Социальная сеть"),
    GIT_HAB("Репозиторий"),
    STACK_OVER_FLOW("САЙТ"),
    HOMEPAGE("Домашняя страница");

    ContactType(String title) {
    }

    public String getTitle(String title) {
        return title;
    }
}