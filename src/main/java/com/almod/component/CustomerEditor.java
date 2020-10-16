package com.almod.component;

import com.almod.enginer.Gender;
import com.almod.entity.Customer;
import com.almod.service.CustomerService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@SpringComponent
@UIScope
public class CustomerEditor extends VerticalLayout implements KeyNotifier {
    private CustomerService customerService;
    private Customer customer;

    private TextField name = new TextField("Name");
    private RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();

    /* Action buttons */
    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private Binder<Customer> binder = new Binder<>(Customer.class);

    private ChangeHandler changeHandler;

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public CustomerEditor(CustomerService customerService){
        this.customerService = customerService;

        radioGroup.setLabel("Gender");
        radioGroup.setItems("Male", "Female");
        radioGroup.addThemeVariants(RadioGroupVariant.MATERIAL_VERTICAL);
        radioGroup.setValue("Male");

        add(name, radioGroup, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e-> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }

    void delete() {
        customerService.delete(customer);
        changeHandler.onChange();
    }

    void cancel(){
        editCustomer(customer);
        changeHandler.onChange();
    }

    void save() {
        customer.setGender(radioGroup.getValue().equalsIgnoreCase("male") ? Gender.MALE : Gender.FEMALE);
        customer.setDate(new Date());

        customerService.save(customer);
        changeHandler.onChange();
    }

    public final void editCustomer(Customer c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            customer = customerService.getById(c.getId()).get();
        }
        else {
            customer = c;
        }
        cancel.setVisible(persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(customer);

        setVisible(true);

        // Focus gender initially
        //gender.focus();
    }
}
