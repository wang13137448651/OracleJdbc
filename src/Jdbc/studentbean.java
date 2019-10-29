package Jdbc;

import java.io.Serializable;
import java.math.BigDecimal;

public class studentbean implements Serializable{
	private BigDecimal SID;
	private String NAME;
	private BigDecimal AGE;
	private String SEX;
	public studentbean(BigDecimal sID, String nAME, BigDecimal aGE, String sEX) {
		super();
		SID = sID;
		NAME = nAME;
		AGE = aGE;
		SEX = sEX;
	}
	public studentbean() {
		super();
	}
	public BigDecimal getSID() {
		return SID;
	}
	public void setSID(BigDecimal sID) {
		SID = sID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public BigDecimal getAGE() {
		return AGE;
	}
	public void setAGE(BigDecimal aGE) {
		AGE = aGE;
	}
	public String getSEX() {
		return SEX;
	}
	public void setSEX(String sEX) {
		SEX = sEX;
	}
	@Override
	public String toString() {
		return "studentbean [SID=" + SID + ", NAME=" + NAME + ", AGE=" + AGE + ", SEX=" + SEX + "]";
	}

}
