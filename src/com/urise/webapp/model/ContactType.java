package com.urise.webapp.model;

public enum ContactType {
    TELEPHONE("Телефон") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + " : " + toLink(value, value);
        }
    },
    SKYPE("Скайп") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + " : " + toLink(value, value);
        }
    },
    EMAIL("Почта") {
        @Override
        public String toHtml0(String value) {
            return toLink(value, value);
        }
    },
    LINKED_IN("Социальная сеть") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    GIT_HAB("Репозиторий") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    STACK_OVER_FLOW("САЙТ") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    HOMEPAGE("Домашняя страница") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    };
    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml0(String value) {
        return value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public String toLink(String href) {
        return toLink(href, title);
    }

    public static String toLink(String href, String value) {
        return "<a href='" + href + "'>" + value + "</a>";
    }
}