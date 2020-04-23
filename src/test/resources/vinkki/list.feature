Feature: User can list all existing items

    Scenario: user sees a list
        Given list is selected
        Then list page opens

    Scenario: user adds read date
        Given mark as read is selected
        Then read date is added

    Scenario: user removes read date
        Given remove read date is selected
        Then read date is removed


