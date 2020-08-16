package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
    private static SelenideElement paymentButton = $$("button").find(Condition.exactText("Купить"));
    ;
    private static SelenideElement creditButton = $$("button").find(Condition.exactText("Купить в кредит"));
    private static SelenideElement payCard = $$("h3[class]").find(exactText("Оплата по карте"));
    private static SelenideElement payCredit = $$("h3[class]").find(exactText("Кредит по данным карты"));

    public static void openPayPage() {
        paymentButton.click();
        payCard.waitUntil(enabled, 15000);
    }

    public static void openCreditPage() {
        creditButton.click();
        payCredit.waitUntil(enabled, 15000);
    }
}
