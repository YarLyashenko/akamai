## Akamai test exercise with Selenium Webdriver

#####How to run:

With chrome:

`mvn clean test`

With firefox:

`mvn clean test -Dbrowser=firefox`


######Test scenarios:

Feature: Unlogged customer is able to search for a job
Scenario: Search for a job matching given criteria
Given Customer is on ' http://akamaijobs.referrals.selectminds.com/
When Customer specifies job title : Test
And Location : PL-Krakow
And clicks on 'Search'
Then Any job offers are found
-----------------------------------------------------------------
Feature: Customer is notified when no offers match given criteria
Scenario: Search for a job matching given criteria
Given Customer is on ' http://akamaijobs.referrals.selectminds.com/ '
When Customer specifies job title : XXX
And clicks on 'Search'
Then Notification about no offers found is displayed
