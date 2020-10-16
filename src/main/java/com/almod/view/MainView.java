package com.almod.view;

import com.almod.component.CustomerEditor;
import com.almod.entity.Customer;
import com.almod.service.CustomerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class MainView extends VerticalLayout {
    private final CustomerService customerService;

    private Grid<Customer> grid = new Grid<>(Customer.class);

    private final TextField filter = new TextField("", "Type to filter");
    private final Button addNewBtn = new Button("Add new");
    private final CustomerEditor customerEditor;

    @Autowired
    public MainView(CustomerService customerService, CustomerEditor customerEditor){
        this.customerService = customerService;
        this.customerEditor = customerEditor;

        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        actions.setJustifyContentMode(JustifyContentMode.CENTER);
        actions.setWidth("100%");

        customerEditor.setAlignItems(Alignment.CENTER);

        grid.setColumns("id", "name", "date", "gender");
        add(actions, grid, customerEditor);


        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listCustomers(e.getValue()));

        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            customerEditor.editCustomer(e.getValue());
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> customerEditor.editCustomer(new Customer()));

        // Listen changes made by the editor, refresh data from backend
        customerEditor.setChangeHandler(() -> {
            customerEditor.setVisible(false);
            listCustomers(filter.getValue());
        });


        listCustomers("");
    }

    void listCustomers(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(customerService.findAll());
        }
        else {
            grid.setItems(customerService.findByNameStartsWithIgnoreCase(filterText));
        }
    }
}
