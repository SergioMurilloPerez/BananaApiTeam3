package es.netmind.banana_invoices.controller;

import es.netmind.banana_invoices.models.Propietario;
import es.netmind.banana_invoices.models.User;
import es.netmind.banana_invoices.persistence.UserRepository;
import es.netmind.banana_invoices.services.IInventario;
import es.netmind.banana_invoices.services.InventarioImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/propietarios")
public class PropietariosController {
	
	@Autowired
	IInventario inventarioRepository;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Propietario>> getAllUsers() {
        List<Propietario> propietarios = inventarioRepository.findAllPropietarios();
        return new ResponseEntity<>(propietarios, HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPropietario(@RequestBody @Valid Propietario newProp) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      
        inventarioRepository.savePropietario(newProp);
        return new ResponseEntity<>(newProp, HttpStatus.CREATED);
    }

}
