package ru.klepov.hw36;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

/**
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@PWA(name = "hw36 loadbalancer frontend", shortName = "hw36 frontend")
@Theme("my-theme")
public class AppShell implements AppShellConfigurator {
}
