Feature: A user can add a working link to an item

    Scenario: user can not add an empty link
        Given add new is selected
        When title "Ronja ryövärinpoika", link "", description "Most awesome book ever " and tags "humour, tragedy" are given
        Then system will respond with "Vinkillä pitää olla myös linkki!"

    Scenario: user can not add wrong type of link
        Given add new is selected
        When title "Ronja ryövärinpoika", link "vääränlainen linkki", description "Most awesome book ever " and tags "humour, tragedy" are given
        Then system will respond with "Anna linkki oikeassa muodossa! https://... tai www..."

    Scenario: user gives a link without http://
        Given add new is selected
        When title "Pussikaljaromaani", link "www.teos.fi/kirjat/kaikki/2004/pussikaljaromaani.html", description "Most awesome book ever" and tags "humour, tragedy" are given
        Given list is selected
        Then http:// is added in the beginning of link
