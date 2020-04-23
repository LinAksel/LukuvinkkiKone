Feature: User can mark and unmark item as read

    Scenario: user adds read date
        Given mark as read is selected
        Then read date is added

    Scenario: user removes read date
        Given remove read date is selected
        Then read date is removed