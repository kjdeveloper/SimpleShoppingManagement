module persistence {
    requires gson;
    requires java.sql;

    exports jankowiak.kamil.persistence.model;
    exports jankowiak.kamil.persistence.repositories.impl;
    exports jankowiak.kamil.persistence.model.enums;
    exports jankowiak.kamil.persistence.repositories.converters;
    exports jankowiak.kamil.persistence.repositories;

    opens jankowiak.kamil.persistence.model;
}