Narrative: Détection des intéractions
As a intervenant
I want to le système m'aide à détecter des intéractions médicamenteuses
In order to prévenir une réaction grave du patient pouvant causer la mort
 
Scenario: Ajout d'une prescription avec une interaction
Given un patient existant
Given une prescription associée à ce patient
When j'ajoute une prescription pour laquelle il y a une interaction
Then une erreur est retournée
And cette erreur a le code "PRES002"

Scenario: Ajout d'une prescription sans interaction
Given un patient existant
Given une prescription associée à ce patient
When j'ajoute une prescription pour laquelle il n'y a pas d'interaction
Then cette prescription est conservée

Scenario: Ajout d'une prescription sans din d'un médicament présentant une interaction
Given un patient existant
Given une prescription associée à ce patient
When j'ajoute une prescription avec nom de médicament pour laquelle il y a une interaction
Then cette prescription est conservée