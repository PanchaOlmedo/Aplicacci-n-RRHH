package ar.com.ada.backend12.borrador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import ar.com.ada.backend12.borrador2.Menu;

public class empleadoBorrador3 {

	private static final String URL = "jdbc:mysql://localhost:3306/rrhh";
	private static final String USER = "root";
	private static final String PASS = "Ciromanju28";

	public static void main(String[] args) {

		Connection conn = null;
		Statement stmt = null;
		Menu menu = new Menu();
		Scanner s = new Scanner(System.in);
		boolean cierreMientras = true;

		try {

			conn = DriverManager.getConnection(URL, USER, PASS);

			while (cierreMientras) {
				menu.imprimirMenu();

				System.out.print("Ingrese una opcion: ");

				int opcion = s.nextInt();

				switch (opcion) {
				case 1:
					System.out.println("Usted a elegido la opción 1: 'Crear usuario'.");

					System.out.println("Ingrese un nombre");
					String nombreE = s.next();
					/////////////////////////
					System.out.println("Ingrese un apellido");
					String apellidoE = s.next();
					///////////////////////////////
					System.out.println("Ingrese el documento de identidad: ");
					String diE = s.next();
					///////////////////////////////////
					System.out.println("Ingrese la fecha de nacimiento: ");
					String fechaNacimiento = s.next();
					/////////////////////////////////////
					System.out.println("Ingrese el departamento: ");
					String departamentoE = s.next();
					/////////////////////////////////////
					System.out.println("Ingrese la fecha de contratación: ");
					String fechaContratacion = s.next();
					/////////////////////////////////////
					System.out.println("Ingrese el salario: ");
					int salario = s.nextInt();
					insertCustomer(nombreE, apellidoE, diE, fechaNacimiento, departamentoE, fechaContratacion, salario,
							conn);
					System.out.println(
							"----------------Se ha agregado un nuevo empleado a la base de datos.------------- ");
					break;

				case 2:
					System.out.println("Usted a elegido la opción 2: 'Consultar usuario/s'.");
					System.out.println(
							"Eliga una opción:      A. Consultar un usuario por su ID | B.  Consultar todos los usuarios ");
					// int inputEmployeeID = s.nextInt();
					String subOpcion = s.next();

					switch (subOpcion) {

					case "A":
						System.out.println("Ingrese el ID del empleado a consultar: ");
						int inputEmployeeID = s.nextInt();
						consultEmployee(conn, inputEmployeeID);

						/*
						 * if (inputEmployeeID == -1) {
						 * System.out.println("Error de consulta, vuelve a intentarlo."); break; } else
						 * {
						 * 
						 * }
						 */

						break;

					case "B":
						System.out.println("Usted a elegido la opción 'consultar todos los usuario'. ");
						consultarEmployees(conn);
						break;
					}
					break;

				case 3:
					System.out.println("Usted a elegido la opción 3: 'Actualizar información del usuario'.");
					System.out.println("Ingrese el ID del empleado a actualizar: ");
					int inputEmployeeIdUpdate = s.nextInt();
					//////////////////////////////////////
					System.out.println("Ingrese el nombre del nuevo empleado: ");
					String nameU = s.next();
					/////////////////////////////////////////
					System.out.println("Ingrese el apellido del nuevo empleado: ");
					String lastName = s.next();
					///////////////////////////////////////
					System.out.println("Ingrese el documento de identidad: ");
					String dI = s.next();
					//////////////////////////////////////
					System.out.println("Ingrese la fecha de nacimiento: ");
					String fecha_Nacimiento = s.next();
					////////////////////////////////////////
					System.out.println("Ingrese su departamento: ");
					String departamento = s.next();
					/////////////////////////////////////////
					System.out.println("Ingrese la fecha de contratación: ");
					String fecha_Contratacion = s.next();
					////////////////////////////////////////
					System.out.println("Ingrese el salario: ");
					int salarioE = s.nextInt();

					updateEmployee(conn, nameU, lastName, dI, fecha_Nacimiento, departamento, fecha_Contratacion,
							salarioE, inputEmployeeIdUpdate);

					System.out.println("--------------Actualización exitosa.-------------------");

					break;

				case 4:
					System.out.println("Usted a elegido la opción 4: 'Eliminar usuario'.");
					System.out.println("Ingrese el ID del empleado a eliminar: ");
					int inputEmployeeIDelete = s.nextInt();
					deleteEmployee(conn, inputEmployeeIDelete);
					System.out.println("------------Eliminación exitosa----------------");
					break;

				case 5:
					cierreMientras = false;

					System.out.println("Fin del programa");

					break;

				}

			}

			System.out.println("Fin del programa.");
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error en el programa. Se aborta la ejecución.");
			e.printStackTrace();
		} finally {

			// 4.Cerrar la conexión a la base de datos.
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException f) {
					f.printStackTrace();
				}

				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException f) {
						f.printStackTrace();
					}
				}
			}

		}
	}

	/*
	 * 
	 * // if(stmt.executeUpdate(sql) > 0 ) {
	 * 
	 * // } if (conn != null) { try { conn.close(); } catch (SQLException f) {
	 * f.printStackTrace(); } } } }
	 */
	private static void insertCustomer(String nombreE, String apellidoE, String diE, String fechaNacimiento,
			String departamentoE, String fechaContratacion, int salario, Connection conn) throws SQLException {
		// 2.Preparar nuestra consulta.
		String sql = "INSERT INTO EMPLOYEE (NAME, LAST_NAME, DI, BIRTHDAY, DEPARTAMENT, HIRING_DATE, SALARY)  VALUES (?,?,?,?,?,?,?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, nombreE);
		stmt.setString(2, apellidoE);
		stmt.setString(3, diE);
		stmt.setString(4, fechaNacimiento);
		stmt.setString(5, departamentoE);
		stmt.setString(6, fechaContratacion);
		stmt.setInt(7, salario);

		// 3.Ejecutar nuestra consulta.
		stmt.executeUpdate();
		stmt.close();
	}

	///////// CONSULTAR EMPLEADO POR ID "CASE 2"//////
	private static void consultEmployee(Connection conn, int inputEmployeeID) throws SQLException {

		String sql = "SELECT * FROM EMPLOYEE WHERE ID = " + inputEmployeeID;

		Statement stmt = null;

		stmt = conn.createStatement();

		// 2.Ejecutar nuestra consulta.
		ResultSet rs = stmt.executeQuery(sql);

		// 3.Procesar los resultados de nuestra consulta.

		while (rs.next()) {
			int idEmpleado = rs.getInt("ID");
			String nombre = rs.getString("NAME");
			String lastName = rs.getString("LAST_NAME");
			String diE = rs.getString("DI");
			String fechaNacimiento = rs.getString("BIRTHDAY");
			String departamentoE = rs.getString("DEPARTAMENT");
			String hiringDate = rs.getString("HIRING_DATE");
			String salarioE = rs.getString("SALARY");

			System.out.println(idEmpleado + ", " + nombre + ", " + lastName + ", " + diE + ", " + fechaNacimiento + ", "
					+ departamentoE + ", " + hiringDate + ", " + salarioE);
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException f) {
				f.printStackTrace();
			}
		}

	}

	//// CONSULTAR TODOS LOS EMPLEADOS////
	private static void consultarEmployees(Connection conn) throws SQLException {

		String sql = "SELECT * FROM EMPLOYEE";

		Statement stmt = null;

		stmt = conn.createStatement();

		// 2.Ejecutar nuestra consulta.
		ResultSet rs = stmt.executeQuery(sql);

		// 3.Procesar los resultados de nuestra consulta.

		while (rs.next()) {
			int idEmpleado = rs.getInt("ID");
			String nombre = rs.getString("NAME");
			String lastName = rs.getString("LAST_NAME");
			String diE = rs.getString("DI");
			String fechaNacimiento = rs.getString("BIRTHDAY");
			String departamentoE = rs.getString("DEPARTAMENT");
			String hiringDate = rs.getString("HIRING_DATE");
			String salarioE = rs.getString("SALARY");

			System.out.println(idEmpleado + ", " + nombre + ", " + lastName + ", " + diE + ", " + fechaNacimiento + ", "
					+ departamentoE + ", " + hiringDate + ", " + salarioE);
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException f) {
				f.printStackTrace();
			}
		}

	}

	/////////////// CASE 3://///////////////////////////
	private static void updateEmployee(Connection conn, String nameU, String lastName, String dI,
			String fecha_Nacimiento, String departamento, String fecha_Contratacion, int salarioE,
			int inputEmployeeIdUpdate) throws SQLException {

		/*
		 * String name = null; String lastname = null; String ide = null; String
		 * fechaNacimiento = null; String departamentoU = null; String fechaContratacion
		 * = null; int salario = 0; int idU = 0;
		 */

		// 2.Preparar nuestra consulta.
		String sql = "UPDATE EMPLOYEE SET NAME = ?, LAST_NAME = ?,  DI = ?, BIRTHDAY = ?, DEPARTAMENT = ?, HIRING_DATE = ?, SALARY = ? WHERE ID = ? ";
		PreparedStatement stmtp = conn.prepareStatement(sql);
		stmtp.setString(1, nameU);
		stmtp.setString(2, lastName);
		stmtp.setString(3, dI);
		stmtp.setString(4, fecha_Nacimiento);
		stmtp.setString(5, departamento);
		stmtp.setString(6, fecha_Contratacion);
		stmtp.setDouble(7, salarioE);
		stmtp.setInt(8, inputEmployeeIdUpdate);

		stmtp.executeUpdate();
		stmtp.close();

	}

//////////CASE 4://///////////////////////////
	private static void deleteEmployee(Connection conn, int inputEmployeeIDelete) throws SQLException {

		String sql = "DELETE FROM EMPLOYEE WHERE ID = ? ";
		PreparedStatement stmtp = conn.prepareStatement(sql);
		stmtp.setInt(1, inputEmployeeIDelete);

		// 3.Ejecutar nuestra consulta.
		stmtp.executeUpdate();
		stmtp.close();

	}

}
