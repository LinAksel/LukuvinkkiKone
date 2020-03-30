Feature: A user can add a new item with a valid title

    Scenario: user can add new item with unused title
        Given add new is selected
        When title "Pussikaljaromaani" and link "http://www.teos.fi/kirjat/kaikki/2004/pussikaljaromaani.html" are given
        Then system will respond with "Lisääminen onnistui!"

    Scenario: user can not add a title which already exists
        Given add new is selected
        When username "Paroni von Münchhausen" and password "http://www.gutenberg.org/ebooks/48623" are given
        Then system will respond with "Vinkki on jo olemassa!"
