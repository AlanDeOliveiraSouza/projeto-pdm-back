package com.conhecidos.projeto.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.conhecidos.projeto.database.MySQLConnection;
import com.conhecidos.projeto.model.Conhecido;

public class ConhecidoRepository {

    public static void cadastrarConhecido(Conhecido conhecido) throws Exception {

        String sql = "INSERT INTO conhecido (nm_conhecido, qt_idade, dt_conheceu, qt_anos_conhece, ds_ocasiao, nm_genero) VALUES (?, ?, ?, ?, ?, ?)";

        try(Connection conexao = MySQLConnection.conectar(); PreparedStatement pstmt = conexao.prepareStatement(sql);) {
            pstmt.setString(1, conhecido.getNome());
            pstmt.setInt(2, conhecido.getIdade());
            pstmt.setString(3, conhecido.getDataConheceu().toString());
            pstmt.setInt(4, conhecido.getAnosConhece());
            pstmt.setString(5, conhecido.getOcasiao());
            pstmt.setString(6, conhecido.getGenero());

            pstmt.executeUpdate();
            System.out.println("Conhecido cadastrado");

        } catch(SQLException e) {
            System.out.println(e);
        }
    }

    public static Conhecido getConhecidoPorId(Integer id) throws Exception {

        String sql = "SELECT * FROM conhecido WHERE id_conhecido = ?";
        
        Conhecido conhecido = new Conhecido();

        try(Connection conexao = MySQLConnection.conectar(); PreparedStatement pstmt = conexao.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                conhecido.setId(rs.getInt("id_conhecido"));
                conhecido.setNome(rs.getString("nm_conhecido"));
                conhecido.setIdade(rs.getInt("qt_idade"));
                conhecido.setDataConheceu(LocalDate.parse(rs.getString("dt_conheceu")));
                conhecido.setAnosConhece(rs.getInt("qt_anos_conhece"));
                conhecido.setOcasiao(rs.getString("ds_ocasiao"));
                conhecido.setGenero(rs.getString("nm_genero"));
            }
        } catch(SQLException e) {
            System.out.println(e);
        }

        System.out.println("Conhecido encontrado");

        return conhecido;
    }

    public static List<Conhecido> getTodosConhecidos() throws Exception {

        String sql = "SELECT * FROM conhecido";
        
        List<Conhecido> conhecidos = new ArrayList<>();

        try(Connection conexao = MySQLConnection.conectar(); PreparedStatement pstmt = conexao.prepareStatement(sql);) {
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Conhecido conhecido = new Conhecido();

                conhecido.setId(rs.getInt("id_conhecido"));
                conhecido.setNome(rs.getString("nm_conhecido"));
                conhecido.setIdade(rs.getInt("qt_idade"));
                conhecido.setDataConheceu(LocalDate.parse(rs.getString("dt_conheceu")));
                conhecido.setAnosConhece(rs.getInt("qt_anos_conhece"));
                conhecido.setOcasiao(rs.getString("ds_ocasiao"));
                conhecido.setGenero(rs.getString("nm_genero"));

                conhecidos.add(conhecido);
            }
        } catch(SQLException e) {
            System.out.println(e);
        }

        System.out.println("Conhecidos encontrados");
        return conhecidos;
    }

    public static void atualizarConhecido(Conhecido conhecido) throws Exception {

        String sql = "SELECT * FROM conhecido WHERE id_conhecido = ?";

        Conhecido conhecidoBanco = new Conhecido();

        try(Connection conexao = MySQLConnection.conectar(); PreparedStatement pstmt = conexao.prepareStatement(sql);) {
            pstmt.setInt(1, conhecido.getId());

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                conhecidoBanco.setId(rs.getInt("id_conhecido"));
                conhecidoBanco.setNome(rs.getString("nm_conhecido"));
                conhecidoBanco.setIdade(rs.getInt("qt_idade"));
                conhecidoBanco.setDataConheceu(LocalDate.parse(rs.getString("dt_conheceu")));
                conhecidoBanco.setAnosConhece(rs.getInt("qt_anos_conhece"));
                conhecidoBanco.setOcasiao(rs.getString("ds_ocasiao"));
                conhecidoBanco.setGenero(rs.getString("nm_genero"));
            }
        } catch(SQLException e) {
            System.out.println(e);
        }

        // Lógica para comparar dados do banco com os atualizados
        boolean[] comparacao = Conhecido.compararConhecido(conhecido, conhecidoBanco);

        int qtde = 0; // Variável para contar quantas ocorrencias são iguais, também é usada para contar quantos valores faltam no comando sql
        for(int i = 0; i<6; i++) {
            if(comparacao[i]) {qtde++;}
        }
        if(qtde == 6) {
            // Significa que todos os dados são iguais
            System.out.println("Os dados não foram alterados. Todos os dados são iguais.");
            return;
        }

        String[] tabela = {"nm_conhecido", "qt_idade", "dt_conheceu", "qt_anos_conhece", "ds_ocasiao", "nm_genero"};

        sql = "UPDATE conhecido SET ";
        for(int i = 0; i<6; i++) {
            if(i > 0 && qtde < 6) {
                // Se o item anterior for inserido no comando, seu nome será anulado
                if(tabela[i-1].equals("")) {
                    sql += ", ";
                }
            }
            System.out.println(comparacao[i]);
            if(!comparacao[i]) {
                sql += tabela[i];
                sql += " = ?";
                tabela[i] = "";
                qtde++;
            }
        }
        sql += " WHERE id_conhecido = ?";
        // A lógica acima verifica quais itens do conhecido foram atualizados e insere-os no comando sql de atualização

        System.out.println(sql);

        try(Connection conexao = MySQLConnection.conectar(); PreparedStatement pstmt = conexao.prepareStatement(sql);) {
            int i = 1; // Conta os parâmetros a serem alterados no banco
            for(int j = 0; j<6; j++) {
                if(!comparacao[j]) {
                    switch(j) {
                        case 0 -> pstmt.setString(i, conhecido.getNome());
                        case 1 -> pstmt.setInt(i, conhecido.getIdade());
                        case 2 -> pstmt.setString(i, conhecido.getDataConheceu().toString());
                        case 3 -> pstmt.setInt(i, conhecido.getAnosConhece());
                        case 4 -> pstmt.setString(i, conhecido.getOcasiao());
                        case 5 -> pstmt.setString(i, conhecido.getGenero());
                        default -> System.out.println("Opção inválida!");
                    }
                    i++;
                }
            }
            pstmt.setInt(i, conhecido.getId());
            
            pstmt.executeUpdate();
            System.out.println("Conhecido atualizado!");
        } catch(SQLException e) {
            System.out.println(e);
        }
    }

    public static void deletarConhecido(Integer id) throws Exception {
        String sql = "DELETE FROM conhecido WHERE id_conhecido = ?";

        try(Connection conexao = MySQLConnection.conectar(); PreparedStatement pstmt = conexao.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Conhecido deletado!");
        } catch(SQLException e) {
            System.out.println(e);
        }
    }

}
