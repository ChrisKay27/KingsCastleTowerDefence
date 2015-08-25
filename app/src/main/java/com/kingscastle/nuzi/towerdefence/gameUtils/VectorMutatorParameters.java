package com.kingscastle.nuzi.towerdefence.gameUtils;

class VectorMutatorParameters
{
	private float dxdt;
	private float dydt;
	private vector vectorToMutate;

	private float reverseAfter;





	public float getDxdt() {
		return dxdt;
	}

	public void setDxdt(float dxdt) {
		this.dxdt = dxdt;
	}

	public float getDydt() {
		return dydt;
	}

	public void setDydt(float dydt) {
		this.dydt = dydt;
	}

	public vector getVectorToMutate() {
		return vectorToMutate;
	}

	public void setVectorToMutate(vector vectorToMutate) {
		this.vectorToMutate = vectorToMutate;
	}

	public float getReverseAfter() {
		return reverseAfter;
	}

	public void setReverseAfter(float reverseAfter) {
		this.reverseAfter = reverseAfter;
	}


}
