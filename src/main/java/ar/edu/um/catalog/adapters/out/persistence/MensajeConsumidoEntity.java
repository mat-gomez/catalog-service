package ar.edu.um.catalog.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "mensajes_consumidos_registro")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MensajeConsumidoEntity {

    @Id
    @Column(name = "id_mensaje")
    private String idMensaje;

    @Column(nullable = false)
    private String topico;

    @Column(nullable = false)
    private String estado;

    @Column(name = "fecha_procesamiento", nullable = false)
    private OffsetDateTime fechaProcesamiento;
}