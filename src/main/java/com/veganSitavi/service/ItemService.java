package com.veganSitavi.service;

import com.veganSitavi.domain.Item;
import com.veganSitavi.repository.ProductoRepository;
import com.veganSitavi.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ItemService {   
    
    //Session para guardar el ArrayList de Item del carrito de compra...
    @Autowired
    private HttpSession session;
    
    //Este método suma 1 al item que queremos comprar,
    //crea la lista si no existe...
    public void save(Item item) {
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        if (lista==null) {
            //Si es la primera ocasión que queremos un producto... 
            //se crea el arreglo y listo...
            lista = new ArrayList<>();
        }
        var existe=false;
        for(Item i : lista) {
            if(Objects.equals(i.getIdProducto(), item.getIdProducto())) {
                existe = true;
                if(i.getExistencias()> i.getCantidad()) {
                    i.setCantidad(i.getCantidad()+1);
                }
                break;
            }
        }
        if (!existe) { //Si no está previamente en la lista
            item.setCantidad(1);
            lista.add(item);
        }
        session.setAttribute("listaItems", lista);
    }
    
    //Este método sólo busca en la lista el item con el idProducto buscado,
    public Item getItem(Item item) {
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        if (lista==null) {
            //Si la variable de session no existe... tampoco hay items...
            return null;
        }        
        for(Item i : lista) {
            if(Objects.equals(i.getIdProducto(), item.getIdProducto())) {                
                return i;
            }
        }
        //Si recorre toda la lista y no lo encuentra... se retorna null
        return null;
    }
    
    //Este método retorna la lista de items quye están en la session.
    public List<Item> getItems() {
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");        
        return lista;
    }
    
    //Este método retorna el total de venta que está en el carrito.
    public double getTotal() {
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems"); 
        double total=0;
        if (lista!=null) {
            for(Item i : lista) {
                total+=i.getCantidad()*i.getPrecio();
            }
        }        
        return total;
    }
    
    //El siguiente método elimina un item de la varible de session
    public void delete(Item item) {
        //Se recupera la variable de session
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        //Se valida si la lista existe...
        if (lista != null) {
            //Se busca el idProducto en la lista
            boolean existe = false;
            var posicion = -1;
            for (Item i : lista) {
                posicion++;
                if (Objects.equals(i.getIdProducto(), item.getIdProducto())) {
                    existe = true;
                    break;
                }
            }
            //Validamos si es la primera vez que se ingresa el producto en el carrito
            if (existe) { // si existe lo elimino
                lista.remove(posicion);
                session.setAttribute("listaItems", lista);
            }
        }
    }

    //El siguiente método actualiza la cantidad de elementos de un producto del carrito de compras
    public void update(Item item) {
        //Se recupera la variable de session
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        //Se valida si la lista existe...
        if (lista != null) {
            //Se busca el idProducto en la lista
            for (Item i : lista) {
                if (Objects.equals(i.getIdProducto(), item.getIdProducto())) {
                    i.setCantidad(item.getCantidad());
                    session.setAttribute("listaItems",lista);
                    break;
                }
            }            
        }
    }
    
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProductoRepository productoRepository;  // para poder bajar la cantidad de producto
//    @Autowired
//    private FacturaRepository facturaRepository; // para poder insertar los registros de las ventas
//    @Autowired
//    private VentaRepository ventaRepository;
//    
//    public void facturar() {
//        //Se debe recuperar el usuario autenticado y obtener su idUsuario
//        String username = "";
//        var principal = SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal();
//
//        if (principal instanceof UserDetails userDetails) {
//            username = userDetails.getUsername();
//        } else {
//            if (principal != null) {
//                username = principal.toString();
//            }
//        }
//
//        if (username.isBlank()) {
//            System.out.println("username en blanco...");
//            return;
//        }
//
//        Usuario usuario = usuarioRepository.findByUsername(username);
//        if (usuario == null) {
//            System.out.println("Usuario no existe en usuarios...");
//            return;
//        }
//
//        //Se debe registrar la factura incluyendo el usuario
//        Factura factura = new Factura(usuario.getIdUsuario());
//        factura = facturaRepository.save(factura);
//
//        //Se debe registrar las ventas de cada producto -actualizando existencias-
//        @SuppressWarnings("unchecked")
//        List<Item> listaItems = (List) session.getAttribute("listaItems");
//        if (listaItems != null) {
//            double total = 0;
//            for (Item i : listaItems) {
//                var producto = productoRepository.getReferenceById(i.getIdProducto());
//                if (producto.getExistencias() >= i.getCantidad()) {
//                    Venta venta = new Venta(factura.getIdFactura(),
//                            i.getIdProducto(),
//                            i.getPrecio(),
//                            i.getCantidad());
//                    ventaRepository.save(venta);
//                    producto.setExistencias(producto.getExistencias() - i.getCantidad());
//                    productoRepository.save(producto);
//                    total += i.getCantidad() * i.getPrecio();
//                }
//            }
//
//            //Se debe registrar el total de la venta en la factura
//            factura.setTotal(total);
//            facturaRepository.save(factura);
//
//            //Se debe limpiar el carrito la lista...
//            listaItems.clear();
//        }
//    }
}