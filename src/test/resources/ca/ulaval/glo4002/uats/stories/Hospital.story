Hospital

Narrative:
As a intervenant
I want to ajouter des prescriptions à un patient
In order to  lui fournir ses médicaments lors de son séjour et d'en garder un historique
 
Scenario: Ajouter une prescription avec des informations manquantes
Given un patient existant
And une prescription valide avec des données manquantes
When j'ajoute cette prescription au dossier du patient
Then une erreur est retournée
And cette erreur a le code "PRES001"