# KN-D3-Lorian – Football Match Tracker

## Kurzbeschreibung

Die Konsolenanwendung zeigt Bundesliga-Spiele und ihren Spielverlauf mithilfe der OpenLigaDB-API an. Man kann Spiele nach Spieltag oder Team suchen oder ein einzelnes Match über seine Match-ID öffnen. Die Anwendung zeigt den Spielstand sowie erfasste Tore mit Spielminute, Torschütze, Spielstand nach dem Tor und Kennzeichnung für Elfmeter, Eigentor oder Nachspielzeit.

Der Umfang ist bewusst kleiner als eine vollständige OneFootball-App: KN-D3 konzentriert sich auf das Abrufen und Anzeigen von Spielen und Toren. OpenLigaDB stellt die Daten ohne API-Key als JSON bereit. Die Daten werden durch eine Community gepflegt; deshalb können einzelne Wettbewerbe oder Ereignisse unvollständig sein.

API-Dokumentation: <https://api.openligadb.de/index.html>

## Starten

Voraussetzung ist ein JDK 11 oder neuer sowie eine Internetverbindung. Unter Windows im Ordner `KN-D3` `run.bat` ausführen. Das Skript kompiliert die Java-Dateien und startet die Anwendung. Es ist kein API-Key nötig.

## Bedienung

1. Spiele eines Bundesliga-Spieltags anzeigen. Ein leeres Saisonfeld verwendet automatisch das aktuelle Saison-Startjahr.
2. Alle Spiele eines Teams in einer Saison suchen; ein Teil des Teamnamens reicht.
3. Match-ID eingeben, um Spielinformationen und Torereignisse anzuzeigen.
0. Programm beenden.

Beispiele für die API: Spieltag 7 der Saison 2026 mit Saison-Startjahr `2026`; Team-Suche `Bayern`; Match-Details über eine Match-ID aus der Spieltags- oder Teamliste.

## Delegation und Zuständigkeiten

- `Main` startet die Anwendung.
- `FootballApplication` koordiniert Menü, Service-Aufrufe und Fehlerausgabe.
- `ConsoleView` übernimmt Ein- und Ausgabe.
- `FootballService` validiert die Eingaben und delegiert die Datenabfrage an `FootballDataProvider`.
- `OpenLigaDbClient` implementiert `FootballDataProvider`, ruft die HTTP-API auf und bildet JSON-Daten auf die eigenen Modelle `Match`, `Team` und `Goal` ab.
- `JsonParser` liest die JSON-Antwort ohne zusätzliche Bibliothek.

## Exceptions und Eingabeprüfung

- `InvalidInputException` meldet ungültige Saisonjahre, Spieltage, Match-IDs oder Teamnamen.
- `FootballApiException` meldet Netzwerk-, HTTP- und Antwortformatfehler.
- `FootballApplication` fängt diese Fehler ab und zeigt eine verständliche Meldung, statt das Programm unerwartet zu beenden.

## Grenzen

Die Anwendung liest Daten aus der OpenLigaDB und verändert keine Spielergebnisse. Sie zeigt die von der API gelieferten Ereignisse an; die Aktualität und Vollständigkeit hängen von den dort gepflegten Daten ab.
