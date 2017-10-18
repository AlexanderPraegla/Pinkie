INSERT INTO announcement (text, createdon) VALUES ('Dies ist die erste Ankündigung auf der neuen Homepage', now());
INSERT INTO announcement (text, createdon) VALUES ('Es liegen nun alle Ergebnisse vom Wochenende vor. Spielberichte sind auch schon einige vorhanden. Einfach unter Top News nachlesen.',now());
INSERT INTO announcement (text, createdon) VALUES ('Biber-Einlaufkinder beim Bundesligisten HC Erlangen: Am Samstag, 16.12.2017 werden zum Bundesligaspiel des HC Erlangen gegen Ludwigshafen/Friesenheim unsere Biber-Kids mit den Bundesligaspielern des HC Erlangen einlaufen! Wer zum Spiel des „einzigen bayerischen Handball-Bundesligisten“ mitfahren möchte, meldet sich bitte bei Christian (christian.sack@altenerding-biber.de) an. Bei ausreichendem Interesse werden wir wieder einen Bus organisieren. Die Kosten für die ermäßigten Eintrittskarten und die Busfahrt werden bekannt gegeben, sobald verfügbar.',now());

INSERT INTO team (name, league, orderId, created_on) VALUES ('Herren 1', 'Bezirksoberliga', 1, now());
INSERT INTO team (name, league, orderId, created_on) VALUES ('Herren 2', 'Bezirksliga', 2, now());
INSERT INTO team (name, league, orderId, created_on) VALUES ('Damen 1', 'Bezirksoberliga', 3, now());
INSERT INTO team (name, league, orderId, created_on) VALUES ('Damen 2', 'Bezirksliga', 4, now());


INSERT INTO member (city, createdon, email, firstname, homepage, lastname, mobilenumber, nickname, password, phonenumber, street, zipcode) VALUES ('Erding', now(), 'max_biber@sva.org', 'Max', '', 'Mustermann', '+49123456789', 'Maxl', '1234', '', 'Biberstraße 8', '85435');
INSERT INTO member (city, createdon, email, firstname, homepage, lastname, mobilenumber, nickname, password, phonenumber, street, zipcode) VALUES ('Erding', now(), 'eva_biber@sva.org', 'Eva', '', 'Musterfrau', '+49987654321', '', '1234', '', 'Biberstraße 8', '85435');