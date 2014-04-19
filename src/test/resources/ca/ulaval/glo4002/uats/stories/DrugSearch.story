Narrative:
As a archiviste
I want to chercher des médicaments par leur nom
In order totrouver le DIN dans le cas d'une prescription manuscrite par un médecin
 
Scenario: Recherche avec trop peu de caractères
When je cherche un médicaments avec moins de caractères que la limite requise
Then une erreur est retournée
Then cette erreur a le code "DIN001"

Scenario: Recherche avec un mot clé valide dans le nom
When je cherche des médicaments avec un mot-clé qui se retrouve dans quelques noms de médicaments
Then la liste de médicaments retournée contient ceux-ci

Scenario: Recherche avec un mot clé valide dans la description
When je cherche des médicaments avec un mot-clé qui se retrouve dans quelques descriptions de médicaments
Then la liste de médicaments retournée contient ceux-ci

Scenario: Recherche avec un mot clé invalide dans le nom
When je cherche des médicaments avec un mot-clé qui ne se retrouve pas dans aucun nom de médicaments
Then la liste de médicaments retournée est vide

Scenario: Recherche avec un mot clé invalide dans le description
When je cherche des médicaments avec un mot-clé qui ne se retrouve pas dans aucune description de médicaments
Then la liste de médicaments retournée est vide

Scenario: Recherche avec un mot clé contenant un patron générique
When je cherche des médicaments avec un mot-clé qui contient un patron générique
Then la liste de médicaments retournée contient ceux-ci
