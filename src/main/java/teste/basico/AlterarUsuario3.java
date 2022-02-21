package teste.basico;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import modelo.basico.Usuario;

public class AlterarUsuario3 {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exercicios-jpa");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();;
		
		Usuario usuario = em.find(Usuario.class, 8L);
		em.detach(usuario); //inativa para não persistir no banco
		
		usuario.setNome("Harrisson Dutra");
		
		em.merge(usuario); //persiste no banco
		em.getTransaction().commit();
		
		em.close();
		emf.close();

	}

}
