package com.estudo.estruturadados;

import com.estudo.estruturadados.lista.ListaDuplamanenteEncadeada;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

        listaDuplamanenteEncadeada.forEach(System.out::println);

        listaDuplamanenteEncadeada.tryPrevious().ifPresent(System.out::println);
        listaDuplamanenteEncadeada.tryPrevious().ifPresent(System.out::println);
        listaDuplamanenteEncadeada.tryAdvance().ifPresent(System.out::println);
        listaDuplamanenteEncadeada.tryAdvance().ifPresent(System.out::println);
    }
}
