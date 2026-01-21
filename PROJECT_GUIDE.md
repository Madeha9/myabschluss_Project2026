# 🎓 Projekt-Leitfaden: Agile Entwicklung & Dokumentation

Willkommen zum Java-Aufbau-Projekt! Dieser Guide zeigt dir, wie du GitHub als professionelles Werkzeug für Planung, Code und Dokumentation nutzt.

---

## 1. Setup deines Projekt-Boards
Jeder Trainee nutzt ein eigenes Board zur Steuerung.

1.  **Erstellen:** Gehe auf dein GitHub-Profil -> `Projects` -> `New Project` -> `Board` (Vorlagen-Name: "Agile Kanban").
2.  **Verknüpfen:** * Klicke im Projekt oben rechts auf `...` -> `Settings`.
    * Wähle `Linked repositories` -> Füge dieses Repository hinzu.
3.  **Automatisierung (Auto-Add):**
    * Gehe im Projekt zu `Workflows` -> `Auto-add to project`.
    * Klicke auf `Edit`, wähle dein Repo und filtere nach `is:issue`. 
    * **Save and turn on**. (Jetzt landen neue Issues automatisch auf dem Board).
4.  **Sprints (Iterationen) anlegen:**
    * Klicke im Board auf das `+` neben einer Spalte -> Feldtyp `Iteration`.
    * Nenne es **"Sprints"**. Erstelle 3 Iterationen (Woche 1, 2 und 3).

---

## 2. Requirements & Tickets
Wir arbeiten mit einer klaren Hierarchie aus **Features** (Ziele) und **Stories** (Aufgaben).

### A. Feature erstellen (Das "Was")
* Gehe im Repo auf `Issues` -> `New Issue` -> `Feature Request`.
* Ein Feature beschreibt eine große funktionale Einheit (z.B. "REST-API für Benutzer").

### B. User Stories erstellen (Das "Wie")
* Erstelle für jedes Feature mehrere `User Stories`.
* **Verknüpfung:** Kopiere die Issue-Nummer der Story (z. B. `#10`) in die Checkliste deines Feature-Issues. So siehst du dort den Fortschrittsbalken.

---

## 3. Sprint-Planung
Bevor du mit dem Coden startest:
1.  Gehe in dein **Projekt-Board**.
2.  Öffne die neuen Story-Karten in der Spalte `Todo`.
3.  Weise jeder Story einen **Sprint** (1, 2 oder 3) zu.
4.  Wähle eine Story aus und schiebe sie in `In Progress`.

---

## 4. Dokumentation "as Code"
Dokumentation gehört in dieses Repository, nicht in externe Tools.

| Bereich | Pfad im Projekt | Inhalt |
| :--- | :--- | :--- |
| **Architektur** | `/docs/architecture/` | Entscheidungen (ADRs) und UML-Diagramme. |
| **Features** | `/docs/features/` | Fachliche Beschreibung (Was macht das Feature?). |
| **Setup** | `/docs/setup.md` | Wie installiere ich die DB/Umgebung? |

---

## 5. Der Daily Workflow
1.  **Branching:** Erstelle aus dem Issue heraus einen Branch (`feature/issue-id-name`).
2.  **Commits:** Nutze die Issue-ID in der Nachricht (z. B. `feat: add auth service (#10)`).
3.  **Doku:** Wenn du Code änderst, aktualisiere zeitgleich das passende `.md` File im `/docs` Ordner.
4.  **Pull Request:** Verknüpfe den PR mit dem Keyword `closes #10`. Sobald der PR gemerged ist, schließt sich das Ticket automatisch.

---

> **Tipp:** Nutze das Board als deinen "Single Point of Truth". Wenn es nicht im Board steht, existiert es nicht!
