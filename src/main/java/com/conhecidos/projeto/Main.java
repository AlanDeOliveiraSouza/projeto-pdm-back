package com.conhecidos.projeto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.conhecidos.projeto.database.MySQLConnection;
import com.conhecidos.projeto.model.Conhecido;
import com.conhecidos.projeto.repository.ConhecidoRepository;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {

        // Criando banco de dados (caso ele não exista)
        MySQLConnection.criarBancoDeDados();

        // Iniciando aplicação
        Javalin app = Javalin.create().start(7070);

        try {
            Conhecido c = new Conhecido(6, "Ricardo Silva", 26, LocalDate.parse("2026-04-21"), 0, "Em um casamento", "Masculino");
            ConhecidoRepository.atualizarConhecido(c);
        } catch(Exception e) {
            e.printStackTrace();
        }
        // ENDPOINTS:

        // Endpoint da rota (raiz) "/api"
        app.get("/api", ctx -> {
            ctx.json(Map.of("message","API executando com sucesso!"));
        });

        // Endpoint para buscar todos os conhecidos
        app.get("/api/conhecidos", ctx -> {
            try {
                List<Conhecido> conhecidos = ConhecidoRepository.getTodosConhecidos();
                ctx.json(conhecidos);
                //ctx.json(Map.of("success", true));
            } catch(Exception e) {
                ctx.json(Map.of("success", false));
                System.out.println(e);
            }
        });

        // Endpoint para buscar conhecido por id
        app.get("/api/conhecidos/{id}", ctx -> {
            try {
                Integer id = Integer.valueOf(ctx.pathParam("id"));
                Conhecido conhecido = ConhecidoRepository.getConhecidoPorId(id);
                if(conhecido.getId() != null) {
                    ctx.json(conhecido);
                    //ctx.json(Map.of("success", true));
                } else {
                    ctx.json(Map.of("success", false));
                    System.out.println("Conhecido não encontrado");
                }
            } catch(NumberFormatException e) {
                System.out.println("Valor de id inválido: " + e);
                ctx.json(Map.of("success", false));
            } catch(Exception e) {
                ctx.json(Map.of("success", false));
                System.out.println(e);
            }
        });

        // Endpoint para cadastrar novo conhecido
        app.post("/api/conhecidos/cadastrar", ctx -> {
            try {
                Conhecido conhecido = ctx.bodyAsClass(Conhecido.class);
                ConhecidoRepository.cadastrarConhecido(conhecido);
                ctx.json(conhecido);
                //ctx.json(Map.of("success", true));
            } catch(Exception e) {
                ctx.json(Map.of("success", false));
                System.out.println(e);
            }
        });

        // Endpoint para atualizar conhecido
        app.put("/api/conhecidos", ctx -> {
            try {
                Conhecido conhecido = ctx.bodyAsClass(Conhecido.class);
                ConhecidoRepository.atualizarConhecido(conhecido);
                ctx.json(conhecido);
                //ctx.json(Map.of("success", true));
            } catch(Exception e) {
                ctx.json(Map.of("success", false));
                e.printStackTrace();
                System.out.println(e);
            }
        });

        // Endpoint para excluir conhecido
        app.delete("/api/conhecidos/{id}", ctx -> {
            try{
                Integer id = Integer.valueOf(ctx.pathParam("id"));
                ConhecidoRepository.deletarConhecido(id);
                //ctx.json(Map.of("success", true));
            } catch(NumberFormatException e) {
                System.out.println("Valor de id inválido: " + e);
                ctx.json(Map.of("success", false));
            } catch(Exception e) {
                ctx.json(Map.of("success", false));
                System.out.println(e);
            }
        });
    }
}