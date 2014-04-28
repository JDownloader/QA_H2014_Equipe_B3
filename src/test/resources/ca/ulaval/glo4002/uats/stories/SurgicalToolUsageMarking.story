Narrative:
As a infirmière de bloc
I want to pouvoir indiquer quels instruments sont utilisés lors de l'intervention
In order to assurer une traçabilité des instruments

Scenario: Ajouter un instrument valide
Given un instrument avec des informations valides
When j'ajoute cet instrument à une intervention
Then cet instrument est conservé

Scenario: Ajouter un instrument anonyme à une intervention
Given un instrument anonyme avec des informations valides
When j'ajoute cet instrument à une intervention autorisant les instruments anonymes
Then cet instrument est conservé

Scenario: Ajouter un instrument avec des informations manquantes
Given un instrument avec des informations manquantes
When j'ajoute cet instrument à une intervention
Then une erreur est retournée
And cette erreur a le code "INT010"

Scenario: Ajouter un instrument avec un numéro de série déjà utilisé
Given un instrument avec un numéro de série déjà utilisé
When j'ajoute cet instrument à une intervention
Then une erreur est retournée
And cette erreur a le code "INT011"

Scenario: Ajouter un instrument anonyme à une intervention interdisant les instruments anonymes
Given un instrument anonyme avec des informations valides
When j'ajoute cet instrument à une intervention interdisant les instruments anonymes
Then une erreur est retournée
And cette erreur a le code "INT012"

Scenario: Modifier un instrument
Given un instrument avec des informations valides
When j'ajoute cet instrument à une intervention
And je modifie cet instrument avec des informations valides
Then cet instrument est modifié

Scenario: Modifier le statut d'un instrument
Given un instrument avec des informations valides
When j'ajoute cet instrument à une intervention
And je modifie le statut de cet instrument
Then cet instrument est modifié

Scenario: Utiliser plusieurs fois un instrument
Given un instrument non-utilisé
When j'ajoute cet instrument à une intervention
And j'utilise cet instrument
And j'utilise cet instrument une autre fois
Then cet instrument est modifié

Scenario: Modifier le code d'un instrument
Given un instrument avec des informations valides
When j'ajoute cet instrument à une intervention
And je modifie le code de cet instrument
Then une erreur est retournée
And cette erreur a le code "INT010"

Scenario: Modifier le numéro de série d'un instrument par un numéro de série existant
Given un instrument avec des informations valides
When j'ajoute cet instrument à une intervention
And je modifie le le numéro de série de cet instrument par un numéro de série existant
Then une erreur est retournée
And cette erreur a le code "INT011"

Scenario: Modifier le numéro de série d'un instrument dans une intervention interdisant les instruments anonymes
Given un instrument avec des informations valides
When j'ajoute cet instrument à une intervention interdisant les instruments anonymes
And je rend l'instrument anonyme
Then une erreur est retournée
And cette erreur a le code "INT012"
