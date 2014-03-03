package ca.ulaval.glo4002.domain.surgicaltool;

import org.apache.commons.lang3.StringUtils;

public class SurgicalToolBuilder {

	protected String typeCode;
	protected String serialNumber;
	protected SurgicalToolStatus status;

	public SurgicalToolBuilder typeCode(String typeCode) {
		this.typeCode = typeCode;
		return this;
	}

	public SurgicalToolBuilder serialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
		return this;
	}

	public SurgicalToolBuilder status(SurgicalToolStatus status) {
		this.status = status;
		return this;
	}

	public SurgicalTool build() {
		SurgicalTool surgicalTool = new SurgicalTool(this);

		if (StringUtils.isBlank(typeCode) || status == null) {
			throw new IllegalStateException();
		}

		return surgicalTool;
	}
}
