package com.urise.webapp.model;

public enum ContactType {
    TELEPHONE("Телефон"),
    SKYPE("Скайп"),
    EMAIL("Почта"),
    LINKED_IN("Социальная сеть"),
    GIT_HAB("Репозиторий"),
    STACK_OVER_FLOW("САЙТ"),
    HOMEPAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public  String  getTitle() {
        return title;
    }
}