package ru.klepov.hw36;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import ru.klepov.hw36.services.BalancerService;

import java.util.Arrays;
import java.util.List;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@CssImport(value = "./recipe/textfieldlabelleft/textfieldlabelleft.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {
    private final BalancerService balancer;
    private Grid<RegistryItem> gNodes;
    private TextField tfPingResult;

    public MainView(BalancerService balancer) {
        this.balancer = balancer;
        initComponents();
    }

    private void initComponents() {
        var sNodesTitle = new Span("Список нод:");
        gNodes = new Grid<>(RegistryItem.class, false);
        gNodes.addColumn(RegistryItem::getHost).setHeader("Хост");
        gNodes.addColumn(RegistryItem::getRegisterTime).setHeader("Дата регистрации");
        gNodes.addColumn(RegistryItem::getCounter).setHeader("Счетчик");
        gNodes.setWidth("800px");

        var buttonRefresh = new Button("Обновить", e -> refreshListener(e));
        buttonRefresh.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        var buttonSendPing = new Button("Послать ping", e -> sendPing(e));
        buttonSendPing.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        tfPingResult = new TextField("Результат пинга:", "", "");
        tfPingResult.addThemeName("label-left");
        tfPingResult.setWidth("600px");
        tfPingResult.setReadOnly(true);

        var pingArea = new HorizontalLayout(buttonSendPing, tfPingResult);
        pingArea.setAlignItems(FlexComponent.Alignment.CENTER);
        pingArea.getStyle().setBackgroundColor("#CCD5FF");
        pingArea.getStyle().setBorderRadius("5px");
        pingArea.getStyle().setPaddingLeft("10px");

        add(sNodesTitle, gNodes, buttonRefresh, pingArea);
    }

    private void refreshListener(ClickEvent<Button> b) {
        try {
            gNodes.setItems(balancer.getListNodes());
        } catch (Exception ex) {
            Notification notification = Notification
                    .show("Ошибка выборки списка нод");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void sendPing(ClickEvent<Button> b) {
        try {
            tfPingResult.setValue(balancer.ping());
        } catch (Exception ex) {
            Notification notification = Notification
                    .show("Ошибка отправки пинга");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

}
