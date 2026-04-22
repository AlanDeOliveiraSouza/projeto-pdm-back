package com.conhecidos.projeto.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {

    // URL de caminho e acesso ao banco de dados
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DBNAME = "projeto_pdm";

    public static void criarBancoDeDados() {
        // Cria/verifica banco de dados

        // Script de criação do banco de dados
        String criarBanco = "CREATE DATABASE IF NOT EXISTS " + DBNAME + ";";

        try {
            Connection c = DriverManager.getConnection(URL, "root", null); 
            Statement stmt = c.createStatement();

            stmt.execute(criarBanco);
            System.out.println("Banco de Dados '" + DBNAME + "' criado/verificado com sucesso!");
        } catch(SQLException e) {
            System.out.println("Erro ao criar/verificar banco de dados: " + e.getMessage());
        } 

        criarTabelas();

    }

    public static void criarTabelas() {
        // Cria/verifica tabelas e dados do banco de dados

        // CRIAÇÃO DE TABELAS

        // Script de criação da tabela CONHECIDO
        String tabelaConhecido = 
        "CREATE TABLE IF NOT EXISTS conhecido (" + 
            "id_conhecido INT NOT NULL AUTO_INCREMENT, " + 
            "nm_conhecido VARCHAR(100) NOT NULL, " +  
            "qt_idade INT, " + 
            "dt_conheceu DATE, " + 
            "qt_anos_conhece INT, " + 
            "ds_ocasiao VARCHAR(250), " +
            "nm_genero VARCHAR(10), " +
            "PRIMARY KEY (id_conhecido)" +
        ");";

        // INSERÇÃO DE DADOS

        // Inserindo dados na tabela  CONHECIDO
        String insercaoConhecido = 
        "INSERT IGNORE conhecido (id_conhecido, nm_conhecido, qt_idade, dt_conheceu, qt_anos_conhece, ds_ocasiao, nm_genero) VALUES " +
        "(1, 'Juliano Silva', 20, '2024-02-10', 3, 'Na faculdade', 'Masculino'), " + 
        "(2, 'Carol Menezes', 19, '2024-02-10', 3, 'Na faculdade', 'Feminino'), " + 
        "(3, 'Fernanda Correa', 22, '2024-02-10', 3, 'Num restaurante', 'Feminino'), " + 
        "(4, 'Leonardo Souza', 25, '2024-02-10', 3, 'No trabalho', 'Masculino'), " + 
        "(5, 'Ernesto Ribeiro', 23, '2024-02-10', 3, 'Na faculdade', 'Masculino')";

        try(Connection conexao = conectar(); Statement stmt = conexao.createStatement();) {
            // Executa os scripts no banco de dados

            stmt.execute(tabelaConhecido);
            stmt.execute(insercaoConhecido);

            System.out.println("Tabelas criadas/verificadas com sucesso!");
        } catch(Exception e) {
            System.out.println("Erro ao criar/verificar banco de dados: " + e.getMessage());
        }

    }

    public static Connection conectar() throws Exception {
        // Inicia conexão ao banco de dados

        //                                       local, usuario, senha
        return DriverManager.getConnection(URL + DBNAME, "root", null);
    }
    
    
}