import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldBookApointment() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String currentDate;
        currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79998887766");
        $("[data-test-id='agreement'] span").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification']").should(Condition.appear, Duration.ofSeconds(15));
        $("[data-test-id='notification']").shouldHave(Condition.text("Встреча успешно забронирована на " + currentDate));
    }
}
