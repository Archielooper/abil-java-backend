package com.policy.bazaar.enums;

public enum PurchasedStatusEnum {
	
	PENDING(0),APPROVED(1),REJECTED(2);
	
	private int numValue;
	
	PurchasedStatusEnum(int numValue) {
      this.numValue = numValue;
	}
	
	public int getNumValue() {
		return numValue;
	}
	
	public static PurchasedStatusEnum getEnumByNumValue(int numValue) {
		
		for(PurchasedStatusEnum e: PurchasedStatusEnum.values()) {
			if(e.numValue == numValue) {
				return e;
			}
		}
		return null;
	} 

}
