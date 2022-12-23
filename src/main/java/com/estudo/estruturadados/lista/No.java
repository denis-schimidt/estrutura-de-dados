package com.estudo.estruturadados.lista;

import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

class No<T> {
    private T elemento;
    private No<T> anterior;
    private No<T> posterior;

    public No(T elemento) {
        this.elemento = elemento;
    }

    public T getElemento() {
        return elemento;
    }

    public void setElemento(T elemento) {
        this.elemento = elemento;
    }

    public No<T> getAnterior() {
        return anterior;
    }

    public void setAnterior(No<T> noExterno) {
        this.anterior = noExterno;

        if (this.anterior != null) {
            this.anterior.posterior = this;
        }
    }

    public No<T> getPosterior() {
        return posterior;
    }

    public void setPosterior(No<T> noExterno) {
        this.posterior = noExterno;

        if (this.posterior != null) {
            this.posterior.anterior = this;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, JSON_STYLE)
                .append("elemento", elemento)
                .append("anterior", anterior != null ? anterior.getElemento() : null)
                .append("posterior", posterior != null ? posterior.getElemento() : null)
                .toString();
    }
}
