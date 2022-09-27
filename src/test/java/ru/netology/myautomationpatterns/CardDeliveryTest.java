package ru.netology.myautomationpatterns;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 3;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        if ($("[data-test-id='city'] .input__sub").has(exactText("Доставка в выбранный город недоступна"))) {
            $("[data-test-id=city] input").setValue(validUser.getCity());
        } else {
            return;
        }
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button__content").click();
        $("[data-test-id='success-notification'] .notification__title").shouldHave(exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $(".button__content").click();
        $("[data-test-id='replan-notification'] .notification__title ").shouldHave(exactText("Необходимо подтверждение"));
        $("[data-test-id='replan-notification'] .notification__content .button").click();
        $("[data-test-id='success-notification'] .notification__title").shouldHave(exactText("Успешно!"));
    }
}