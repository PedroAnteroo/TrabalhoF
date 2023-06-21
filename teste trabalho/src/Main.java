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
            Menu();
            linha = ler.nextInt();
            ler.nextLine();

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
                    System.out.println("Finalizado");
                    break;
                default:
                    System.out.println("ERRO!");
                    break;
            }

            System.out.println();
        } while (linha != 0);
    }

    private static void Menu() {
        System.out.println("Opções do Garçom:");
        System.out.println("1) Inserir");
        System.out.println("2) Remover");
        System.out.println("3) Buscar");
        System.out.println("4) Alterar");
        System.out.println("5) Calcular Média do Salario");
        System.out.println("Digite 0 para Finalizar");
    }
    //CALCULAR MEDIA
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
            System.out.println("Erro no calculo da Media!");
            e.printStackTrace();
        }
    }
    //INSERIR
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
    //REMOVER
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
    //BUSCAR
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
    //ALTERAR
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
}
