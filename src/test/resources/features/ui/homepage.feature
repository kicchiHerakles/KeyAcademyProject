Feature: Home Page Element Validations

  Background:
    Given Home page is loaded

  @KEY-2 @Web-Desktop
  Scenario:
    Then user should see the following menus in the navigation bar
      | Courses  |
      | Services |
      | About Us |
      | Contact  |

  @KEY-3 @Web-Desktop
  Scenario:
    Then user should see following buttons
      | CREATE AN ACCOUNT |
      | SIGN IN           |

  @KEY-4 @Web-Desktop
  Scenario:
    Then page name should be "KEY ACADEMY"

  @KEY-7 @Web-Desktop
  Scenario:
    Then user should see the following titles in order through the Home Page slider
      | English for Kids               |
      | Professional English Trainings |
      | YDS Course Started             |

  @KEY-13 @Web-Desktop
  Scenario:
    When user scrolls down to the end of the page
    Then user should see -To the top- button with an arrow upwards