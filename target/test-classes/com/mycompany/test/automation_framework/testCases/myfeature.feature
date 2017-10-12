Feature: New User should be able to Register to the service 

@SmokeTest  
 Scenario: New User Should Register using a Client 
  
    Given A client that Uses Some service 
    When user Register and confirm his email
    Then User logout 
 
 @RegressionTest
 Scenario: Existing users can Sign in 
  
    Given A client that Uses Some service 
    When user existing user uses his email id to login
    Then user is logged in
    And user can logout
 

  @SmokeTest  
  @RegressionTest
 Scenario: Existing users can Sign in 
  
    Given A client that Uses Some service 
    When user existing user uses his email id to login
    Then user is logged in
    And user can logout
 