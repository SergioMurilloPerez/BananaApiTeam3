package es.netmind.banana_invoices.controller;

import es.netmind.banana_invoices.models.Propietario;
import es.netmind.banana_invoices.models.Recibo;
import es.netmind.banana_invoices.models.User;
import es.netmind.banana_invoices.persistence.UserRepository;
import es.netmind.banana_invoices.services.IInventario;
import es.netmind.banana_invoices.services.InventarioImpl;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/recibos")
public class RecibosController {
   
	@Autowired
	IInventario inventarioRepository;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Recibo>> getAllRecibos() {
        List<Recibo> recibos = inventarioRepository.findAllRecibos();
        return new ResponseEntity<>(recibos, HttpStatus.OK);
    }
    
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recibo> getRecibo( @PathVariable @ApiParam(name = "id", value = "Product id", example = "1") Long id ) {
     Recibo recibo = inventarioRepository.findReciboById(id);
     return new ResponseEntity<>(recibo, HttpStatus.OK);
    }
    
    @GetMapping(value = "/{id}/validar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<String>> getReciboValido(@PathVariable @ApiParam(name = "id", value = "Product id", example = "1") Long id ) {
    	Set<String> reciboVal = inventarioRepository.esValidoRecibo(id);
        return new ResponseEntity<>(reciboVal, HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createRecibo(@RequestBody @Valid Recibo newRecibo) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
       // String enc_password = newRecibo.getPassword(); //passwordEncoder.encode(newUser.getPassword());
    	Propietario prop = null;
    	boolean asocia = false;
    	if (null != newRecibo.getPropietario()) {
    		prop = newRecibo.getPropietario();
        	prop = inventarioRepository.savePropietario(prop);
        	newRecibo.setPropietario(null);
        	asocia = true;
    	}
    	
        Recibo savedRec = inventarioRepository.saveRecibo(newRecibo);
        
        if (asocia) {
        	 inventarioRepository.asocia(savedRec.getId(), prop.getPid());
        }
       
        
        return new ResponseEntity<>(newRecibo, HttpStatus.CREATED);
    }
    
    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateRecibo(@RequestBody @Valid Recibo updRecibo) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
       // String enc_password = newRecibo.getPassword(); //passwordEncoder.encode(newUser.getPassword());
    	
    	if (null != inventarioRepository.findReciboById(updRecibo.getId())) {
    		inventarioRepository.saveRecibo(updRecibo);
    	}
        return new ResponseEntity<>(updRecibo, HttpStatus.OK);
    }
    

}
