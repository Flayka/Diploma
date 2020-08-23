package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataCard;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.MainPage;
import ru.netology.web.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TravelBuyTest {
    MainPage mainPage = new MainPage();
    PaymentPage paymentPage = new PaymentPage();
    private static final String approvedCard = "APPROVED";
    private static final String declinedCard = "DECLINED";

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
    }

    @AfterAll
    static void CleanAllTables() {
        SelenideLogger.removeListener("allure");
 //       DataHelper.DataSQL.cleanTables();
    }

    //PayTests
    @Test
    void shouldPayApprovedCard() throws InterruptedException {
        mainPage.openPayPage();
        DataCard card = DataHelper.getApprovedCard();
        paymentPage.fillInCard(card);
        paymentPage.showNotificationApprove();

        sleep(10000);
        val actual = DataHelper.DataSQL.getPaymentStatus();
        assertEquals(approvedCard, actual);//

        val paymentId = DataHelper.DataSQL.getPaymentId();
        val paymentOrderId = DataHelper.DataSQL.getOrderPaymentId();
        assertEquals(paymentId, paymentOrderId);
    }

    @Test
    void shouldPayDeclinedCard() throws InterruptedException {
        mainPage.openPayPage();
        DataCard card = DataHelper.getDeclinedCard();
        paymentPage.fillInCard(card);
        paymentPage.showNotificationApprove();

        sleep(10000);
        val actual = DataHelper.DataSQL.getPaymentStatus();
        assertEquals(declinedCard, actual);

        val paymentId = DataHelper.DataSQL.getPaymentId();
        val paymentOrderId = DataHelper.DataSQL.getOrderPaymentId();
        assertEquals(paymentId, paymentOrderId);
    }

    @Test
    void shouldPayWrongCard() {
        mainPage.openPayPage();
        DataCard card = DataHelper.getWrongCard();
        paymentPage.fillInCard(card);
        paymentPage.showNotificationDecline();

        val paymentOrderId = DataHelper.DataSQL.getOrderPaymentId();//
        assertEquals(null, paymentOrderId);
    }

    @Test
    void shouldPayEmptyForm() {
        mainPage.openPayPage();
        paymentPage.clickProceedButton();
        paymentPage.allFieldErrorVisible();

        val paymentOrderId = DataHelper.DataSQL.getOrderPaymentId();
        assertEquals(null, paymentOrderId);
    }

    @Test
    void shouldRefillFieldsByCorrectCard() throws InterruptedException {
        mainPage.openPayPage();
        DataCard incorrectCard = DataHelper.getUnrealCard();
        paymentPage.fillInIncorrectCard(incorrectCard);
        paymentPage.allFieldErrorVisible();
        paymentPage.cleanCardFields();
        DataCard correctCard = DataHelper.getApprovedCard();
        paymentPage.fillInCard(correctCard);
        paymentPage.showNotificationApprove();

        sleep(10000);
        val paymentId = DataHelper.DataSQL.getPaymentId();
        val paymentOrderId = DataHelper.DataSQL.getOrderPaymentId();
        assertEquals(paymentId, paymentOrderId);

        paymentPage.allFieldErrorInvisible(); // issue
    }

    //Tests with wrong field forms
    @Test
    void shouldPayUnrealCard() {
        mainPage.openPayPage();
        DataCard card = DataHelper.getUnrealCard();
        paymentPage.fillInCard(card);
        String actual = paymentPage.checkCardNumberError();
        assertEquals("Неверный формат", actual);
    }

    @Test
    void shouldPayMonthErrorCard() {
        mainPage.openPayPage();
        DataCard card = DataHelper.getWrongMonthCard();
        paymentPage.fillInCard(card);
        String actual = paymentPage.checkCardMonthError();
        assertEquals("Неверно указан срок действия карты", actual);
    }

    @Test
    void shouldPayPastYearErrorCard() {
        mainPage.openPayPage();
        DataCard card = DataHelper.getPastYearCard();
        paymentPage.fillInCard(card);
        String actual = paymentPage.checkCardYearError();
        assertEquals("Истёк срок действия карты", actual);
    }

    @Test
    void shouldPayFutureYearErrorCard() {
        mainPage.openPayPage();
        DataCard card = DataHelper.getFutureYearCard();
        paymentPage.fillInCard(card);
        String actual = paymentPage.checkCardYearError();
        assertEquals("Неверно указан срок действия карты", actual);
    }

    @Test
    void shouldPayOwnerErrorCard() {
        mainPage.openPayPage();
        DataCard card = DataHelper.getWrongOwnerCard();
        paymentPage.fillInCard(card);
        String actual = paymentPage.checkCardOwnerError(); //Issue
        assertEquals("Неверный формат!!!", actual);
    }

    @Test
    void shouldPayEmptyOwnerCard() {
        mainPage.openPayPage();
        DataCard card = DataHelper.getEmptyOwnerCard();
        paymentPage.fillInCard(card);
        String actual = paymentPage.checkCardCVCError();
        assertEquals("Поле обязательно для заполнения", actual);
    }

    @Test
    void shouldPayCVCErrorCard() {
        mainPage.openPayPage();
        DataCard card = DataHelper.getWrongCVCCard();
        paymentPage.fillInCard(card);
        String actual = paymentPage.checkCardCVCError();
        assertEquals("Неверный формат", actual);
    }

    //CreditTests
    @Test
    void shouldCreditByApprovedCard() throws InterruptedException {
        mainPage.openCreditPage();
        DataCard card = DataHelper.getApprovedCard();
        paymentPage.fillInCard(card);
        paymentPage.showNotificationApprove();

        sleep(10000);
        val actual = DataHelper.DataSQL.getCreditStatus();
        assertEquals(approvedCard, actual);

        val creditId = DataHelper.DataSQL.getCreditId();
        val creditOrderId = DataHelper.DataSQL.getOrderCreditId();
        assertEquals(creditId, creditOrderId);
    }

    @Test
    void shouldCreditDeclinedCard() throws InterruptedException {
        mainPage.openCreditPage();
        DataCard card = DataHelper.getDeclinedCard();
        paymentPage.fillInCard(card);
        paymentPage.showNotificationApprove();

        sleep(10000);
        val actual = DataHelper.DataSQL.getCreditStatus();
        assertEquals(declinedCard, actual);
        val creditId = DataHelper.DataSQL.getCreditId();
        val creditOrderId = DataHelper.DataSQL.getOrderCreditId();
        assertEquals(creditId, creditOrderId);
    }

    @Test
    void shouldCreditWrongCard() throws InterruptedException {
        mainPage.openCreditPage();
        DataCard card = DataHelper.getWrongCard();
        paymentPage.fillInCard(card);
        paymentPage.showNotificationDecline();

        sleep(10000);
        val creditOrderId = DataHelper.DataSQL.getOrderCreditId();
        assertEquals(null, creditOrderId);
    }

    @Test
    void shouldCreditEmptyForm() throws InterruptedException {
        mainPage.openCreditPage();
        paymentPage.clickProceedButton();
        paymentPage.allFieldErrorVisible();

        sleep(10000);
        val creditOrderId = DataHelper.DataSQL.getOrderCreditId();
        assertEquals(null, creditOrderId);
    }
}
