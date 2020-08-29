
package ru.netology.web.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataCard;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement cardMonth = $("[placeholder='08']");
    private SelenideElement cardYear = $("[placeholder='22']");
    private SelenideElement cardOwner = $(withText("Владелец")).parent().$(".input__box>input.input__control");
    private SelenideElement cardCVC = $("[placeholder='999']");
    private SelenideElement proceedButton = $(withText("Продолжить"));

    private ElementsCollection textInputFields = $$(".input_type_text");
    private SelenideElement cardNumberError = textInputFields.get(0).find(".input__sub");
    private SelenideElement cardMonthError = textInputFields.get(1).find(".input__sub");
    private SelenideElement cardYearError = textInputFields.get(2).find(".input__sub");
    private SelenideElement cardOwnerError = textInputFields.get(3).find(".input__sub");
    private SelenideElement cardCVCError = textInputFields.get(4).find(".input__sub");

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

    public void fillInIncorrectCard(DataCard card) {
        cardNumber.setValue("1");
        cardMonth.setValue("2");
        cardYear.setValue("3");
        cardOwner.setValue("4");
        cardCVC.setValue("5");
        proceedButton.click();
    }

    public void clickProceedButton() {
        proceedButton.click();
    }

    public void waitNotificationApprove() {
        notificationApprove.waitUntil(visible, 15000);
    }

    public void waitNotificationDecline() {
        notificationDecline.waitUntil(visible, 15000);
    }

    public String checkCardNumberError() {
        cardNumberError.shouldBe(visible);
        String error = cardNumberError.getText();
        return error;
    }

    public String checkCardMonthError() {
        cardMonthError.shouldBe(visible);
        String error = cardMonthError.getText();
        return error;
    }

    public String checkCardYearError() {
        cardYearError.shouldBe(visible);
        String error = cardYearError.getText();
        return error;
    }

    public String checkCardOwnerError() {
        cardOwnerError.shouldBe(visible);
        String error = cardOwnerError.getText();
        return error;
    }

    public String checkCardCVCError() {
        cardCVCError.shouldBe(visible);
        String error = cardCVCError.getText();
        return error;
    }

    public void cleanCardFields() {
        cardNumber.sendKeys(Keys.LEFT_CONTROL + "a" + Keys.DELETE);
        cardMonth.sendKeys(Keys.LEFT_CONTROL + "a" + Keys.DELETE);
        cardYear.sendKeys(Keys.LEFT_CONTROL + "a" + Keys.DELETE);
        cardOwner.sendKeys(Keys.LEFT_CONTROL + "a" + Keys.DELETE);
        cardCVC.sendKeys(Keys.LEFT_CONTROL + "a" + Keys.DELETE);
    }

    public void checkAllFieldErrorVisible() {
        cardNumberError.shouldBe(visible);
        cardMonth.shouldBe(visible);
        cardYear.shouldBe(visible);
        cardOwner.shouldBe(visible);
        cardCVC.shouldBe(visible);
    }

    public void checkAllFieldErrorInvisible() {
        cardNumberError.shouldBe(disappear);
        cardMonth.shouldBe(disappear);
        cardYear.shouldBe(disappear);
        cardOwner.shouldBe(disappear);
        cardCVC.shouldBe(disappear);
    }
}
