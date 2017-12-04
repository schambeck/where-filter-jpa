package com.schambeck.wherefilter.jpa;

import com.schambeck.wherefilter.jpa.operator.BaseOperator;
import com.schambeck.wherefilter.parser.Visitor;
import com.schambeck.wherefilter.parser.WhereParser;
import org.parboiled.Node;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class WhereBuilder<T> implements Visitor {

    private CriteriaBuilder builder;

    private Root<T> from;

    private Class<T> tClass;

    private String where;

    private List<Predicate> predicates = new ArrayList<>();

    private ParsingResult<?> result;

    private WhereBuilder(String where) {
        this.where = where;
    }

    private WhereBuilder(CriteriaBuilder builder, Root<T> from, Class<T> tClass, String where) {
        this.builder = builder;
        this.from = from;
        this.tClass = tClass;
        this.where = where;
    }

    public static <T> WhereBuilder<T> create(CriteriaBuilder builder, Root<T> root, Class<T> tClass, String where) {
        return new WhereBuilder<>(builder, root, tClass, where);
    }

    static <T> WhereBuilder<T> create(String where) {
        return new WhereBuilder<>(where);
    }

    private void analyzeTree(Node node) {
        analyzeNode(node);
        for (Object object : node.getChildren()) {
            Node child = (Node) object;
            if (!child.getChildren().isEmpty()) {
                analyzeTree(child);
            }
        }
    }

    private void analyzeNode(Node node) {
        BaseOperator operator = BaseOperator.fromString(node.getLabel());
        if (operator != null) {
            Predicate predicate = operator.getFunction().get(builder, from, node, where, tClass);
            predicates.add(predicate);
        }
    }

    @Override
    public void visit(WhereParser parser) {
        predicates.clear();
        result = new ReportingParseRunner(parser.Expression()).run(where);
        analyzeTree(result.parseTreeRoot);
    }

    public List<Predicate> getPredicates() {
        return predicates;
    }

    public ParsingResult<?> getResult() {
        return result;
    }

}
