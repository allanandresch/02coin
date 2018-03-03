package o2coin;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A simple example to introduce building forms. As your real application is probably much
 * more complicated than this example, you could re-use this form in multiple places. This
 * example component is only used in TransactionUI.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX. See e.g. AbstractForm in Viritin
 * (https://vaadin.com/addon/viritin).
 */
@SpringComponent
@UIScope
public class TransactionEditor extends VerticalLayout {

    private final TransactionRepository repository;
    /* Fields to edit properties in O2CoinTransaction entity */
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    /* Action buttons */
    Button save = new Button("Save", FontAwesome.SAVE);
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", FontAwesome.TRASH_O);
    CssLayout actions = new CssLayout(save, cancel, delete);
    Binder<O2CoinTransaction> binder = new Binder<>(O2CoinTransaction.class);
    /**
     * The currently edited o2CoinTransaction
     */
    private O2CoinTransaction o2CoinTransaction;

    @Autowired
    public TransactionEditor(TransactionRepository repository) {
        this.repository = repository;

        addComponents(firstName, lastName, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> repository.save(o2CoinTransaction));
        delete.addClickListener(e -> repository.delete(o2CoinTransaction));
        cancel.addClickListener(e -> editCustomer(o2CoinTransaction));
        setVisible(false);
    }

    public final void editCustomer(O2CoinTransaction c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            o2CoinTransaction = repository.findOne(c.getId());
        } else {
            o2CoinTransaction = c;
        }
        cancel.setVisible(persisted);

        // Bind o2CoinTransaction properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(o2CoinTransaction);

        setVisible(true);

        // A hack to ensure the whole form is visible
        save.focus();
        // Select all text in firstName field automatically
        firstName.selectAll();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
    }

    public interface ChangeHandler {

        void onChange();
    }

}
