import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static Connection connection = null;
    static Scanner ler = new Scanner(System.in);
    static Scanner nomeScanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        int choice;

        do {
            System.out.println("Selecione uma opção:");
            System.out.println("1. Inserir Garçom");
            System.out.println("2. Remover Garçom");
            System.out.println("3. Buscar Garçom");
            System.out.println("4. Alterar Garçom");
            System.out.println("0. Sair");

            choice = ler.nextInt();
            ler.nextLine(); // Limpar o buffer de entrada

            switch (choice) {
                case 1:
                    inserirGarcom();
                    break;
                case 2:
                    removerGarcom();
                    break;
                case 3:
                    buscarGarcom();
                    break;
                case 4:
                    alterarGarcom();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

            System.out.println(); // Nova linha para separar as iterações
        } while (choice != 0);
    }

    private static void inserirGarcom() throws SQLException, ClassNotFoundException {
        int id_garcom;
        String nome, cpf, email, sexo, dataNascimento;
        double telefone, salarioFixo;

        System.out.println("Digite os dados do Garçom");

        System.out.println("Digite o nome");
        nome = ler.nextLine();
        System.out.println("Digite o CPF");
        cpf = ler.nextLine();
        System.out.println("Digite a data de nascimento");
        dataNascimento = ler.nextLine();
        System.out.println("Digite o email");
        email = ler.nextLine();
        System.out.println("Digite o telefone");
        telefone = ler.nextDouble();
        System.out.println("Digite o sexo");
        sexo = ler.next();
        System.out.println("Digite o salario fixo");
        salarioFixo = ler.nextDouble();
        System.out.println("Digite o ID");
        id_garcom = ler.nextInt();

        Garcom g = new Garcom(nome, cpf, dataNascimento, email, telefone, sexo, salarioFixo, id_garcom);
        inserirGarcom(g);
    }

    private static void removerGarcom() throws SQLException, ClassNotFoundException {
        System.out.println("Digite o email do garçom que deseja remover:");
        String emailGarcomRemover = ler.next();

        try {
            removerGarcomPeloEmail(emailGarcomRemover);
            System.out.println("Garçom removido com sucesso");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void buscarGarcom() throws SQLException, ClassNotFoundException {
        System.out.println("Digite o email do garçom que você deseja buscar");
        String emailGarcomBuscado = ler.next();

        Garcom gEncontrado = null;
        try {
            gEncontrado = buscarGarcomPeloEmail(emailGarcomBuscado);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (gEncontrado == null) {
            System.out.println("Garçom não encontrado");
        } else {
            System.out.println("\nNOME: "+ gEncontrado.getNome() );
            System.out.println("CPF: "+ gEncontrado.getCpf());
            System.out.println("DATANASCIMENTO: "+ gEncontrado.getDataNascimento());
            System.out.println("EMAIL: "+ gEncontrado.getEmail());
            System.out.println("TELEFONE: "+ gEncontrado.getTelefone());
            System.out.println("SEXO: "+ gEncontrado.getSexo());
            System.out.println("SALARIO FIXO: "+ gEncontrado.getSalarioFixo());
            System.out.println("ID: "+ gEncontrado.getId_garcom());

            System.out.println();
        }
    }

    private static void alterarGarcom() throws SQLException, ClassNotFoundException {
        System.out.println("Digite o email do garçom que deseja alterar:");
        String emailGarcomAlterar = ler.next();

        Garcom gEncontrado = null;
        try {
            gEncontrado = buscarGarcomPeloEmail(emailGarcomAlterar);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (gEncontrado == null) {
            System.out.println("Garçom não encontrado");
        } else {
            System.out.println("Digite os novos dados do garçom:");

            System.out.println("NOME:");
            String nome = ler.next();
            ler.nextLine();
            System.out.println("CPF:");
            String cpf = ler.nextLine();
            System.out.println("DataNascimento");
            String dataNascimento = ler.nextLine();
            System.out.println("EMAIL:");
            String email = ler.nextLine();
            System.out.println("TELEFONE:");
            double telefone = ler.nextDouble();
            System.out.println("SEXO");
            String sexo = ler.next();
            System.out.println("SalarioFixo");
            double salarioFixo = ler.nextDouble();
            System.out.println("ID:");
            int id_garcom = Integer.parseInt(ler.next());
            ler.nextLine();

            Garcom gDadosAtualizados = new Garcom(nome, cpf, dataNascimento, email, telefone, sexo, salarioFixo, id_garcom);
            try {
                alterarGarcom(gDadosAtualizados);
                System.out.println("Garçom alterado com sucesso");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void inserirGarcom(Garcom g) throws SQLException, ClassNotFoundException {
        try {
            connection = ConexaoDB.getInstance();
            String sql = "INSERT INTO garcom (id_garcom, nome, cpf, dataNascimento, email, telefone, sexo, salarioFixo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, g.getId_garcom());
            stmt.setString(2, g.getNome());
            stmt.setString(3, g.getCpf());
            stmt.setString(4, g.getDataNascimento());
            stmt.setString(5, g.getEmail());
            stmt.setDouble(6, g.getTelefone());
            stmt.setString(7, g.getSexo());
            stmt.setDouble(8, g.getSalarioFixo());
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Não foi possível cadastrar o Garçom");
            e.printStackTrace();
        }
    }

    public static void removerGarcomPeloEmail(String emailDoGarcomQueSeraRemovido) throws Exception {
        connection = ConexaoDB.getInstance();
        String sql = "DELETE FROM garcom WHERE email LIKE ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, emailDoGarcomQueSeraRemovido);

        stmt.execute();
        stmt.close();
    }

    public static Garcom buscarGarcomPeloEmail(String emailBuscado) throws Exception {
        connection = ConexaoDB.getInstance();

        String sql = "SELECT * FROM garcom WHERE email LIKE ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, emailBuscado);
        ResultSet resultado = stmt.executeQuery();
        Garcom g = null;

        if (resultado.next()) {
            g = new Garcom(
                    resultado.getString("nome"),
                    resultado.getString("cpf"),
                    resultado.getString("dataNascimento"),
                    resultado.getString("email"),
                    resultado.getDouble("telefone"),
                    resultado.getString("sexo"),
                    resultado.getDouble("salarioFixo"),
                    resultado.getInt("id_garcom"));
        }

        resultado.close();
        stmt.close();

        return g;
    }

    public static void alterarGarcom(Garcom gSendoAlterado) throws Exception {
        connection = ConexaoDB.getInstance();

        String sql = "UPDATE garcom SET nome = ?, cpf = ?, dataNascimento = ?, email = ?, telefone = ?, sexo = ?, salarioFixo = ?, id_garcom = ? WHERE id_garcom = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, gSendoAlterado.getNome());
        stmt.setString(2, gSendoAlterado.getCpf());
        stmt.setString(3, gSendoAlterado.getDataNascimento());
        stmt.setString(4, gSendoAlterado.getEmail());
        stmt.setDouble(5, gSendoAlterado.getTelefone());
        stmt.setString(6, gSendoAlterado.getSexo());
        stmt.setDouble(7, gSendoAlterado.getSalarioFixo());
        stmt.setInt(8, gSendoAlterado.getId_garcom());
        stmt.setInt(9, gSendoAlterado.getId_garcom());



        stmt.execute();
        stmt.close();
    }
}
