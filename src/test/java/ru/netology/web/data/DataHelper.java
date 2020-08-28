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

//    @Value
//    public static class DataSQL {
//
//
//    }
}
