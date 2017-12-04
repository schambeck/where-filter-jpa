package com.schambeck.wherefilter.jpa.operator;

import com.schambeck.wherefilter.jpa.function.OperatorFunction;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import java.util.Arrays;
import java.util.List;

public enum Operator {

    EQUAL("=", (builder, path, value) -> builder.equal(path, value)),
    NOT_EQUAL("!=", (builder, path, value) -> builder.notEqual(path, value)),
    GREATER(">", (builder, path, value) -> builder.greaterThan((Path<Comparable>) path, (Comparable) value)),
    LESS(">", (builder, path, value) -> builder.lessThan((Path<Comparable>) path, (Comparable) value)),
    GREATER_OR_EQUAL(">", (builder, path, value) -> builder.greaterThanOrEqualTo((Path<Comparable>) path, (Comparable) value)),
    LESS_OR_EQUAL(">", (builder, path, value) -> builder.lessThanOrEqualTo((Path<Comparable>) path, (Comparable) value)),
    IN("in", (builder, path, value) -> path.in((List<Object>) value)), // TODO: 04/12/17 se não fizer cast pra list dá erro em runtime (why?)
    LIKE("like", (builder, path, value) -> builder.like((Expression<String>) path, (String) value)),
    ;

    private String operator;

    private OperatorFunction function;

    Operator(String operator, OperatorFunction function) {
        this.operator = operator;
        this.function = function;
    }

    public String getOperator() {
        return operator;
    }

    public OperatorFunction getFunction() {
        return function;
    }

    public static Operator fromString(String operatorStr) {
        return Arrays.stream(values())
                .filter(o -> operatorStr.equals(o.getOperator()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid operator: " + operatorStr));
    }

}
