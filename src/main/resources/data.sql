INSERT INTO roles (role_name)
VALUES ('ADMINISTRADOR')
    ON CONFLICT (role_name) DO NOTHING;

INSERT INTO roles (role_name)
VALUES ('ASISTENTE')
    ON CONFLICT (role_name) DO NOTHING;

INSERT INTO roles (role_name)
VALUES ('VISITANTE')
    ON CONFLICT (role_name) DO NOTHING;
