package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Year;

public class DataHelper {
    private static final Faker faker = new Faker();

    private static final String approvedCardNumber = "4444 4444 4444 4441";
    private static final String declinedCardNumber = "4444 4444 4444 4442";
    private static final String wrongCardNumber = "4444 4444 4444 4443";
    private static final String unrealCardNumber = faker.number().digits(8);


    private DataHelper() {
    }

    public static DataCard fillCardInfo(String cardNumber) {
        String month = String.format("%02d", faker.number().numberBetween(1, 12));
        int currentYear = Year.now().getValue() % 100;
        String year = String.valueOf(faker.number().numberBetween(currentYear + 1, currentYear + 5));
        String owner = faker.name().name();
        String cvc = String.format("%03d", faker.number().numberBetween(1, 999));
        return new DataCard(cardNumber, month, year, owner, cvc);
    }

    public static DataCard getApprovedCard() {
        return fillCardInfo(approvedCardNumber);
    }

    public static DataCard getDeclinedCard() {
        return fillCardInfo(declinedCardNumber);
    }

    public static DataCard getWrongCard() {
        return fillCardInfo(wrongCardNumber);
    }

    public static DataCard getUnrealCard() {
        return fillCardInfo(unrealCardNumber);
    }

    public static DataCard getWrongMonthCard() {
        DataCard dataCard = fillCardInfo(approvedCardNumber);
        dataCard.setCardMonth(String.valueOf(faker.number().numberBetween(13, 99)));
        return dataCard;
    }

    public static DataCard getPastYearCard() {
        DataCard dataCard = fillCardInfo(approvedCardNumber);
        int currentYear = Year.now().getValue() % 100;
        dataCard.setCardYear(String.valueOf(faker.number().numberBetween(currentYear - 1, currentYear - 10)));
        return dataCard;
    }

    public static DataCard getFutureYearCard() {
        DataCard dataCard = fillCardInfo(approvedCardNumber);
        int currentYear = Year.now().getValue() % 100;
        dataCard.setCardYear(String.valueOf(faker.number().numberBetween(currentYear + 6, currentYear + 15)));
        return dataCard;
    }

    public static DataCard getWrongOwnerCard() {
        DataCard dataCard = fillCardInfo(approvedCardNumber);
        dataCard.setCardOwner(faker.phoneNumber().cellPhone());
        return dataCard;
    }

    public static DataCard getEmptyOwnerCard() {
        DataCard dataCard = fillCardInfo(approvedCardNumber);
        dataCard.setCardOwner("");
        return dataCard;
    }

    public static DataCard getWrongCVCCard() {
        DataCard dataCard = fillCardInfo(approvedCardNumber);
        dataCard.setCardCVC(String.valueOf(faker.number().numberBetween(1, 99)));
        return dataCard;
    }

    @Value
    public static class DataSQL {
        private static String url = "jdbc:mysql://192.168.99.100:3306/app";
        //        private static String url = "jdbc:postgresql://192.168.99.100:5432/app";
        private static String user = "app";
        private static String password = "pass";
        private static Connection conn;
        private static QueryRunner runner = new QueryRunner();


        public static Connection runConnection() {
            try {
                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return conn;
        }

        public static void cleanTables() {
            val deleteCreditRequestEntity = "DELETE FROM credit_request_entity";
            val deleteOrderEntity = "DELETE FROM order_entity";
            val deletePaymentEntity = "DELETE FROM payment_entity";
            try (
                    val conn = runConnection();
            ) {
                runner.update(conn, deleteCreditRequestEntity);
                runner.update(conn, deletePaymentEntity);
                runner.update(conn, deleteOrderEntity);
                System.out.println("Tables are clean");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        public static String getPaymentStatus() {
            val paymentStatus = "SELECT status FROM payment_entity WHERE created >= DATE_SUB(NOW() , INTERVAL 1 MINUTE);";
            try (
                    val conn = runConnection();
                    val countStmt = conn.createStatement();
            ) {
                try (val rs = countStmt.executeQuery(paymentStatus)) {
                    if (rs.next()) {
                        val status = rs.getString("status");
                        return status;
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
        }

        public static String getCreditStatus() {
            val select = "SELECT status FROM credit_request_entity WHERE created >= DATE_SUB(NOW() , INTERVAL 1 MINUTE);";
            try (
                    val conn = runConnection();
                    val countStmt = conn.createStatement();
            ) {
                try (val rs = countStmt.executeQuery(select)) {
                    if (rs.next()) {
                        val status = rs.getString("status");
                        return status;
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
        }

        public static String getOrderPaymentId() {
            val select = "SELECT payment_id FROM order_entity WHERE created >= DATE_SUB(NOW() , INTERVAL 15 SECONDS);";
            try (
                    val conn = runConnection();
                    val countStmt = conn.createStatement();
            ) {
                try (val rs = countStmt.executeQuery(select)) {
                    if (rs.next()) {
                        val getPaymentId = rs.getString("payment_id");
                        return getPaymentId;
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
        }

        public static String getOrderCreditId() {
            val select = "SELECT credit_id FROM order_entity WHERE created >= DATE_SUB(NOW() , INTERVAL 15 SECONDS);";
            try (
                    val conn = runConnection();
                    val countStmt = conn.createStatement();
            ) {
                try (val rs = countStmt.executeQuery(select)) {
                    if (rs.next()) {
                        val getCreditId = rs.getString("credit_id");
                        return getCreditId;
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
        }

        public static String getCreditId() {
            val select = "SELECT bank_id FROM credit_request_entity WHERE created >= DATE_SUB(NOW() , INTERVAL 15 SECONDS);";
            try (
                    val conn = runConnection();
                    val countStmt = conn.createStatement();
            ) {
                try (val rs = countStmt.executeQuery(select)) {
                    if (rs.next()) {
                        val getBankId = rs.getString("bank_id");
                        return getBankId;
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
        }

        public static String getPaymentId() {
            val select = "SELECT transaction_id FROM payment_entity WHERE created >= DATE_SUB(NOW() , INTERVAL 15 SECONDS);";
            try (
                    val conn = runConnection();
                    val countStmt = conn.createStatement();
            ) {
                try (val rs = countStmt.executeQuery(select)) {
                    if (rs.next()) {
                        val getTransactionId = rs.getString("transaction_id");
                        return getTransactionId;
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
        }
    }
}
