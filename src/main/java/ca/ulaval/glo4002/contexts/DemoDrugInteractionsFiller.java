package ca.ulaval.glo4002.contexts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import ca.ulaval.glo4002.domain.drug.Din;
import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.drug.DrugNotFoundException;
import ca.ulaval.glo4002.domain.drug.DrugRepository;

public class DemoDrugInteractionsFiller {
	
	private static final String DRUG_SEPARATOR = ",";
	private static final String DRUG_INTERACTION_SEPARATOR = "=>";
	
	private EntityManager entityManager;
	private DrugRepository drugRepository;
	
	public DemoDrugInteractionsFiller(EntityManager entityManager, DrugRepository drugRepository) {
		this.entityManager = entityManager;
		this.drugRepository = drugRepository;
	}
	
	public void fillInteractions(Reader reader) throws IOException {
		entityManager.getTransaction().begin();
		loadDrugInteractionsFromStream(reader);
		entityManager.getTransaction().commit();
	}
	
	public void loadDrugInteractionsFromStream(Reader reader) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(reader);
		String nextLine;
		int lineNumber = 0;
		
		while ((nextLine = bufferedReader.readLine()) != null) {
			parseDrugInteraction(nextLine, ++lineNumber);
		}
		
		bufferedReader.close();
	}
	
	private void parseDrugInteraction(final String line, int lineNumber) {
		try {
			Din sourceDin = getSourceDinFromLine(line);
			Set<Din> interactiveDins = getInteractiveDinsFromLine(line);
			
			Drug drug = drugRepository.getByDin(sourceDin);
			drug.setInteractiveDinList(interactiveDins);
			drugRepository.persist(drug);
		} catch (DrugNotFoundException e) {
			throw new BadFileFormatException(String.format("Interactive at line %d could not be found in the repository.", lineNumber));
		} catch (Exception e) {
			throw new BadFileFormatException(String.format("Could not parse line %d due to bad data format.", lineNumber));
		}
	}
	
	private Din getSourceDinFromLine(final String line) {
		String din = line.substring(0, line.indexOf(DRUG_INTERACTION_SEPARATOR));
		return new Din(din);
	}
	
	private Set<Din> getInteractiveDinsFromLine(final String line) {
		Set<Din> interactiveDinList = new HashSet<Din>();
		
		String interactiveDinsCSV = line.substring(line.indexOf(DRUG_INTERACTION_SEPARATOR) + DRUG_INTERACTION_SEPARATOR.length());
		String[] interactiveDins = interactiveDinsCSV.split(DRUG_SEPARATOR);
		
		for (String interactiveDin : interactiveDins) {
			interactiveDinList.add(new Din(interactiveDin.trim()));
		}
		
		return interactiveDinList;
	}
}
