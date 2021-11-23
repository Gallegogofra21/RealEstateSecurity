package realEstate.salesianos.triana.dam.realEstate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import realEstate.salesianos.triana.dam.realEstate.models.Interesa;
import realEstate.salesianos.triana.dam.realEstate.models.Vivienda;
import realEstate.salesianos.triana.dam.realEstate.repositories.InteresaRepository;
import realEstate.salesianos.triana.dam.realEstate.services.base.BaseService;
import realEstate.salesianos.triana.dam.realEstate.users.model.Usuario;
import realEstate.salesianos.triana.dam.realEstate.users.services.UserEntityService;

@Service
public class InteresaService extends BaseService<Interesa, Long, InteresaRepository> {
    @Autowired
    private UserEntityService usuarioService;
    @Autowired
    private ViviendaService viviendaService= new ViviendaService();

    public Interesa findByInteresaPk (Long id1,Long id2){
        Interesa i1 = new Interesa();
        i1= repositorio.findByViviendaIdAndInteresadoId(id1,id2);
        return i1;
    }

    //Este método sirve para eliminar el interes de un interesado por una vivienda, se borra así una
    //línea de la tabala Interesa.
    public void eliminarInteresaPorPk(Long id1,Long id2){

        //Se consigue una instancia de los objetos con los que trabajaremos.
        Vivienda vivienda = viviendaService.findById(id1).get();
        Usuario usuario = usuarioService.findById(id2).get();
        Interesa i1=repositorio.findByViviendaIdAndInteresadoId(id1,id2);

        //Usamos los helpers de interesa para borrar a su interesado y su vivienda.
         i1.removeFromInteresado(usuario);
         i1.removeFromVivienda(vivienda);

         //Actualizamos los servicios de vivienda e interesado.
         viviendaService.save(vivienda);
         usuarioService.save(usuario);

        //Finalmente borramos el interesa en cuestión.
        delete(i1);

    }
}
