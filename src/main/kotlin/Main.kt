import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

fun main(args: Array<String>) {


        val dbUrl = "jdbc:mysql://localhost:3306/world"
        val dbUser = "root"
        val dbPassword = "M@rujo023"

        try {
            val connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)
            val statement = connection.createStatement()

            println("****CALENDÁRIO DE VIAGENS****")
            while (true) {
                println("O que você deseja fazer?")
                println("1. CADASTRAR uma nova cidade de destino")
                println("2. ATUALIZAR uma cidade de destino")
                println("3. DELETAR uma cidade de destino")
                println("4. VER OS DETALHES de uma cidade de destino")
                println("5. Sair")

                when (readOption()) {
                    1 -> cadastrarCidade(connection)
                    2 -> atualizarCidade(connection)
                    3 -> deletarCidade(connection)
                    4 -> verDetalhesDaCidade(connection)
                    5 -> {
                        statement.close()
                        connection.close()
                        break
                    }
                    else -> println("Opção inválida. Tente novamente.")
                }
            }

        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun readOption(): Int {
        return readLine()?.toIntOrNull() ?: -1
    }

    fun cadastrarCidade(connection: Connection) {

        var id = 10000
        println("Insira o nome da cidade:")
        val nome = readLine()
        println("Insira as 3 primeiras letras do país dessa cidade:")
        val codPais = readLine()
        println("Insira a população da cidade:")
        val populacao = readLine()

        if (nome != null) {
            val statement = connection.prepareStatement("INSERT INTO city (ID, NAME, countryCode, POPULATION) VALUES (?,?, ?, ?)")
            statement.setInt(1, id)
            id++
            statement.setString(2, nome)
            statement.setString(3, codPais)
            statement.setString(3, populacao)

            val rowCount = statement.executeUpdate()
            if (rowCount > 0) {
                println("Cidade cadastrada com sucesso!")

            } else {
                println("Erro ao cadastrar a cidade.")
            }
        } else {
            println("Entrada inválida.")
        }
    }

    fun atualizarCidade(connection: Connection) {
        println("Insira o nome da cidade que deseja atualizar:")
        val nomeCidade = readLine()
        
        println("Insira o novo nome da cidade:")
        val novoNome = readLine()

        if (nomeCidade != null && novoNome != null) {
            val statement = connection.prepareStatement("UPDATE city SET NAME = ? WHERE NAME = ?")
            statement.setString(1, novoNome)
            statement.setString(2, nomeCidade)

            val rowCount = statement.executeUpdate()
            if (rowCount > 0) {
                println("Cidade atualizada com sucesso!")
            } else {
                println("Erro ao atualizar a cidade. Verifique se o nome da cidade existe.")
            }
        } else {
            println("Entrada inválida.")
        }
    }

    fun deletarCidade(connection: Connection) {
        println("Insira o nome da cidade que deseja deletar:")
        val nomeCidade = readLine()

        if (nomeCidade != null) {
            val statement = connection.prepareStatement("DELETE FROM city WHERE NAME = ?")
            statement.setString(1, nomeCidade)

            val rowCount = statement.executeUpdate()
            if (rowCount > 0) {
                println("Cidade deletada com sucesso!")
            } else {
                println("Erro ao deletar a cidade. Verifique se o nome da cidade existe.")
            }
        } else {
            println("Entrada inválida.")
        }
    }

    fun verDetalhesDaCidade(connection: Connection) {
        println("Insira o nome da cidade que deseja ver os detalhes:")
        val nomeCidade = readLine()

        if (nomeCidade != null) {
            val statement = connection.prepareStatement("SELECT * FROM city WHERE NAME = ?")
            statement.setString(1, nomeCidade)

            val resultSet = statement.executeQuery()
            if (resultSet.next()) {
                val id = resultSet.getInt("ID")
                val nome = resultSet.getString("NAME")
                val populacao = resultSet.getString("population")
                println("Detalhes da cidade:")
                println("ID: $id")
                println("Nome: $nome")
                println("População: $populacao")
            } else {
                println("Cidade não encontrada.")
            }
        } else {
            println("Entrada inválida.")
        }
    }
