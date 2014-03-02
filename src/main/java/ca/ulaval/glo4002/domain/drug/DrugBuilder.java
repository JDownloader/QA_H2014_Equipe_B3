package ca.ulaval.glo4002.domain.drug;

import org.h2.util.StringUtils;

public class DrugBuilder {
	public Din din;
	public String name;
	public String description;
	
	public DrugBuilder din(Din din) {
		this.din = din;
		return this;
	}
	
	public DrugBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public DrugBuilder description(String description) {
		this.description = description;
		return this;
	}
	
	public Drug build() {
		Drug drug = new Drug(this);
		if (din == null 
				&& StringUtils.isNullOrEmpty(name)) {
			throw new IllegalStateException();
		}
        return drug;
    }
}