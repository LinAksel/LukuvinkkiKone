Feature: User can list all existing items

    Scenario: user sees a list
        Given list is selected
        Then list page opens

    Scenario: user gives a link without http://
        Given add new is selected
        When title "Pussikaljaromaani", link "www.teos.fi/kirjat/kaikki/2004/pussikaljaromaani.html", description "Most awesome book ever" and tags "humour, tragedy" are given
        Given list is selected
        Then http:// is added in the beginning of link
