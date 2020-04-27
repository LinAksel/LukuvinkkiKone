Feature: User can filter list by tag

    Scenario: user filters by tag
        Given list is selected
        When tag "paroni" is entered
        Then page has content "Paroni von Münchhausen"

    Scenario: user filters by empty tag
        Given list is selected
        When empty tag is entered
        Then list page opens


