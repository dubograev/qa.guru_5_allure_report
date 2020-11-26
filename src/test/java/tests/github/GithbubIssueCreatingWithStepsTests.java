package tests.github;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
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

public class GithbubIssueCreatingWithStepsTests {

    static double random = Math.random();
    static String issueTitle = "Test issue #" + random;

    static String login;
    static {
        try {
            login = Files.readAllLines(Paths.get("src/test/resources/login")).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String pass;
    static {
        try {
            pass = Files.readAllLines(Paths.get("src/test/resources/pass")).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Creating issue test")
    @Story("User should be able to create an issue with labels and attendees")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Andrey")
    @Feature("Issues")
    public void CreateIssueTest() {
        Configuration.browserSize = "1920x1080";

        BaseSteps steps = new BaseSteps();

        steps.openMainPage();
        steps.signIn();
        steps.openRepository();
        steps.openIssuesTab();
        steps.clickNewIssueButton();

        steps.openOrCloseLabelsDropdown();
        String label0 = steps.addBugLabel();
        String label2 = steps.addDuplicateLabel();
        String label3 = steps.addEnhancementLabel();
        String label5 = steps.addHelpWantedLabel();
        steps.openOrCloseLabelsDropdown();

        steps.openOrCloseAttendeesDropdown();
        steps.selectAttendee();
        steps.openOrCloseAttendeesDropdown();

        steps.enterCommentToIssue();
        steps.enterTitleOfIssueAndSubmit();

        steps.checkTitle();
        steps.checkLablels(label0, label2, label3, label5);
        steps.checkAttendees();

        Selenide.closeWindow();
    }

    public static class BaseSteps {

        @Step("Open main page")
        private void openMainPage() {
            open("https://github.com");
        }

        @Step("Sign In")
        private void signIn() {
            $("[href='/login']").click();
            $("input[name=login]").val(login).pressTab();
            $("input[name=password]").val(pass).pressTab();
            $("input[value='Sign in']").waitUntil(visible,1000).click();
        }

        @Step("Open Repository")
        private void openRepository() {
            $(byText("qa.guru_homework_lesson5")).click();
        }

        @Step("Add 'help wanted' label")
        private String addHelpWantedLabel() {
            SelenideElement labelElement5 = $$(".select-menu-item-text").get(5);
            String label5 = labelElement5.$(".name").getText();
            System.out.println(label5);
            labelElement5.click();
            return label5;
        }

        @Step("Add 'enhancement' label")
        private String addEnhancementLabel() {
            SelenideElement labelElement3 = $$(".select-menu-item-text").get(3);
            String label3 = labelElement3.$(".name").getText();
            System.out.println(label3);
            labelElement3.click();
            return label3;
        }

        @Step("Add 'duplicate' label")
        private String addDuplicateLabel() {
            SelenideElement labelElement2 = $$(".select-menu-item-text").get(2);
            String label2 = labelElement2.$(".name").getText();
            System.out.println(label2);
            labelElement2.click();
            return label2;
        }

        @Step("Add 'bug' label")
        private String addBugLabel() {
            SelenideElement labelElement0 = $$(".select-menu-item-text").get(0);
            String label0 = labelElement0.$(".name").getText();
            System.out.println(label0);
            labelElement0.click();
            return label0;
        }

        @Step("Click on Labels to open or close the dropdown")
        private void openOrCloseLabelsDropdown() {
            $("#labels-select-menu").click();
        }

        @Step("Click on New Issue button")
        private void clickNewIssueButton() {
            $$(byText("New issue")).find(visible).click();
        }

        @Step("Open Issues Tab")
        private void openIssuesTab() {
            $("[data-content='Issues']").click();
        }

        @Step("Enter the title of an issue and click on Submit button")
        private void enterTitleOfIssueAndSubmit() {
            $("#issue_title").val(issueTitle).pressEnter();
        }

        @Step("Add a comment to the issue")
        private void enterCommentToIssue() {
            $("#issue_body").val("test issue");
        }

        @Step("Add an assignee")
        private void selectAttendee() {
            $(".js-username").click();
        }

        @Step("Click on Attendees to open or close the dropdown")
        private void openOrCloseAttendeesDropdown() {
            $("#assignees-select-menu").click();
        }

        @Step("Check that the list of attendees is correct")
        private void checkAttendees() {
            $("#assignees-select-menu").parent().shouldHave(text("dubograev"));
        }

        @Step("Check that the list of labels is correct")
        private void checkLablels(String label0, String label2, String label3, String label5) {
            $("#discussion_bucket").$(".js-issue-timeline-container").shouldHave(text(label0));
            $("#discussion_bucket").$(".js-issue-timeline-container").shouldHave(text(label2));
            $("#discussion_bucket").$(".js-issue-timeline-container").shouldHave(text(label3));
            $("#discussion_bucket").$(".js-issue-timeline-container").shouldHave(text(label5));
        }

        @Step("Check that the title is correct")
        private void checkTitle() {
            $("h1[class*='gh-header-title']").shouldHave(text(issueTitle));
        }
    }
}


