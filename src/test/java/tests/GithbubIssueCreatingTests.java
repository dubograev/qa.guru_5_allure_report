package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class GithbubIssueCreatingTests {

    @Test
    public void CreateIssueTest() {
        open("https://github.com");
        $("[href='/login']").click();
        $("input[name=login]").val("").pressTab();
        $("input[name=password]").val("").pressTab();
        $("input[value='Sign in']").waitUntil(visible,2000).click();
        $$(".btn").findBy(text("New")).should(exist);
    }
}
