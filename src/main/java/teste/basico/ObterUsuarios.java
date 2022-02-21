package teste.basico;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import modelo.basico.Usuario;

public class ObterUsuarios {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exercicios-jpa");
		EntityManager em = emf.createEntityManager();
		
//		String jpql = "SELECT u FROM Usuario u";
//		TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
//		query.setMaxResults(5);
		
		//cria uma lista com a pesquisa
		List<Usuario> usuarios = em
				.createQuery("SELECT u FROM Usuario u", Usuario.class)
				.setMaxResults(5) //limita a quantidade que será exibido
				.getResultList();//recebe a consulta
				
		//Imprimir os dados		
		for (Usuario usuario : usuarios) {
			System.out.println("ID: " + usuario.getId() + " - Email: " + usuario.getEmail());
		}
		
		
		emf.close();
		em.close();
	}

}
