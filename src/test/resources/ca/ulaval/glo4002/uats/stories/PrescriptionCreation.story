Narrative:
In order to lui fournir ses médicaments lors de son séjour et d'en garder un historique
As a intervenant
I want to ajouter des prescriptions à un patient
 
Scenario: Ajouter une prescription avec des informations manquantes
Given un patient existant
And une prescription valide avec des données manquantes
When j'ajoute cette prescription au dossier du patient
Then une erreur est retournée
And cette erreur a le code "PRES001"

Scenario: Ajouter une prescription avec un médicament connu
Given un patient existant
And une prescription valide avec DIN
When j'ajoute cette prescription au dossier du patient
Then cette prescription est conservée

Scenario: Ajouter une prescription avec un médicament par nom
Given un patient existant
And une prescription valide avec nom de médicament
When j'ajoute cette prescription au dossier du patient
Then cette prescription est conservée

Scenario: Ajouter une prescription avec un médicament inconnu
Given un patient existant
And une prescription valide avec DIN
And que ce DIN n'existe pas
When j'ajoute cette prescription au dossier du patient
Then une erreur est retournée
And cette erreur a le code "PRES001"