package com.schambeck.wherefilter.jpa.function;

import org.parboiled.Node;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@FunctionalInterface
public interface BaseOperatorFunction {

    Predicate get(CriteriaBuilder builder, Root<?> from, Node node, String where, Class<?> tClass);

}
