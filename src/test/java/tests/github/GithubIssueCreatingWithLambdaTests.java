package tests.github;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class GithubIssueCreatingWithLambdaTests {

    double random = Math.random();
    String issueTitle = "Test issue #" + random;
    String login = Files.readAllLines(Paths.get("src/test/resources/login")).get(0);
    String pass = Files.readAllLines(Paths.get("src/test/resources/pass")).get(0);

    public GithubIssueCreatingWithLambdaTests() throws IOException {
    }

    @Test
    @DisplayName("Creating issue test")
    @Story("User should be able to create an issue with labels and attendees")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Andrey")
    @Feature("Issues")
    public void CreateIssueTest() {
        Configuration.browserSize = "1920x1080";

        step("Open GitHub main page", () ->
            open("https://github.com")
        );

        step("Authorization", () -> {
            $("[href='/login']").click();
            $("input[name=login]").val(login).pressTab();
            $("input[name=password]").val(pass).pressTab();
            $("input[value='Sign in']").waitUntil(visible,1000).click();
        });

        step("Open repository", () ->
            $(byText("qa.guru_homework_lesson5")).click()
        );

        step("Open Issues tab", () ->
            $("[data-content='Issues']").click()
        );

        step("Start to create a new issue", () ->
            $$(byText("New issue")).find(visible).click()
        );

        step("Add labels", () -> {
            $("#labels-select-menu").click();
            $$(".select-menu-item-text").get(0).click();
            $$(".select-menu-item-text").get(2).click();
            $$(".select-menu-item-text").get(3).click();
            $$(".select-menu-item-text").get(5).click();
            $("#labels-select-menu").click();
        });

        step("Add assignees", () -> {
            $("#assignees-select-menu").click();
            $(".js-username").click();
            $("#assignees-select-menu").click();
        });

        step("Add a comment", () -> {
            $("#issue_body").val("test issue");
        });

        step("Add a title and submit", () -> {
            $("#issue_title").val(issueTitle).pressEnter();
        });

        step("Check that issue title is the same with the title we entered before", () -> {
            $("h1[class*='gh-header-title']").shouldHave(text(issueTitle));
        });

        step("Check that the labels are correct", () -> {
            $("#discussion_bucket").$(".js-issue-timeline-container").shouldHave(text("bug"));
            $("#discussion_bucket").$(".js-issue-timeline-container").shouldHave(text("duplicate"));
            $("#discussion_bucket").$(".js-issue-timeline-container").shouldHave(text("enhancement"));
            $("#discussion_bucket").$(".js-issue-timeline-container").shouldHave(text("help wanted"));
        });

        step("Check the assignees", () -> {
            $("#assignees-select-menu").parent().shouldHave(text("dubograev"));
        });

        Selenide.closeWindow();
    }
}