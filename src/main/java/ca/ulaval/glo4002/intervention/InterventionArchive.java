package ca.ulaval.glo4002.intervention;

import java.util.ArrayList;

import ca.ulaval.glo4002.exceptions.InterventionNotFoundException;

public class InterventionArchive {
	ArrayList<Intervention> archive;

	public InterventionArchive() {
		archive = new ArrayList<Intervention>();
	}

	public Intervention getIntervention(int id) throws InterventionNotFoundException {

		for (Intervention intervention : archive) {
			if (intervention.getId() == id)
				return intervention;
		}

		throw new InterventionNotFoundException(String.format("Cannot find 'Intervention' with id '%s'.", id));
	}

	public void addIntervention(Intervention intervention) {
		archive.add(intervention);
	}
}
