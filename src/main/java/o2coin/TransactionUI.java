package o2coin;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@SpringUI
public class TransactionUI extends UI {

    final Grid<O2CoinTransaction> grid;
    final TextField filter;
    private final TransactionRepository repo;
    private final TransactionEditor editor;
    private final Button addNewBtn;

    @Autowired
    public TransactionUI(TransactionRepository repo, TransactionEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(O2CoinTransaction.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New transaction", FontAwesome.PLUS);
    }

    @Override
    protected void init(VaadinRequest request) {
        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
        setContent(mainLayout);

        grid.setHeight(300, Unit.PIXELS);
        grid.setColumns("id", "value","privKey","senderPrivKey","gasPrice","gas");

        filter.setPlaceholder("Filter by last name");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listCustomers(e.getValue()));

        // Connect selected O2CoinTransaction to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editCustomer(e.getValue());
        });

        // Instantiate and edit new O2CoinTransaction the new button is clicked
        addNewBtn.addClickListener(e -> editor.editCustomer(new O2CoinTransaction("", "")));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listCustomers(filter.getValue());
        });

        // Initialize listing
        listCustomers(null);
    }

    // tag::listCustomers[]
    void listCustomers(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
        }
    }
    // end::listCustomers[]

}
