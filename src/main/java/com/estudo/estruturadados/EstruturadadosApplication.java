package com.estudo.estruturadados;

import com.estudo.estruturadados.lista.ListaDuplamanenteEncadeada;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Spliterator;

@SpringBootApplication
public class EstruturadadosApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(EstruturadadosApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ListaDuplamanenteEncadeada<Integer> listaDuplamanenteEncadeada = new ListaDuplamanenteEncadeada<>();
        listaDuplamanenteEncadeada.adicionarNoInicioDaLista(1);
        listaDuplamanenteEncadeada.adicionarNoInicioDaLista(3);
        listaDuplamanenteEncadeada.adicionarNoInicioDaLista(9);
        listaDuplamanenteEncadeada.adicionarNoInicioDaLista(15);

        for(Integer numero : listaDuplamanenteEncadeada) {
            System.out.println(numero);
        }

        System.out.println("----------------------------------------------------------------------------");

        listaDuplamanenteEncadeada.forEach(System.out::println);

        System.out.println("----------------------------------------------------------------------------");
        Spliterator<Integer> spliterator = listaDuplamanenteEncadeada.spliterator().trySplit();

        spliterator.trySplit().forEachRemaining(System.out::println);

        System.out.println(listaDuplamanenteEncadeada);
    }
}
