Feature: A user can add a new item with a valid title

    Scenario: user can add new item with unused title
        Given add new is selected
        When title "Pussikaljaromaani", link "http://www.teos.fi/kirjat/kaikki/2004/pussikaljaromaani.html", description "Most awesome book ever" and tags "humour, tragedy" are given
        Then system will respond with "Lisääminen onnistui!"

    Scenario: user can not add a title which already exists
        Given add new is selected
        When title "Paroni von Münchhausen", link "http://www.gutenberg.org/ebooks/48623", description "Most awesome book ever" and tags "humour, tragedy" are given
        Then system will respond with "Teokselle on jo luotu vinkki!"


    Scenario: user can not add a title that is less than 3 characters long
        Given add new is selected
        When title "Pa", link "http://www.gutenberg.org/ebooks/48623", description "Most awesome book ever" and tags "humour, tragedy" are given
        Then system will respond with "Nimen pitää olla vähintään kolme merkkiä pitkä!"

    Scenario: user can not add an empty description
        Given add new is selected
        When title "Paroni von Münchhausen", link "http://www.gutenberg.org/ebooks/48623", description "" and tags "humour, tragedy" are given
        Then system will respond with "Kuvausta ei ole annettu."

    
    Scenario: user can not add an empty tags String
        Given add new is selected
        When title "Paroni von Münchhausen", link "http://www.gutenberg.org/ebooks/48623", description "Most awesome book ever " and tags "" are given
        Then system will respond with "Tägejä ei ole annettu."

    Scenario: user can not add an empty link
        Given add new is selected
        When title "Ronja ryövärinpoika", link "", description "Most awesome book ever " and tags "humour, tragedy" are given
        Then system will respond with "Vinkillä pitää olla myös linkki!"

    Scenario: user can not add wrong type of link
        Given add new is selected
        When title "Ronja ryövärinpoika", link "vääränlainen linkki", description "Most awesome book ever " and tags "humour, tragedy" are given
        Then system will respond with "Anna linkki oikeassa muodossa! https://... tai www..."

    Scenario: user can not add wrong type of tag 
        Given add new is selected
        When title "Ronja ryövärinpoika", link "http://www.gutenberg.org/ebooks/48623", description "Most awesome book ever " and tags "yksi tagi" are given
        Then system will respond with "Anna tägejä enemmän kuin yksi. Tägit pitää erottaa pilkulla! Käytä vain yksisanaisia tägejä."




