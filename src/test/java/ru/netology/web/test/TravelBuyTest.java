package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataCard;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.MainPage;
import ru.netology.web.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

class TravelBuyTest {
    MainPage mainPage = new MainPage();
    PaymentPage paymentPage = new PaymentPage();

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
    }

//    @AfterAll
//    public static void CleanAllTables() {
//        DataHelper.cleanTables();
//    }

    @Test
    void shouldApprovedCard() {
        mainPage.openPayPage();
        DataCard card = DataHelper.getApprovedCard();
        paymentPage.fillInCard(card);
        paymentPage.showNotificationApprove();
    }

    @Test
    void shouldPayDeclinedCard() {
        mainPage.openPayPage();
        DataCard card = DataHelper.getDeclinedCard();
        paymentPage.fillInCard(card);
        paymentPage.showNotificationDecline();
    }

    @Test
    void shouldPayWrongCard() {
        mainPage.openPayPage();
        DataCard card = DataHelper.getWrongCard();
        paymentPage.fillInCard(card);
        paymentPage.showNotificationDecline();
    }

    @Test
    void shouldCreditApprovedCard() {
        mainPage.openCreditPage();
        DataCard card = DataHelper.getApprovedCard();
        paymentPage.fillInCard(card);
        paymentPage.showNotificationApprove();
    }

    @Test
    void shouldCreditDeclinedCard() {
        mainPage.openCreditPage();
        DataCard card = DataHelper.getDeclinedCard();
        paymentPage.fillInCard(card);
        paymentPage.showNotificationDecline();
    }

    @Test
    void shouldCreditWrongCard() {
        mainPage.openCreditPage();
        DataCard card = DataHelper.getWrongCard();
        paymentPage.fillInCard(card);
        paymentPage.showNotificationDecline();
    }

}