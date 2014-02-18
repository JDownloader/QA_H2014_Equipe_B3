package ca.ulaval.glo4002.drug;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.h2.util.StringUtils;

@Entity(name = "DRUG")
public class Drug {
	@Id
	@Column(name = "DIN")
	private Din din = null;

	@Column(name = "DRUG_NAME")
	private String name = null;
	
	protected Drug() {
		
	}

	public Drug(Builder builder) {
		this.din = builder.din;
		this.name = builder.name;
	}
	
	public static class Builder {
		private Din din = null;
		private String name = null;
		
		public Builder din(Din din) {
			this.din = din;
			return this;
		}
		
		public Builder name(String name) {
			this.name = name;
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

	public Din getDin() {
		return this.din;
	}

	public String getName() {
		return this.name;
	}
}
