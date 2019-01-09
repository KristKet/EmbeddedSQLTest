import java.sql.*;
import java.util.Scanner;

public class TestClassDB {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:ucanaccess://SQLDatabas.accdb";
        Connection con = DriverManager.getConnection(url);
        simpleselect(con);
        parameterizedselect(con);
        con.close();
    }

    private static void simpleselect(Connection con) throws SQLException {
        // Local variables
        String query;
        ResultSet rs;
        Statement stmt;

        System.out.println("Visa kurskod, kursben, längd, pris");

        query = "SELECT kurskod, kursben, längd, pris FROM Kurs";
        stmt = con.createStatement();
        rs = stmt.executeQuery(query);
        System.out.println("Resultatet:");

        while (rs.next()) {
            System.out.print("Kurskod: " + rs.getString("kurskod"));
            System.out.print(" kursben: " + rs.getString("kursben"));
            System.out.print(" längd: " + rs.getInt("längd"));
            System.out.println(" pris: " + rs.getInt("pris") + "kr");
        }

        stmt.close();
    }

    private static void parameterizedselect(Connection con) throws SQLException {
        // Local variables
        String query;
        ResultSet rs;
        PreparedStatement pstmt;
        String lokalparam;

        System.out.println("Se vilka lärare som undervisat i en viss lokal.");

        Scanner in = new Scanner(System.in);

        System.out.print("Ange lokal: ");
        lokalparam = in.nextLine();

        query = "SELECT DISTINCT lnamn FROM Lärare, Ktillf Where lid = lärare AND lokal = ?";

        pstmt = con.prepareStatement(query);
        pstmt.setString(1, lokalparam);
        rs = pstmt.executeQuery();

        System.out.println("Resultatet lärare som undervisat i " + lokalparam + " är");
        while (rs.next()) {
            System.out.println(rs.getString("lnamn"));
        }

        pstmt.close();
    }
}
