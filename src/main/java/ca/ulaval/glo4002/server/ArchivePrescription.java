package ca.ulaval.glo4002.server;

import java.util.ArrayList;

public class ArchivePrescription {
	
	ArrayList<Prescription> archive;
	
	public ArchivePrescription() {
		archive = new ArrayList<Prescription>();
	}
	
	public Prescription getPrescription(int id) throws Exception{
		
		for(Prescription prescription : archive){
			if(prescription.getId() == id)
				return prescription;
		}
		
		throw new Exception("Unable to find prescription"); //TODO: Define an exception class
	}
	
	public void addPrescription(Prescription prescription){
		archive.add(prescription);
	}
	
}
