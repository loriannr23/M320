# KN-D3-Lorian – Football Match Tracker

## Idee

Die Konsolen-App fragt nach einem Bundesliga-Spieltag und zeigt die Spiele, Spielstände und erfassten Tore aus der OpenLigaDB an.

## D3: Delegation und Trennung

- Main startet nur die Anwendung.
- ConsoleView liest den Spieltag ein und zeigt Ergebnisse.
- FootballApplication verbindet Oberfläche und Service.
- FootballService prüft den Spieltag und delegiert die Abfrage an OpenLigaDbClient. Dabei gibt er Saison und Spieltag weiter.
- OpenLigaDbClient fragt die API ab. JsonParser liest die JSON-Antwort in Match- und Goal-Objekte.
- InvalidInputException meldet eine ungültige Eingabe.

Ablauf: ConsoleView → FootballApplication → FootballService → OpenLigaDbClient → OpenLigaDB.

## Start

JDK 11 oder neuer und eine Internetverbindung werden benötigt. Im Ordner KN-D3-Lorian die Datei run.bat ausführen. Danach einen Spieltag von 1 bis 34 eingeben.

## Besprechung

Die Delegation findet in FootballService.getMatchday statt: Der Service übergibt Saison und Spieltag an den API-Client und erhält eine Liste von Spielen zurück. Eingabe/Ausgabe, Anwendungsablauf, Prüfung und API-Aufruf liegen in getrennten Klassen.
