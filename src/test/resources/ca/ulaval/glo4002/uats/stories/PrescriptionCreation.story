Narrative:
In order to lui fournir ses médicaments lors de son séjour et d'en garder un historique
As a intervenant
I want to ajouter des prescriptions à un patient
 
Scenario: Ajouter une prescription avec des informations manquantes
Given un patient existant
Given une prescription valide avec des données manquantes
When j'ajoute cette prescription au dossier du patient
Then une erreur est retournée
Then cette erreur a le code "PRES001"