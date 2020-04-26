Feature: User can filter list by tag

    Scenario: user filters by tag
        Given list is selected
        When tag "google" is entered
        Then page has content "google"


