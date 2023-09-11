import java.sql.*;
import java.util.Scanner;

public class Usus {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("*****BIENVENIDOS*****");

        System.out.println("Deseas crear un usuario, eliminar un usuario o editar un usuario?: ");
        String respuesta = scanner.nextLine();

        if (respuesta.equals("crear usuario")) {

            System.out.println("Ingrese el nombre: ");
            String nombreusuario = scanner.nextLine();

            String nombrebd = Select_One(nombreusuario);
            if (nombrebd.equals("")) {
                System.out.println("Ingrese documento de identidad: ");
                String documento = scanner.nextLine();

                System.out.print("Ingrese su usuario: ");
                String usuario = scanner.nextLine();

                System.out.print("Ingrese su contrase\u00f1a: ");
                String pass = scanner.nextLine();

                Insert(nombreusuario, documento, usuario, pass);

                if (nombreusuario.equals("") || documento.equals("") || usuario.equals("") || pass.equals("")) {
                    System.out.println("No se admiten datos vacios.");

                }

            } else {
                System.out.println("Este usuario ya se encuentra registrado");
            }

        }
        if (respuesta.equals("eliminar usuario")) {
            System.out.println("Que nombre de usuario deseas eliminar? ");
            String nombreusuario = scanner.nextLine();
            String busqueda = Select_One(nombreusuario);
            if (busqueda.equals("")) {
                System.out.println("Este usuario no se encuentra registrado");
            } else {
                Eliminar(nombreusuario);
            }
        }
        String nombreusuario = null;

        if (respuesta.equals("editar usuario")) {

            System.out.println("Que nombre de usuario deseas actualizar? ");
            nombreusuario = scanner.nextLine();

            if (Select_One(nombreusuario).equals("")) {
                System.out.println("No se encuentra este usuario");
            } else {


                System.out.print("Actualice el documento: ");
                String documento = scanner.nextLine();

                System.out.print("Actualice el usuario: ");
                String usuario = scanner.nextLine();

                System.out.print("Actualice la contrase\u00f1a: ");
                String pass = scanner.nextLine();

                Editar(nombreusuario, documento, usuario, pass);

            }
        }
    }

    private static void Editar(String nombreusuario, String documento, String usuario, String pass) throws ClassNotFoundException, SQLException {
        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/usuarios";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        String consulta = "UPDATE usus SET documento = ?, usuario = ?, pass = ? WHERE nombre = ?";
        PreparedStatement preparedStatement = connection2.prepareStatement(consulta);
        preparedStatement.setString(1, documento);
        preparedStatement.setString(2, usuario);
        preparedStatement.setString(3, pass);
        preparedStatement.setString(4, nombreusuario);

        int filasActualizadas = preparedStatement.executeUpdate();
        if (filasActualizadas > 0) {
            System.out.println("Usuario actualizado exitosamente");
        } else {
            System.out.println("No se encontró un usuario para actualizar");
        }

        preparedStatement.close();
        connection2.close();
    }

    private static String Select_One(String Nombre) throws ClassNotFoundException, SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/usuarios";
        String username = "root";
        String password = "";

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);

        String consultaSQL = "SELECT * FROM usus WHERE nombre = ?";

        PreparedStatement statement = connection.prepareStatement(consultaSQL);
        statement.setString(1, Nombre); // Establecer el valor del parámetro

        // Ejecutar la consulta
        ResultSet resultSet = statement.executeQuery();

        // Procesar el resultado si existe
        if (resultSet.next()) {
            String nombre = resultSet.getString("nombre");
            String documento = resultSet.getString("documento");
            String usuario = resultSet.getString("usuario");
            ;

            return nombre;

        }

        // Cerrar recursos
        resultSet.close();
        statement.close();
        connection.close();

        return "";
    }


    private static void Insert(String nombreusuario, String documento, String usuario, String pass) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/usuarios";
        String username = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM usus");


            // Sentencia INSERT
            String sql = "INSERT INTO usus (nombre, documento, usuario, pass) VALUES (?, ?, ?, ?)";

            // Preparar la sentencia
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nombreusuario);
            preparedStatement.setString(2, documento);
            preparedStatement.setString(3, usuario);
            preparedStatement.setString(4, pass);


            // Ejecutar la sentencia
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("usuario agregado exitosamente.");
            } else {
                System.out.println("No se pudo agregar el usuario.");
            }

            preparedStatement.close();
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void Eliminar(String nombreusuario) throws ClassNotFoundException, SQLException {
        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/usuarios";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        String sentenciaSQL = "DELETE FROM usus WHERE nombre = ?";
        PreparedStatement prepareStatement = connection2.prepareStatement(sentenciaSQL);
        prepareStatement.setString(1, nombreusuario);
        prepareStatement.executeUpdate();

        System.out.println("Usuario eliminado correctamente");
    }
    }
