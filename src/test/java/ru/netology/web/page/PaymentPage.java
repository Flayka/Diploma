
package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataCard;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class PaymentPage {
    private SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement cardMonth = $("[placeholder='08']");
    private SelenideElement cardYear = $("[placeholder='22']");
    private SelenideElement cardOwner = $("#root > div > form > fieldset > div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement cardCVC = $("[placeholder='999']");
    private SelenideElement proceedButton = $$("button[type='button'][role='button'] span.button__text").find(text("Продолжить"));

    private SelenideElement notificationApprove = $(withText("Операция одобрена Банком."));
    private SelenideElement notificationDecline = $(withText("Ошибка! Банк отказал в проведении операции."));

    public void fillInCard(DataCard card) {
        cardNumber.setValue(card.getCardNumber());
        cardMonth.setValue(card.getCardMonth());
        cardYear.setValue(card.getCardYear());
        cardOwner.setValue(card.getCardOwner());
        cardCVC.setValue(card.getCardCVC());
        proceedButton.click();
    }

    public void clickProceedButton() {
        proceedButton.click();
    }

    public void showNotificationApprove() {
        notificationApprove.waitUntil(visible,20000);
    }

    public void showNotificationDecline() {
        notificationDecline.waitUntil(visible, 15000);
    }
}
