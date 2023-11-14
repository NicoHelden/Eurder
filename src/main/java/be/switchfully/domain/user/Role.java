package be.switchfully.domain.user;

import java.util.List;

public enum Role {
    ADMIN(List.of(Feature.ADD_ITEM, Feature.UPDATE_ITEM, Feature.GET_ALL_USERS, Feature.GET_CUSTOMER_DETAILS, Feature.GET_ITEMS_SHIPPING_TODAY, Feature.GET_ITEM_OVERVIEW)),
    CUSTOMER(List.of(Feature.ORDER_ITEM, Feature.GET_REPORT_ORDERS, Feature.REORDER_EXISTING_ORDER));

    private final List<Feature> featureList;

    Role(List<Feature> featureList) {
        this.featureList = featureList;
    }

    public boolean hasAccessTo(Feature feature) {
        return featureList.contains(feature);
    }

}
