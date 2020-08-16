package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Year;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DataHelper {
    private static final String approvedCardNumber = "4444 4444 4444 4441";
    private static final String declinedCardNumber = "4444 4444 4444 4442";
    private static final String wrongCardNumber = "4444 4444 4444 4443";

    private static final Faker faker = new Faker();
    private static Connection connection;
    private static QueryRunner runner = new QueryRunner();


//    private static String url = "jdbc:mysql://192.168.99.100:3306/app";
//    private static String user = "app";
//    private static String password = "pass";

    private DataHelper() {
    }

    public static DataCard fillCardInfo(String cardNumber) {
        String month = String.format("%2d" ,faker.number().numberBetween(1,12)).replace(" ", "0");
        int currentYear = Year.now().getValue() % 100;
        String year = String.valueOf(faker.number().numberBetween(currentYear,currentYear+5));
        String owner = faker.name().name();
        String cvc = String.format("%3d", faker.number().numberBetween(1, 999)).replace(" ", "0");
        return new DataCard(cardNumber,month,year,owner,cvc);
    }

    public static DataCard getApprovedCard(){
        return fillCardInfo(approvedCardNumber);
    }

    public static DataCard getDeclinedCard() {
        return fillCardInfo(declinedCardNumber);
    }

    public static DataCard getWrongCard() {
        return fillCardInfo(wrongCardNumber);
    }


//    @Value
//    public static class VerificationCode {
//        private String code;
//    }
//
//    public static String getVerificationCodeFor() {
//
//        val authCodes = "SELECT  code FROM auth_codes WHERE created >= DATE_SUB(NOW() , INTERVAL 1 MINUTE);";
//        try (
//                val conn = DriverManager.getConnection(url, user, password);
//                val countStmt = conn.createStatement();
//        ) {
//            try (val rs = countStmt.executeQuery(authCodes)) {
//                if (rs.next()) {
//                    // выборка значения по индексу столбца (нумерация с 1)
//                    val code = rs.getString("code");
//                    return code;
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
//
//
//    public static void cleanTables() {
//        val runner = new QueryRunner();
//        val deleteUsers = "DELETE FROM users";
//        val deleteCards = "DELETE FROM cards";
//        val deleteAuthCodes = "DELETE FROM auth_codes";
//        val deleteCardTrans = "DELETE FROM card_transactions";
//        try (
//                val conn = DriverManager.getConnection(url, user, password);
//        ) {
//            // обычная вставка
//            runner.update(conn, deleteCardTrans);
//            runner.update(conn, deleteAuthCodes);
//            runner.update(conn, deleteCards);
//            runner.update(conn, deleteUsers);
//            System.out.println("Tables are clean");
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
}