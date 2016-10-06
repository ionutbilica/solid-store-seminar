package com.luxoft.training.solid.store.persistenceservice.file;

import com.luxoft.training.solid.store.persistence.CartData;
import com.luxoft.training.solid.store.persistence.ProductData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SerializableCartData implements Serializable {

	private static final long serialVersionUID = 512277321410964047L;
	private final int id;
    private final List<SerializableProductData> products;
    private final boolean hasDelivery;

    public SerializableCartData(CartData cd) {
        this.id = cd.getId();
        this.products = toSerializableProductDataList(cd.getProducts());
        this.hasDelivery = cd.hasDelivery();
    }

    public int getId() {
        return id;
    }

    public List<SerializableProductData> getProducts() {
        return products;
    }

    public boolean isHasDelivery() {
        return hasDelivery;
    }

    public CartData asCartData() {
        return new CartData(id, toProductDataList(products), hasDelivery);
    }

    private List<SerializableProductData> toSerializableProductDataList(List<ProductData> list) {
        List<SerializableProductData> sList = new ArrayList<>(list.size());
        for (ProductData pd : list) {
            sList.add(new SerializableProductData(pd.getName(), pd.getPrice(), pd.getCount()));
        }
        return sList;
    }

    private List<ProductData> toProductDataList(List<SerializableProductData> sList) {
        List<ProductData> list = new ArrayList<>(sList.size());
        for (SerializableProductData spd : sList) {
            list.add(spd.asProductData());
        }
        return list;
    }

    @Override
    public String toString() {
        return "SerializableCartData{" +
                "id=" + id +
                ", products=" + products +
                ", hasDelivery=" + hasDelivery +
                '}';
    }
}
