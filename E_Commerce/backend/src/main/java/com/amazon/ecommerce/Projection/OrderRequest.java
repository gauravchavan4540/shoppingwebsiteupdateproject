package com.amazon.ecommerce.Projection;

public class OrderRequest {
	private int[] prodIds;
	private int[] prodQuantities;

	public int[] getProdIds() {
		return prodIds;
	}

	public int[] getProdQuantities() {
		return prodQuantities;
	}
}
