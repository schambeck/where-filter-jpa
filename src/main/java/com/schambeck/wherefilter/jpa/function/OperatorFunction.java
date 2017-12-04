package com.schambeck.wherefilter.jpa.function;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface OperatorFunction {

    Predicate get(CriteriaBuilder builder, Path<?> path, Object value);

}
