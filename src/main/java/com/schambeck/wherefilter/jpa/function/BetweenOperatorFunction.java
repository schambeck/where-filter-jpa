package com.schambeck.wherefilter.jpa.function;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface BetweenOperatorFunction {

    Predicate get(CriteriaBuilder builder, Path<Comparable> expression, Comparable value1, Comparable value2);

}
