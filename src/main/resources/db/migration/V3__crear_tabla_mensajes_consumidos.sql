CREATE TABLE mensajes_consumidos_registro (
    id_mensaje VARCHAR(255) PRIMARY KEY,
    topico VARCHAR(150) NOT NULL,
    estado VARCHAR(50) NOT NULL DEFAULT 'PROCESADO',
    fecha_procesamiento TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_estado_valido 
        CHECK (estado IN ('PROCESADO', 'IGNORADO'))
);
