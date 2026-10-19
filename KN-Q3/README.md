# KN-Q3 – Mini Helpdesk Ticket System

Konsolenprogramm für eingehende Support-Tickets. Die Warteschlange verarbeitet Tickets strikt FIFO. Eine separate Übersicht sortiert offene Tickets nach Priorität (1 zuerst) und danach nach Erstellungszeit.

## Starten

Voraussetzung ist ein installiertes JDK. Unter Windows im Projektordner `run.bat` starten. Das Skript kompiliert die Java-Dateien und startet anschliessend das Programm.

## Funktionen

- Tickets mit automatisch erhöhter ID, Titel, Beschreibung, Erstellungszeit, Priorität 1–5 und Status `OPEN`, `IN_PROGRESS` oder `DONE`.
- Priorität 1–2: Start-SLA von 30 Minuten; Priorität 3–5: Start-SLA von 2 Stunden.
- FIFO-Warteschlange mit `enqueue`, `dequeue`, `peek`, `size` und `isEmpty`.
- Start des ältesten wartenden Tickets mit Zuweisung an eine Technikerin oder einen Techniker.
- Abschliessen nur für Tickets mit Status `IN_PROGRESS`.
- Ansichten für offene Tickets nach FIFO und nach Priorität.
- Tagesbericht mit heute erstellten und abgeschlossenen Tickets, durchschnittlicher Bearbeitungszeit sowie Anzahl und Liste der SLA-Verletzungen.

Die SLA wird als Zeit bis zum Start des Tickets ausgewertet. Ein Ticket verletzt die SLA, wenn es nach der Frist gestartet wurde oder nach Ablauf der Frist noch nicht gestartet ist. Die Bearbeitungszeit im Bericht wird von der Erstellung bis zum Abschluss gemessen.

## Bedienung

Im Menü können Tickets erstellt, FIFO-gerecht gestartet und abgeschlossen sowie die beiden Ticketübersichten und der Tagesbericht angezeigt werden. Priorität 1 ist am dringendsten.
