import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    private static Connection connection = null;
    private String fonte = "Restaurante?useTimezone=true&serverTimezone=UTC";
    private String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private ConexaoDB () throws ClassNotFoundException {

        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + fonte, "root", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ocorreu um erro ao conectar o banco de dados");
        }
    }

    public static Connection getInstance() throws ClassNotFoundException {
        if (connection == null){
            new ConexaoDB();
        }
        return connection;
    }

}