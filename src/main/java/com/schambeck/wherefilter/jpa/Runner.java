package com.schambeck.wherefilter.jpa;

import com.schambeck.wherefilter.parser.WhereParser;
import org.parboiled.Parboiled;

import javax.persistence.criteria.Predicate;

public class Runner<T> {

    private WhereBuilder<T> builder;

    public Runner(WhereBuilder<T> builder) {
        this.builder = builder;
    }

    public Predicate[] run() {
        WhereParser parser = Parboiled.createParser(WhereParser.class);
        parser.accept(builder);
        return builder.getPredicates().toArray(new Predicate[]{});
    }

}
