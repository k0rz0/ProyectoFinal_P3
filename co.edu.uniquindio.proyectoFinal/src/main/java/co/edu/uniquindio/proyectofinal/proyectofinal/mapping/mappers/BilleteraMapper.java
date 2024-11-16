package co.edu.uniquindio.proyectofinal.proyectofinal.mapping.mappers;
import co.edu.uniquindio.proyectofinal.proyectofinal.mapping.dto.CuentaDTO;
import co.edu.uniquindio.proyectofinal.proyectofinal.mapping.dto.UsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyectofinal.model.Cuenta;
import co.edu.uniquindio.proyectofinal.proyectofinal.model.Usuario;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BilleteraMapper {
    BilleteraMapper INSTANCE = Mappers.getMapper(BilleteraMapper.class);

    @Named("usuarioToUsuarioDTO")
    UsuarioDTO usuarioToUsuarioDTO(Usuario usuario);
    Usuario usuarioDTOToUsuario(UsuarioDTO usuarioDTO);
    @IterableMapping(qualifiedByName = "usuarioToUsuarioDTO")
    List<UsuarioDTO> getUsuariosDTO(List<Usuario> listUsuarios);
    //Cuenta
    @Named("cuentaToCuentaDTO")
    CuentaDTO cuentaToCuentaDTO(Cuenta cuenta);
    Cuenta cuentaDTOToCuenta(CuentaDTO cuentaDTO);
    @IterableMapping(qualifiedByName = "cuentaToCuentaDTO")
    List<CuentaDTO> getCuentaDTO(List<Cuenta> listCuentas);


    /*@Named("transaccionToTransaccionDTO")
    @Mapping(source = "idTransaccion", target = "idTransaccion")
    @Mapping(source = "fecha", target = "fecha")
    @Mapping(source = "tipoTransaccion", target = "tipoTransaccion")
    @Mapping(source = "monto", target = "monto")
    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "categoria", target = "categoria")
    TransaccionDTO transaccionToTransaccionDTO(Transaccion transaccion);
    Transaccion transaccionDTOToTransaccion(TransaccionDTO transaccionDTO);
    @IterableMapping(qualifiedByName = "transaccionToTransaccionDTO")
    List<TransaccionDTO> getTransaccionesDTO(List<Transaccion> listTransacciones);*/
}
