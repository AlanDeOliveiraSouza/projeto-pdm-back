# Projeto PDM - Conhecidos - API-REST Backend
Este repositório contém o backend do projeto **Conhecidos**, o qual funciona como uma lista de pessoas que o usuário conhece.
## 🚀 Tecnologias Utilizadas
 * **Java 17+**
 * **Javalin**: Framework leve para o desenvolvimento de APIs.
 * **MySQL**: Banco de dados relacional.
 * **Maven**: Gerenciamento de dependências.
## 🛠️ Pré-requisitos
Antes de começar, você precisará ter instalado em sua máquina:
 * JDK 17 ou superior.
 * XAMPP (para rodar o MySQL).
 * Uma IDE de sua preferência (IntelliJ IDEA, VS Code ou Eclipse).
## ⚙️ Configuração do Banco de Dados (XAMPP)
 1. Abra o **XAMPP Control Panel**.
 2. Inicie o módulo **MySQL**.
> **Nota:** A própria API faz a criação/validação do banco de dados.
> 
## 🏃 Como Executar a Aplicação
 1. Clone este repositório:
   ```bash
   git clone https://github.com/AlanDeOliveiraSouza/projeto-pdm-back.git
   
   ```
 2. Na IDE, compile e execute o projeto
 3. A API estará disponível em http://localhost:7070 (ou a porta configurada no Javalin).
## 📌 Endpoints
| Método | Endpoint | Descrição |
|---|---|---|
| **GET** | /conhecidos | Retorna todos os conhecidos. |
| **GET** | /conhecidos/{id} | Retorna um conhecido pelo id. |
| **POST** | /conhecidos/cadastrar | Cadastra um novo conhecido. |
| **PUT** | /conhecidos/ | Atualiza os dados de um conhecido. |
| **DELETE** | /conhecidos/{id} | Exclui um conhecido. |
### 📂 Estrutura de Pastas
```text
src/
 ├── main/
 │    ├── java/com/conhecidos/projeto/
 │    │    ├── database/    # Conexão com MySQL
 │    │    ├── model/       # Classes
 │    │    ├── repository/  # Acesso aos dados 
 │    │    └── Main.java    # Inicialização do Javalin

```
