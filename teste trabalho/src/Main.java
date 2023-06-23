import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static Connection connection = null;
    static Scanner ler = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        int linha;

        do {
            linha = exibirMenuPrincipal();
            System.out.println();

            switch (linha) {
                case 1:
                    menuGarcom();
                    break;
                case 2:
                    menuMesa();
                    break;
                case 0:
                    System.out.println("Finalizado");
                    break;
                default:
                    System.out.println("ERRO!");
                    break;
            }

            System.out.println();
        } while (linha != 0);
    }

    private static int exibirMenuPrincipal() {
        System.out.println("Menu Principal:");
        System.out.println("1) Gerenciar Garçons");
        System.out.println("2) Gerenciar Mesas");
        System.out.println("Digite 0 para Finalizar");

        return ler.nextInt();
    }

    private static void menuGarcom() throws Exception {
        int linha;

        do {
            linha = exibirMenuGarcom();
            System.out.println();

            switch (linha) {
                case 1:
                    inserirGarcom();
                    break;
                case 2:
                    removerGarcomPeloEmail();
                    break;
                case 3:
                    buscarGarcomPeloEmail();
                    break;
                case 4:
                    alterarGarcom();
                    break;
                case 5:
                    calcularMediaSalarioFixo();
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal");
                    break;
                default:
                    System.out.println("ERRO!");
                    break;
            }

            System.out.println();
        } while (linha != 0);
    }

    private static int exibirMenuGarcom() {
        System.out.println("Menu Garçom:");
        System.out.println("1) Inserir Garçom");
        System.out.println("2) Remover Garçom");
        System.out.println("3) Buscar Garçom");
        System.out.println("4) Alterar Garçom");
        System.out.println("5) Calcular Média do Salário Fixo");
        System.out.println("Digite 0 para Voltar ao Menu Principal");

        return ler.nextInt();
    }

    // Restante do código...

    private static void menuMesa() throws Exception {
        int linha;

        do {
            linha = exibirMenuMesa();
            System.out.println();

            switch (linha) {
                case 1:
                    inserirMesa();
                    break;
                case 2:
                    removerMesaPeloNumero();
                    break;
                case 3:
                    buscarMesaPeloNumero();
                    break;
                case 4:
                    buscarGarcomResponsavelPelaMesa();
                    break;
                case 5:
                    buscarMesasLivresComGarcomResponsavel();
                    break;
                case 6:
                    buscarMesasOcupadasPorGarcom();
                    break;
                case 7:
                    calcularQuantidadeMesasAtendidasPorGarcom();
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal");
                    break;
                default:
                    System.out.println("ERRO!");
                    break;
            }

            System.out.println();
        } while (linha != 0);
    }

    private static int exibirMenuMesa() {
        System.out.println("Menu Mesa:");
        System.out.println("1) Inserir Mesa");
        System.out.println("2) Remover Mesa");
        System.out.println("3) Buscar Mesa");
        System.out.println("4) Buscar Garçom Responsável pela Mesa");
        System.out.println("5) Buscar Mesas Livres com Garçom Responsável");
        System.out.println("6) Buscar Mesas Ocupadas por Garçom");
        System.out.println("7) Calcular Quantidade de Mesas Atendidas por Garçom");
        System.out.println("Digite 0 para Voltar ao Menu Principal");

        return ler.nextInt();
    }

    // CALCULAR MEDIA
    private static void calcularMediaSalarioFixo() throws SQLException {
        try {
            connection = ConexaoDB.getInstance();

            String sql = "SELECT AVG(salarioFixo) AS mediaSalarioFixo FROM garcom";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                double mediaSalarioFixo = resultado.getDouble("mediaSalarioFixo");
                System.out.println("Média de Salário: " + mediaSalarioFixo);
            }
            resultado.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Erro no cálculo da Média!");
            e.printStackTrace();
        }
    }

    // INSERIR GARÇOM
    private static void inserirGarcom() throws SQLException {
        try {
            connection = ConexaoDB.getInstance();
            String sql = "INSERT INTO garcom (id_garcom, nome, cpf, dataNascimento, email, telefone, sexo, salarioFixo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);

            System.out.println("Informe o ID do garçom: ");
            int id_garcom = ler.nextInt();
            ler.nextLine();
            System.out.println("Informe o Nome do garçom: ");
            String nome = ler.nextLine();
            System.out.println("Informe o CPF do garçom: ");
            String cpf = ler.nextLine();
            System.out.println("Informe a data de nascimento do garçom: ");
            String dataNascimento = ler.nextLine();
            System.out.println("Informe o email do garçom: ");
            String email = ler.nextLine();
            System.out.println("Informe o telefone do garçom: ");
            String telefone = ler.nextLine();
            System.out.println("Informe o sexo do garçom: ");
            String sexo = ler.nextLine();
            System.out.println("Informe o salário fixo do garçom: ");
            double salarioFixo = ler.nextDouble();

            stmt.setInt(1, id_garcom);
            stmt.setString(2, nome);
            stmt.setString(3, cpf);
            stmt.setString(4, dataNascimento);
            stmt.setString(5, email);
            stmt.setString(6, telefone);
            stmt.setString(7, sexo);
            stmt.setDouble(8, salarioFixo);
            stmt.executeUpdate();

            stmt.close();
            System.out.println("Garçom cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Não foi possível cadastrar o Garçom");
            e.printStackTrace();
        }
    }

    // REMOVER GARÇOM
    public static void removerGarcomPeloEmail() throws Exception {
        connection = ConexaoDB.getInstance();
        String sql = "DELETE FROM garcom WHERE email LIKE ?";

        System.out.println("Informe o email do garçom que deseja remover: ");
        String emailDoGarcomQueSeraRemovido = ler.nextLine();

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, emailDoGarcomQueSeraRemovido);

        stmt.execute();
        stmt.close();
    }

    // BUSCAR GARÇOM
    public static void buscarGarcomPeloEmail() throws Exception {
        connection = ConexaoDB.getInstance();

        String sql = "SELECT * FROM garcom WHERE email LIKE ?";

        System.out.println("Informe o email do garçom que deseja buscar: ");
        String emailBuscado = ler.nextLine();

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, emailBuscado);
        ResultSet resultado = stmt.executeQuery();

        if (resultado.next()) {
            int id_garcom = resultado.getInt("id_garcom");
            String nome = resultado.getString("nome");
            String cpf = resultado.getString("cpf");
            String dataNascimento = resultado.getString("dataNascimento");
            String email = resultado.getString("email");
            String telefone = resultado.getString("telefone");
            String sexo = resultado.getString("sexo");
            double salarioFixo = resultado.getDouble("salarioFixo");

            System.out.println("Garçom encontrado:");
            System.out.println("ID: " + id_garcom);
            System.out.println("Nome: " + nome);
            System.out.println("CPF: " + cpf);
            System.out.println("Data de Nascimento: " + dataNascimento);
            System.out.println("Email: " + email);
            System.out.println("Telefone: " + telefone);
            System.out.println("Sexo: " + sexo);
            System.out.println("Salário Fixo: " + salarioFixo);
        } else {
            System.out.println("Garçom não encontrado!");
        }

        resultado.close();
        stmt.close();
    }

    // ALTERAR GARÇOM
    public static void alterarGarcom() throws Exception {
        connection = ConexaoDB.getInstance();

        System.out.println("Digite o email do garçom que deseja alterar: ");
        String emailGarcomAlterar = ler.nextLine();

        String sql = "SELECT * FROM garcom WHERE email LIKE ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, emailGarcomAlterar);
        ResultSet resultado = stmt.executeQuery();

        if (resultado.next()) {
            int id_garcom = resultado.getInt("id_garcom");
            String nome = resultado.getString("nome");
            String cpf = resultado.getString("cpf");
            String dataNascimento = resultado.getString("dataNascimento");
            String email = resultado.getString("email");
            String telefone = resultado.getString("telefone");
            String sexo = resultado.getString("sexo");
            double salarioFixo = resultado.getDouble("salarioFixo");

            System.out.println("Informe o novo nome do garçom: ");
            nome = ler.nextLine();
            System.out.println("Informe o novo CPF do garçom: ");
            cpf = ler.nextLine();
            System.out.println("Informe a nova data de nascimento do garçom: ");
            dataNascimento = ler.nextLine();
            System.out.println("Informe o novo telefone do garçom: ");
            telefone = ler.nextLine();
            System.out.println("Informe o novo sexo do garçom: ");
            sexo = ler.nextLine();
            System.out.println("Informe o novo email:");
            email = ler.nextLine();
            System.out.println("Informe o novo salário fixo do garçom: ");
            salarioFixo = ler.nextDouble();

            sql = "UPDATE garcom SET nome = ?, cpf = ?, dataNascimento = ?, telefone = ?, sexo = ?, email = ?, salarioFixo = ? WHERE id_garcom = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, dataNascimento);
            stmt.setString(4, telefone);
            stmt.setString(5, sexo);
            stmt.setString(6, email);
            stmt.setDouble(7, salarioFixo);
            stmt.setInt(8, id_garcom);
            stmt.executeUpdate();

            System.out.println("Garçom alterado com sucesso!");
        } else {
            System.out.println("Garçom não encontrado!");
        }

        resultado.close();
        stmt.close();
    }

    // INSERIR MESA
    public static void inserirMesa() throws SQLException {
        try {
            connection = ConexaoDB.getInstance();
            String sql = "INSERT INTO mesa (numero_mesa, situacao, capacidade_maxima) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);

            System.out.println("Informe o número da mesa: ");
            int numeroMesa = ler.nextInt();
            ler.nextLine();
            System.out.println("Informe a situação da mesa (livre, ocupada, reservada): ");
            String situacao = ler.nextLine();
            System.out.println("Informe a capacidade máxima da mesa: ");
            int capacidadeMaxima = ler.nextInt();

            stmt.setInt(1, numeroMesa);
            stmt.setString(2, situacao);
            stmt.setInt(3, capacidadeMaxima);
            stmt.executeUpdate();

            stmt.close();
            System.out.println("Mesa cadastrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Não foi possível cadastrar a mesa");
            e.printStackTrace();
        }
    }

    // REMOVER MESA
    public static void removerMesaPeloNumero() throws Exception {
        connection = ConexaoDB.getInstance();
        String sql = "DELETE FROM mesa WHERE numero_mesa = ?";

        System.out.println("Informe o número da mesa que deseja remover: ");
        int numeroMesa = ler.nextInt();

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, numeroMesa);

        stmt.execute();
        stmt.close();
    }

    // BUSCAR MESA
    public static void buscarMesaPeloNumero() throws Exception {
        connection = ConexaoDB.getInstance();

        String sql = "SELECT * FROM mesa WHERE numero_mesa = ?";

        System.out.println("Informe o número da mesa que deseja buscar: ");
        int numeroMesa = ler.nextInt();

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, numeroMesa);
        ResultSet resultado = stmt.executeQuery();

        if (resultado.next()) {
            int numeroMesaEncontrada = resultado.getInt("numero_mesa");
            String situacao = resultado.getString("situacao");
            int capacidadeMaxima = resultado.getInt("capacidade_maxima");

            System.out.println("Mesa encontrada:");
            System.out.println("Número da Mesa: " + numeroMesaEncontrada);
            System.out.println("Situação: " + situacao);
            System.out.println("Capacidade Máxima: " + capacidadeMaxima);
        } else {
            System.out.println("Mesa não encontrada!");
        }

        resultado.close();
        stmt.close();
    }

    // BUSCAR GARÇOM RESPONSÁVEL PELA MESA
    public static void buscarGarcomResponsavelPelaMesa() throws Exception {
        connection = ConexaoDB.getInstance();

        String sql = "SELECT garcom.* FROM garcom JOIN mesa ON garcom.id_garcom = mesa.garcom_id WHERE mesa.numero_mesa = ?";

        System.out.println("Informe o número da mesa: ");
        int numeroMesa = ler.nextInt();

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, numeroMesa);
        ResultSet resultado = stmt.executeQuery();

        if (resultado.next()) {
            int id_garcom = resultado.getInt("id_garcom");
            String nome = resultado.getString("nome");
            String cpf = resultado.getString("cpf");
            String dataNascimento = resultado.getString("dataNascimento");
            String email = resultado.getString("email");
            String telefone = resultado.getString("telefone");
            String sexo = resultado.getString("sexo");
            double salarioFixo = resultado.getDouble("salarioFixo");

            System.out.println("Garçom responsável pela mesa:");
            System.out.println("ID: " + id_garcom);
            System.out.println("Nome: " + nome);
            System.out.println("CPF: " + cpf);
            System.out.println("Data de Nascimento: " + dataNascimento);
            System.out.println("Email: " + email);
            System.out.println("Telefone: " + telefone);
            System.out.println("Sexo: " + sexo);
            System.out.println("Salário Fixo: " + salarioFixo);
        } else {
            System.out.println("Não há garçom responsável pela mesa!");
        }

        resultado.close();
        stmt.close();
    }

    // BUSCAR MESAS LIVRES COM GARÇOM RESPONSÁVEL
    public static void buscarMesasLivresComGarcomResponsavel() throws Exception {
        connection = ConexaoDB.getInstance();

        String sql = "SELECT mesa.* FROM mesa JOIN garcom ON mesa.garcom_id = garcom.id_garcom WHERE mesa.situacao = 'livre'";

        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();

        if (resultado.next()) {
            System.out.println("Mesas livres com garçom responsável:");
            while (resultado.next()) {
                int numeroMesa = resultado.getInt("numero_mesa");
                String situacao = resultado.getString("situacao");
                int capacidadeMaxima = resultado.getInt("capacidade_maxima");

                System.out.println("Número da Mesa: " + numeroMesa);
                System.out.println("Situação: " + situacao);
                System.out.println("Capacidade Máxima: " + capacidadeMaxima);
                System.out.println();
            }
        } else {
            System.out.println("Não há mesas livres com garçom responsável!");
        }

        resultado.close();
        stmt.close();
    }
    // BUSCAR MESAS OCUPADAS POR GARÇOM
    public static void buscarMesasOcupadasPorGarcom() throws Exception {
        connection = ConexaoDB.getInstance();

        String sql = "SELECT mesa.* FROM mesa JOIN garcom ON mesa.garcom_id = garcom.id_garcom WHERE mesa.situacao = 'ocupada'";

        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();

        if (resultado.next()) {
            System.out.println("Mesas ocupadas por garçom:");
            while (resultado.next()) {
                int numeroMesa = resultado.getInt("numero_mesa");
                String situacao = resultado.getString("situacao");
                int capacidadeMaxima = resultado.getInt("capacidade_maxima");

                System.out.println("Número da Mesa: " + numeroMesa);
                System.out.println("Situação: " + situacao);
                System.out.println("Capacidade Máxima: " + capacidadeMaxima);
                System.out.println();
            }
        } else {
            System.out.println("Não há mesas ocupadas por garçom!");
        }

        resultado.close();
        stmt.close();
    }

    // CALCULAR QUANTIDADE DE MESAS ATENDIDAS POR GARÇOM
    public static void calcularQuantidadeMesasAtendidasPorGarcom() throws Exception {
        connection = ConexaoDB.getInstance();

        String sql = "SELECT garcom.id_garcom, garcom.nome, COUNT(mesa.numero_mesa) AS quantidadeMesasAtendidas FROM garcom JOIN mesa ON garcom.id_garcom = mesa.garcom_id WHERE mesa.situacao = 'ocupada' GROUP BY garcom.id_garcom";

        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();

        if (resultado.next()) {
            System.out.println("Quantidade de mesas atendidas por garçom:");
            while (resultado.next()) {
                int idGarcom = resultado.getInt("id_garcom");
                String nomeGarcom = resultado.getString("nome");
                int quantidadeMesasAtendidas = resultado.getInt("quantidadeMesasAtendidas");

                System.out.println("ID do Garçom: " + idGarcom);
                System.out.println("Nome do Garçom: " + nomeGarcom);
                System.out.println("Quantidade de Mesas Atendidas: " + quantidadeMesasAtendidas);
                System.out.println();
            }
        } else {
            System.out.println("Não há mesas ocupadas por garçom!");
        }

        resultado.close();
        stmt.close();
    }

}
