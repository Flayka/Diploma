package ru.netology.web.data;

import com.github.javafaker.Faker;
import ru.netology.web.entities.CardEntity;

import java.time.Year;

public class DataHelper {
    private static final Faker faker = new Faker();

    private static final String approvedCardNumber = "4444 4444 4444 4441";
    private static final String declinedCardNumber = "4444 4444 4444 4442";
    private static final String wrongCardNumber = "4444 4444 4444 4443";
    private static final String unrealCardNumber = faker.number().digits(8);


    private DataHelper() {
    }

    public static CardEntity fillCardInfo(String cardNumber) {
        String month = String.format("%02d", faker.number().numberBetween(1, 12));
        int currentYear = Year.now().getValue() % 100;
        String year = String.valueOf(faker.number().numberBetween(currentYear + 1, currentYear + 5));
        String owner = faker.name().name();
        String cvc = String.format("%03d", faker.number().numberBetween(1, 999));
        return new CardEntity(cardNumber, month, year, owner, cvc);
    }

    public static CardEntity getApprovedCard() {
        return fillCardInfo(approvedCardNumber);
    }

    public static CardEntity getDeclinedCard() {
        return fillCardInfo(declinedCardNumber);
    }

    public static CardEntity getWrongCard() {
        return fillCardInfo(wrongCardNumber);
    }

    public static CardEntity getUnrealCard() {
        return fillCardInfo(unrealCardNumber);
    }

    public static CardEntity getWrongMonthCard() {
        CardEntity cardEntity = fillCardInfo(approvedCardNumber);
        cardEntity.setCardMonth(String.valueOf(faker.number().numberBetween(13, 99)));
        return cardEntity;
    }

    public static CardEntity getPastYearCard() {
        CardEntity cardEntity = fillCardInfo(approvedCardNumber);
        int currentYear = Year.now().getValue() % 100;
        cardEntity.setCardYear(String.valueOf(faker.number().numberBetween(currentYear - 1, currentYear - 10)));
        return cardEntity;
    }

    public static CardEntity getFutureYearCard() {
        CardEntity cardEntity = fillCardInfo(approvedCardNumber);
        int currentYear = Year.now().getValue() % 100;
        cardEntity.setCardYear(String.valueOf(faker.number().numberBetween(currentYear + 6, currentYear + 15)));
        return cardEntity;
    }

    public static CardEntity getWrongOwnerCard() {
        CardEntity cardEntity = fillCardInfo(approvedCardNumber);
        cardEntity.setCardOwner(faker.phoneNumber().cellPhone());
        return cardEntity;
    }

    public static CardEntity getEmptyOwnerCard() {
        CardEntity cardEntity = fillCardInfo(approvedCardNumber);
        cardEntity.setCardOwner("");
        return cardEntity;
    }

    public static CardEntity getWrongCVCCard() {
        CardEntity cardEntity = fillCardInfo(approvedCardNumber);
        cardEntity.setCardCVC(String.valueOf(faker.number().numberBetween(1, 99)));
        return cardEntity;
    }
}
