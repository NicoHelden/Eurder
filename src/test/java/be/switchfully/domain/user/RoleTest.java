package be.switchfully.domain.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void adminRoleHasAccessToAdminFeatures() {
        assertThat(Role.ADMIN.hasAccessTo(Feature.ADD_ITEM)).isTrue();
        assertThat(Role.ADMIN.hasAccessTo(Feature.UPDATE_ITEM)).isTrue();

        assertThat(Role.ADMIN.hasAccessTo(Feature.GET_ITEM_OVERVIEW)).isTrue();


        assertThat(Role.ADMIN.hasAccessTo(Feature.ORDER_ITEM)).isFalse();

    }

    @Test
    void customerRoleHasAccessToCustomerFeatures() {
        assertThat(Role.CUSTOMER.hasAccessTo(Feature.ORDER_ITEM)).isTrue();
        assertThat(Role.CUSTOMER.hasAccessTo(Feature.GET_REPORT_ORDERS)).isTrue();



        assertThat(Role.CUSTOMER.hasAccessTo(Feature.ADD_ITEM)).isFalse();

    }

}