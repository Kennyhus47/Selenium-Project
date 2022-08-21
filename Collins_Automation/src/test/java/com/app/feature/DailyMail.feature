@SmokeTest
Feature: User Verifying Dailymail Application

  Background: 
    Given User is on Application home page

  Scenario: Verify web page of dailymail application
    Then I Verify the webpage displays current date and time
    When I Navigate to the Sport menu
    When I Click on the Football sub navigation item
    Then I Verify primary navigation colour Sport and secondary navigation Football are same
    And I Click on the first article displayed and traverse to the gallery image
    And I Click on gallery icon appearing on the right-hand side corner
    Then I Verify previous and next arrows buttons
    And I Navigate to a video embedded within the article
    And I Click the full screen button on the right-hand corner of the video
    When I Click on the Facebook share icon and verify it opens Facebook modal dialog
    And I Click the full screen button on the right-hand corner of the video
    And I Click on collapse icon on video
    And I Click on close icon
    And I Get "Liverpool" team points
    And I Get "Liverpool" team position
