Narrative:
As a personne responsable de la planification
I want to créer les interventions à venir
In order to être prêt (avoir tout le matériel) pour l'intervention

Scenario: Créer une intervention avec des informations manquantes
Given une intervention avec des informations manquantes
When j'ajoute cette intervention au dossier du patient
Then une erreur est retournée
And cette erreur a le code "INT001"

Scenario: Créer une intervention avec un statut indéterminé
Given une intervention valide avec un statut indéterminé
When j'ajoute cette intervention au dossier du patient
Then cette intervention est conservée

Scenario: Créer une intervention avec un patient inexistant
Given une intervention avec un patient inexistant
When j'ajoute cette intervention au dossier du patient
Then une erreur est retournée
And cette erreur a le code "INT002"

Scenario: Créer une intervention avec un statut invalide
Given une intervention avec un statut invalide
When j'ajoute cette intervention au dossier du patient
Then une erreur est retournée
And cette erreur a le code "INT001"
