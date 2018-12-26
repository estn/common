package com.argyranthemum.common.jpa.condition;

public enum Operation {

        EQUALS("="),

        NOT_EQUALS("<>"),

        GREATER_THAN(">"),

        LESS_THAN("<"),

        GREATER_THAN_EQUALS(">="),

        LESS_THAN_EQUALS("<="),

        IN("in"),

        IS_NULL("is null"),

        IS_NOT_NULL("is not null"),

        NOT_IN("not in"),

        LEFT_LIKE("like"),

        RIGHT_LIKE("like"),

        ALL_LIKE("like"),

        IS("is");

        private String value;

        Operation(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }