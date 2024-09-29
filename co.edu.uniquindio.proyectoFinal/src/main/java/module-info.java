module co.edu.uniquindio.proyectofinal.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.mapstruct;

    opens co.edu.uniquindio.proyectofinal.proyectofinal to javafx.fxml;
    exports co.edu.uniquindio.proyectofinal.proyectofinal;

    opens co.edu.uniquindio.proyectofinal.proyectofinal.model to javafx.fxml;
    exports co.edu.uniquindio.proyectofinal.proyectofinal.model;

    opens co.edu.uniquindio.proyectofinal.proyectofinal.viewController to javafx.fxml;
    exports co.edu.uniquindio.proyectofinal.proyectofinal.viewController;

    opens co.edu.uniquindio.proyectofinal.proyectofinal.controller to javafx.fxml;
    exports co.edu.uniquindio.proyectofinal.proyectofinal.controller;

    opens co.edu.uniquindio.proyectofinal.proyectofinal.mapping.mappers to javafx.fxml;
    exports co.edu.uniquindio.proyectofinal.proyectofinal.mapping.mappers;
}