Narrative:
As a archiviste
I want to chercher des médicaments par leur nom
In order totrouver le DIN dans le cas d'une prescription manuscrite par un médecin
 
Scenario: Recherche avec trop peu de caractères
When je cherche un médicaments avec moins de caractères que la limite requise
Then une erreur est retournée*
Then cette erreur a le code "DIN001"

Scenario: Recherche avec un mot clé valide dans le nom
When je cherche des médicaments avec un mot-clé qui se retrouve dans quelques noms de médicaments
Then la liste de médicaments retournée contient ceux-ci

Scenario: Recherche avec un mot clé valide dans la description
When je cherche des médicaments avec un mot-clé qui se retrouve dans quelques descriptions de médicaments
Then la liste de médicaments retournée contient ceux-ci
