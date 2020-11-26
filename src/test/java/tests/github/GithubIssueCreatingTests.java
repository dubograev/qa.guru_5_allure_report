package tests.github;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class GithubIssueCreatingTests {

    double random = Math.random();
    String issueTitle = "Test issue #" + random;
    String login = Files.readAllLines(Paths.get("src/test/resources/login")).get(0);
    String pass = Files.readAllLines(Paths.get("src/test/resources/pass")).get(0);

    public GithubIssueCreatingTests() throws IOException {
    }

    @Test
    public void CreateIssueTest() {
        Configuration.browserSize = "1920x1080";

        open("https://github.com");

        $("[href='/login']").click();
        $("input[name=login]").val(login).pressTab();
        $("input[name=password]").val(pass).pressTab();
        $("input[value='Sign in']").waitUntil(visible,1000).click();

        $(byText("qa.guru_homework_lesson5")).click();
        $("[data-content='Issues']").click();
        $$(byText("New issue")).find(visible).click();

        $("#labels-select-menu").click();

        SelenideElement labelElement0 = $$(".select-menu-item-text").get(0);
        String label0 = labelElement0.$(".name").getText();
        System.out.println(label0);
        labelElement0.click();

        SelenideElement labelElement2 = $$(".select-menu-item-text").get(2);
        String label2 = labelElement2.$(".name").getText();
        System.out.println(label2);
        labelElement2.click();

        SelenideElement labelElement3 = $$(".select-menu-item-text").get(3);
        String label3 = labelElement3.$(".name").getText();
        System.out.println(label3);
        labelElement3.click();

        SelenideElement labelElement5 = $$(".select-menu-item-text").get(5);
        String label5 = labelElement5.$(".name").getText();
        System.out.println(label5);
        labelElement5.click();

        $("#labels-select-menu").click();

        $("#assignees-select-menu").click();
        $(".js-username").click();
        $("#assignees-select-menu").click();

        $("#issue_body").val("test issue");
        $("#issue_title").val(issueTitle).pressEnter();

        $("h1[class*='gh-header-title']").shouldHave(text(issueTitle));
        $("#discussion_bucket").$(".js-issue-timeline-container").shouldHave(text(label0));
        $("#discussion_bucket").$(".js-issue-timeline-container").shouldHave(text(label2));
        $("#discussion_bucket").$(".js-issue-timeline-container").shouldHave(text(label3));
        $("#discussion_bucket").$(".js-issue-timeline-container").shouldHave(text(label5));
        $("#assignees-select-menu").parent().shouldHave(text("dubograev"));

        Selenide.closeWindow();
    }
}