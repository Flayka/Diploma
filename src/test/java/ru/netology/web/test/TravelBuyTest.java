package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.DataSQL;
import ru.netology.web.entities.CardEntity;
import ru.netology.web.pages.MainPage;
import ru.netology.web.pages.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        open(System.getProperty("sut.url"));
    }

    @AfterAll
    static void AllureReport() {
        SelenideLogger.removeListener("allure");
        DataSQL.cleanTables();
    }

    //PayTests
    @Test
    void shouldPayApprovedCard() {
        mainPage.openPayPage();
        CardEntity card = DataHelper.getApprovedCard();
        paymentPage.fillInCard(card);
        paymentPage.waitNotificationApprove();

        val actual = DataSQL.getPaymentStatus();
        assertEquals(approvedCard, actual);

        val paymentId = DataSQL.getPaymentId();
        val paymentOrderId = DataSQL.getOrderPaymentId();
        assertEquals(paymentId, paymentOrderId);
    }

    @Test
    void shouldPayDeclinedCard() {
        mainPage.openPayPage();
        CardEntity card = DataHelper.getDeclinedCard();
        paymentPage.fillInCard(card);
        paymentPage.waitNotificationDecline(); //Issue

        val actual = DataSQL.getPaymentStatus();
        assertEquals(declinedCard, actual);

        val paymentId = DataSQL.getPaymentId();
        val paymentOrderId = DataSQL.getOrderPaymentId();
        assertEquals(paymentId, paymentOrderId);
    }

    @Test
    void shouldPayWrongCard() {
        mainPage.openPayPage();
        CardEntity card = DataHelper.getWrongCard();
        paymentPage.fillInCard(card);
        paymentPage.waitNotificationDecline(); //Issue

        val paymentOrderId = DataSQL.getOrderPaymentId();
        assertNull(paymentOrderId);
    }

    @Test
    void shouldPayEmptyForm() {
        mainPage.openPayPage();
        paymentPage.clickProceedButton();
        paymentPage.checkAllFieldErrorVisible();

        val paymentOrderId = DataSQL.getOrderPaymentId();
        assertNull(paymentOrderId);
    }

    @Test
    void shouldRefillFieldsByCorrectCard() {
        mainPage.openPayPage();
        CardEntity incorrectCard = DataHelper.getUnrealCard();
        paymentPage.fillInIncorrectCard(incorrectCard);
        paymentPage.checkAllFieldErrorVisible();
        paymentPage.cleanCardFields();
        CardEntity correctCard = DataHelper.getApprovedCard();
        paymentPage.fillInCard(correctCard);
        paymentPage.waitNotificationApprove();

        val paymentId = DataSQL.getPaymentId();
        val paymentOrderId = DataSQL.getOrderPaymentId();
        assertEquals(paymentId, paymentOrderId);

        paymentPage.checkAllFieldErrorInvisible(); // issue
    }

    //Tests with wrong field forms
    @Test
    void shouldPayUnrealCard() {
        mainPage.openPayPage();
        CardEntity card = DataHelper.getUnrealCard();
        paymentPage.fillInCard(card);
        String actual = paymentPage.checkCardNumberError();
        assertEquals("Неверный формат", actual);
    }

    @Test
    void shouldPayMonthErrorCard() {
        mainPage.openPayPage();
        CardEntity card = DataHelper.getWrongMonthCard();
        paymentPage.fillInCard(card);
        String actual = paymentPage.checkCardMonthError();
        assertEquals("Неверно указан срок действия карты", actual);
    }

    @Test
    void shouldPayPastYearErrorCard() {
        mainPage.openPayPage();
        CardEntity card = DataHelper.getPastYearCard();
        paymentPage.fillInCard(card);
        String actual = paymentPage.checkCardYearError();
        assertEquals("Истёк срок действия карты", actual);
    }

    @Test
    void shouldPayFutureYearErrorCard() {
        mainPage.openPayPage();
        CardEntity card = DataHelper.getFutureYearCard();
        paymentPage.fillInCard(card);
        String actual = paymentPage.checkCardYearError();
        assertEquals("Неверно указан срок действия карты", actual);
    }

    @Test
    void shouldPayOwnerErrorCard() {
        mainPage.openPayPage();
        CardEntity card = DataHelper.getWrongOwnerCard();
        paymentPage.fillInCard(card);
        String actualError = paymentPage.checkCardOwnerError(); //Issue
        assertEquals("Неверный формат", actualError);

        val actual = DataSQL.getPaymentStatus();
        assertNull(actual); //Issue
    }

    @Test
    void shouldPayEmptyOwnerCard() {
        mainPage.openPayPage();
        CardEntity card = DataHelper.getEmptyOwnerCard();
        paymentPage.fillInCard(card);
        String actual = paymentPage.checkCardOwnerError();
        assertEquals("Поле обязательно для заполнения", actual);
    }

    @Test
    void shouldPayCVCErrorCard() {
        mainPage.openPayPage();
        CardEntity card = DataHelper.getWrongCVCCard();
        paymentPage.fillInCard(card);
        String actual = paymentPage.checkCardCVCError();
        assertEquals("Неверный формат", actual);
    }

    //CreditTests
    @Test
    void shouldCreditByApprovedCard() {
        mainPage.openCreditPage();
        CardEntity card = DataHelper.getApprovedCard();
        paymentPage.fillInCard(card);
        paymentPage.waitNotificationApprove();

        val actual = DataSQL.getCreditStatus();
        assertEquals(approvedCard, actual);

        val creditId = DataSQL.getCreditId();
        val creditOrderId = DataSQL.getOrderCreditId();
        assertEquals(creditId, creditOrderId); //Issue
    }

    @Test
    void shouldCreditDeclinedCard() {
        mainPage.openCreditPage();
        CardEntity card = DataHelper.getDeclinedCard();
        paymentPage.fillInCard(card);
        paymentPage.waitNotificationDecline(); //Issue

        val actual = DataSQL.getCreditStatus();
        assertEquals(declinedCard, actual);
        val creditId = DataSQL.getCreditId();
        val creditOrderId = DataSQL.getOrderCreditId();
        assertEquals(creditId, creditOrderId);
    }

    @Test
    void shouldCreditWrongCard() {
        mainPage.openCreditPage();
        CardEntity card = DataHelper.getWrongCard();
        paymentPage.fillInCard(card);
        paymentPage.waitNotificationDecline();

        val creditOrderId = DataSQL.getOrderCreditId();
        assertNull(creditOrderId);
    }

    @Test
    void shouldCreditEmptyForm() {
        mainPage.openCreditPage();
        paymentPage.clickProceedButton();
        paymentPage.checkAllFieldErrorVisible();

        val creditOrderId = DataSQL.getOrderCreditId();
        assertNull(creditOrderId);
    }
}
