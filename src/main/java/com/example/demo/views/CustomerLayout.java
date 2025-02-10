package com.example.demo.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

/**
 * CustomerLayout is the base layout for all customer-related views.
 */
public class CustomerLayout extends AppLayout implements RouterLayout {

    public CustomerLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Customer Portal");
        logo.getStyle().set("margin", "0").set("font-size", "1.5em").set("color", "var(--lumo-primary-color)");

        Anchor logout = new Anchor("/logout", "Logout");

        HorizontalLayout header = new HorizontalLayout(logo, logout);
        header.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        header.setWidth("100%");
        header.getStyle()
                .set("padding", "var(--lumo-space-m)")
                .set("background-color", "var(--lumo-base-color)")
                .set("box-shadow", "0px 2px 4px rgba(0, 0, 0, 0.1)");

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink nutritionPlan = new RouterLink("Nutrition Plans", com.example.demo.views.NutritionPlanView.class);


        VerticalLayout drawer = new VerticalLayout(
                nutritionPlan
            
        );

        addToDrawer(drawer);
    }
}
