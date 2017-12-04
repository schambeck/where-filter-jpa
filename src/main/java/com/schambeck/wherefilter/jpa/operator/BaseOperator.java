package com.schambeck.wherefilter.jpa.operator;

import com.schambeck.wherefilter.jpa.function.BaseOperatorFunction;
import org.parboiled.Node;

import javax.persistence.criteria.Path;
import java.util.Arrays;
import java.util.List;

import static com.schambeck.wherefilter.jpa.utils.JPAUtils.getPath;
import static com.schambeck.wherefilter.parser.utils.NodeUtils.*;

public enum BaseOperator {

    SIMPLE_OPERATOR("SimpleOperator", (builder, from, node, where, tClass) -> {
        Node attributePathNode = (Node) node.getChildren().get(0);
        Node operatorNode = (Node) node.getChildren().get(1);
        Node valueNode = (Node) node.getChildren().get(2);
        String attributePath = getValueExpression(attributePathNode, where).trim(); // TODO: 28/11/17 recuperar o valor sem precisar trim()
        String operatorStr = getValueExpression(operatorNode, where).trim();
        String valueStr = getValueExpression(valueNode, where);
        Path<?> path = getPath(from, attributePath);
        Operator operator = Operator.fromString(operatorStr);
        return operator.getFunction().get(builder, path, valueStr);
    }),
    IN("In", (builder, from, node, where, tClass) -> {
        Node attributePath = (Node) node.getChildren().get(0);
        Node valuesNode = (Node) node.getChildren().get(2);
        Node itemsNode = (Node) valuesNode.getChildren().get(1);
        String attributePathStr = getValueExpression(attributePath, where).trim();
        List<Object> values = getValues(attributePathStr, itemsNode, where, tClass);
        Path<?> path = getPath(from, attributePathStr);
        Operator in = Operator.IN;
        return in.getFunction().get(builder, path, values);
    }),
    BETWEEN("Between", (builder, from, node, where, tClass) -> {
        Node attributePath = (Node) node.getChildren().get(0);
        Node rangeNode = (Node) node.getChildren().get(2);
        Node value1Node = (Node) rangeNode.getChildren().get(0);
        Node value2Node = (Node) rangeNode.getChildren().get(2);
        String attributePathStr = getValueExpression(attributePath, where).trim();
        String value1Str = getValueExpression(value1Node, where);
        String value2Str = getValueExpression(value2Node, where);
        Object value1 = getValue(attributePathStr, value1Str, tClass);
        Object value2 = getValue(attributePathStr, value2Str, tClass);
        Path<?> path = getPath(from, attributePathStr);
        BetweenOperator between = BetweenOperator.BETWEEN;
        return between.getFunction().get(builder, (Path<Comparable>) path, (Comparable) value1, (Comparable) value2);
    }),
    LIKE("Like", (builder, from, node, where, tClass) -> {
        Node attributePath = (Node) node.getChildren().get(0);
        Node valueNode = (Node) node.getChildren().get(2);
        String attributePathStr = getValueExpression(attributePath, where).trim();
        String valueStr = getValueExpression(valueNode, where);
        String value = (String) getValue(attributePathStr, valueStr, tClass);
        Path<String> path = (Path<String>) getPath(from, attributePathStr);
        Operator like = Operator.LIKE;
        return like.getFunction().get(builder, path, value);
    }),;

    private String label;

    private BaseOperatorFunction function;

    BaseOperator(String label, BaseOperatorFunction function) {
        this.label = label;
        this.function = function;
    }

    public String getLabel() {
        return label;
    }

    public BaseOperatorFunction getFunction() {
        return function;
    }

    public static BaseOperator fromString(String operatorStr) {
        return Arrays.stream(values())
                .filter(o -> operatorStr.equals(o.getLabel()))
                .findFirst()
                .orElse(null);
    }

    public static BaseOperator fromStringOrThrow(String operatorStr) {
        return Arrays.stream(values())
                .filter(o -> operatorStr.equals(o.getLabel()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid label: " + operatorStr));
    }

}
