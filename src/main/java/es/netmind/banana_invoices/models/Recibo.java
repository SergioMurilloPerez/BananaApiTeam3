package es.netmind.banana_invoices.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "recibo")
public class Recibo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "propietario")
    private Propietario propietario;
    private LocalDate fecha_emision;
    @NonNull
    private LocalDate fecha_vencimiento;
    private String nombre_contacto;
    private String direccion_contacto;
    private String direccion_envio;
    @NonNull
    private String nombre_producto;
    @NonNull
    private int cantidad;
    @NonNull
    private float precio_unitario;
    @NonNull
    private double base_imponible;
    @NonNull
    private float impuestos;
    @NonNull
    private double total;
    @NonNull
    private boolean estado;
    @NonNull
    private boolean valido;

    public double calcular_total() {
        return base_imponible + base_imponible * impuestos;
    }

    public Set<String> esValido() {
        Set errores = new HashSet();

        if (!(propietario != null && propietario.getPid() > 0)) errores.add("propietario:" + propietario);
        if (fecha_vencimiento == null) errores.add("fecha_vencimiento:"+fecha_vencimiento);
        if (!(nombre_producto != null && !nombre_producto.trim().equals(""))) errores.add("nombre_producto:"+nombre_producto);
        if (cantidad <= 0) errores.add("cantidad:"+cantidad);
        if (precio_unitario <= 0) errores.add("precio_unitario:"+precio_unitario);
        if (base_imponible <= 0 || (base_imponible != cantidad * precio_unitario)) errores.add("base_imponible:"+base_imponible);
        if (impuestos < 0) errores.add("impuestos:"+impuestos);
        if (total != calcular_total()) errores.add("total:"+total);

        return errores;
    }
}
