package com.estudo.estruturadados.lista;

import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

class Node<T> {
    private T value;
    private Node<T> before;
    private Node<T> after;

    public Node(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getBefore() {
        return before;
    }

    public void setBefore(Node<T> otherNode) {
        this.before = otherNode;

        if (this.before != null) {
            otherNode.after = this;
        }
    }

    public Node<T> getAfter() {
        return after;
    }

    public void setAfter(Node<T> otherNode) {
        this.after = otherNode;

        if (this.after != null) {
            otherNode.before = this;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, JSON_STYLE)
                .append("value", value)
                .append("before", before != null ? before.getValue() : null)
                .append("after", after != null ? after.getValue() : null)
                .toString();
    }
}
