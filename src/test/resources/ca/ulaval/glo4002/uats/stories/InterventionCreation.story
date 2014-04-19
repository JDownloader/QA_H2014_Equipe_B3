Narrative:
In order to être prêt (avoir tout le matériel) pour l'intervention
As a personne responsable de la planification,
I want to créer les interventions à venir.
					 
Scenario:  Créer une intervention avec des informations manquantes
Given un patient existant
And une intervention avec des informations manquantes
When j'ajoute cette intervention au dossier du patient
Then une erreur est retournée
And cette erreur a le code "INT001"

Scenario:  Créer une intervention avec un statut indéterminé
Given un patient existant
And une intervention valide avec un statut indéterminé
When j'ajoute cette intervention au dossier du patient
Then cette intervention est conservée

Scenario:  Créer une intervention avec un statut ou type invalide
Given un patient existant
And une intervention avec un statut ou un type invalide
When j'ajoute cette intervention au dossier du patient
Then une erreur est retournée
And cette erreur a le code "INT001"

Scenario:  Créer une intervention avec un patient inexistant
Given une intervention avec un patient inexistant
When j'ajoute cette intervention au dossier du patient
Then une erreur est retournée
And cette erreur a le code "INT002"
