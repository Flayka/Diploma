package ru.netology.web.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardEntity {
    private String cardNumber;
    private String cardMonth;
    private String cardYear;
    private String cardOwner;
    private String cardCVC;
}
