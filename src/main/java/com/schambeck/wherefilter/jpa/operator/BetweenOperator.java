package com.schambeck.wherefilter.jpa.operator;

import com.schambeck.wherefilter.jpa.function.BetweenOperatorFunction;

public enum BetweenOperator {

    BETWEEN((builder, expression, value1, value2) -> builder.between(expression, value1, value2));

    private BetweenOperatorFunction function;

    BetweenOperator(BetweenOperatorFunction function) {
        this.function = function;
    }

    public BetweenOperatorFunction getFunction() {
        return function;
    }

}
