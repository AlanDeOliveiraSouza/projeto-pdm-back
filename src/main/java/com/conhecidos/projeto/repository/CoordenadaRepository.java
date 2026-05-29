package com.conhecidos.projeto.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.conhecidos.projeto.database.MySQLConnection;
import com.conhecidos.projeto.model.Coordenada;

public class CoordenadaRepository {

    public static void cadastrarCoordenada(Coordenada coordenada) throws Exception {

        String sql = "INSERT INTO coordenada (id_conhecido, vl_latitude, vl_longitude, vl_altitude, vl_precisao) VALUES (?, ?, ?, ?, ?)";

        try(Connection conexao = MySQLConnection.conectar(); PreparedStatement pstmt = conexao.prepareStatement(sql);) {
            pstmt.setInt(1, coordenada.getIdConhecido());
            pstmt.setDouble(2, coordenada.getLatitude());
            pstmt.setDouble(3, coordenada.getLongitude());
            pstmt.setDouble(4, coordenada.getAltitude());
            pstmt.setDouble(5, coordenada.getPrecisao());

            pstmt.executeUpdate();
            System.out.println("Coordenada cadastrada");

        } catch(SQLException e) {
            System.out.println(e);
        }
    }

    public static Coordenada getCoordenadaPorId(Integer id) throws Exception {

        String sql = "SELECT * FROM coordenada WHERE id_coordenada = ?";
        
        Coordenada coordenada = new Coordenada();

        try(Connection conexao = MySQLConnection.conectar(); PreparedStatement pstmt = conexao.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                coordenada.setId(rs.getInt("id_coordenada"));
                coordenada.setIdConhecido(rs.getInt("id_conhecido"));
                coordenada.setLatitude(rs.getDouble("vl_latitude"));
                coordenada.setLongitude(rs.getDouble("vl_longitude"));
                coordenada.setAltitude(rs.getDouble("vl_altitude"));
                coordenada.setPrecisao(rs.getDouble("vl_precisao"));
            }
        } catch(SQLException e) {
            System.out.println(e);
        }

        System.out.println("Coordenada encontrada");

        return coordenada;
    }

    public static Coordenada getCoordenadaPorIdConhecido(Integer idConhecido) throws Exception {

        String sql = "SELECT * FROM coordenada WHERE id_conhecido = ?";
        
        Coordenada coordenada = new Coordenada();

        try(Connection conexao = MySQLConnection.conectar(); PreparedStatement pstmt = conexao.prepareStatement(sql);) {
            pstmt.setInt(1, idConhecido);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                coordenada.setId(rs.getInt("id_coordenada"));
                coordenada.setIdConhecido(rs.getInt("id_conhecido"));
                coordenada.setLatitude(rs.getDouble("vl_latitude"));
                coordenada.setLongitude(rs.getDouble("vl_longitude"));
                coordenada.setAltitude(rs.getDouble("vl_altitude"));
                coordenada.setPrecisao(rs.getDouble("vl_precisao"));
            }
        } catch(SQLException e) {
            System.out.println(e);
        }

        System.out.println("Coordenada encontrada por ID Conhecido");

        return coordenada;
    }

    public static List<Coordenada> getTodasCoordenadas() throws Exception {

        String sql = "SELECT * FROM coordenada";
        
        List<Coordenada> coordenadas = new ArrayList<>();

        try(Connection conexao = MySQLConnection.conectar(); PreparedStatement pstmt = conexao.prepareStatement(sql);) {
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Coordenada coordenada = new Coordenada();

                coordenada.setId(rs.getInt("id_coordenada"));
                coordenada.setIdConhecido(rs.getInt("id_conhecido"));
                coordenada.setLatitude(rs.getDouble("vl_latitude"));
                coordenada.setLongitude(rs.getDouble("vl_longitude"));
                coordenada.setAltitude(rs.getDouble("vl_altitude"));
                coordenada.setPrecisao(rs.getDouble("vl_precisao"));

                coordenadas.add(coordenada);
            }
        } catch(SQLException e) {
            System.out.println(e);
        }

        System.out.println("Coordenadas encontradas");
        return coordenadas;
    }

    public static void atualizarCoordenada(Coordenada coordenada) throws Exception {

        String sql = "SELECT * FROM coordenada WHERE id_coordenada = ?";

        Coordenada coordenadaBanco = new Coordenada();

        try(Connection conexao = MySQLConnection.conectar(); PreparedStatement pstmt = conexao.prepareStatement(sql);) {
            pstmt.setInt(1, coordenada.getId());

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                coordenadaBanco.setId(rs.getInt("id_coordenada"));
                coordenadaBanco.setIdConhecido(rs.getInt("id_conhecido"));
                coordenadaBanco.setLatitude(rs.getDouble("vl_latitude"));
                coordenadaBanco.setLongitude(rs.getDouble("vl_longitude"));
                coordenadaBanco.setAltitude(rs.getDouble("vl_altitude"));
                coordenadaBanco.setPrecisao(rs.getDouble("vl_precisao"));
            }
        } catch(SQLException e) {
            System.out.println(e);
        }

        // Lógica para comparar dados do banco com os atualizados
        boolean[] comparacao = Coordenada.compararCoordenada(coordenada, coordenadaBanco);

        int qtde = 0; // Variável para contar quantas ocorrencias são iguais, também é usada para contar quantos valores faltam no comando sql
        for(int i = 0; i<5; i++) {
            if(comparacao[i]) {qtde++;}
        }
        if(qtde == 5) {
            // Significa que todos os dados são iguais
            System.out.println("Os dados não foram alterados. Todos os dados são iguais.");
            return;
        }

        String[] tabela = {"id_conhecido", "vl_latitude", "vl_longitude", "vl_altitude", "vl_precisao"};

        sql = "UPDATE coordenada SET ";
        for(int i = 0; i<5; i++) {
            if(i > 0 && qtde < 5) {
                // Se o item anterior for inserido no comando, seu nome será anulado
                if(tabela[i-1].equals("")) {
                    sql += ", ";
                }
            }
            if(!comparacao[i]) {
                sql += tabela[i];
                sql += " = ?";
                tabela[i] = "";
                qtde++;
            }
        }
        sql += " WHERE id_coordenada = ?";
        // A lógica acima verifica quais itens do conhecido foram atualizados e insere-os no comando sql de atualização

        try(Connection conexao = MySQLConnection.conectar(); PreparedStatement pstmt = conexao.prepareStatement(sql);) {
            int i = 1; // Conta os parâmetros a serem alterados no banco
            for(int j = 0; j<5; j++) {
                if(!comparacao[j]) {
                    switch(j) {
                        case 0 -> pstmt.setInt(i, coordenada.getIdConhecido());
                        case 1 -> pstmt.setDouble(i, coordenada.getLatitude());
                        case 2 -> pstmt.setDouble(i, coordenada.getLongitude());
                        case 3 -> pstmt.setDouble(i, coordenada.getAltitude());
                        case 4 -> pstmt.setDouble(i, coordenada.getPrecisao());
                        default -> System.out.println("Opção inválida!");
                    }
                    i++;
                }
            }
            pstmt.setInt(i, coordenada.getId());
            
            pstmt.executeUpdate();
            System.out.println("Coordenada atualizada!");
        } catch(SQLException e) {
            System.out.println(e);
        }
    }

    public static void deletarCoordenada(Integer id) throws Exception {
        String sql = "DELETE FROM coordenada WHERE id_coordenada = ?";

        try(Connection conexao = MySQLConnection.conectar(); PreparedStatement pstmt = conexao.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Coordenada deletada!");
        } catch(SQLException e) {
            System.out.println(e);
        }
    }

}
