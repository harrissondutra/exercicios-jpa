package infra;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class DAO<E> {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private Class<E> classe;

	static {
		try {
			emf = Persistence.createEntityManagerFactory("exercicios-jpa");
		} catch (Exception e) {
			// Criar Log (Log4j)
		}
	}

	public DAO() {
		this(null);
	}

	public DAO(Class<E> classe) {
		this.classe = classe;
		em = emf.createEntityManager();
	}

	public E obterPorID(Object id) {
		return em.find(classe, id);
	}
	
	public DAO<E> abrirTransact() {
		em.getTransaction().begin();
		return this;
	}

	public DAO<E> fecharTransact() {
		em.getTransaction().commit();
		return this;
	}

	public DAO<E> incluir(E entidade) {
		em.persist(entidade);
		return this;
	}

	public DAO<E> incluirAtomico(E entidade) {
		return this.abrirTransact().incluir(entidade).fecharTransact();
	}

	public List<E> obterTodos() {
		return this.obterTodos(10, 0);
	}

	public List<E> obterTodos(int qtde, int deslocamento) {
		if (classe == null) {
			throw new UnsupportedOperationException("Classe nula.");
		}

		String jpql = "select e from " + classe.getName() + " e";
		TypedQuery<E> query = em.createQuery(jpql, classe);
		query.setMaxResults(qtde);
		query.setFirstResult(deslocamento);
		return query.getResultList();

	}
	
	public void fecharDAO(){
		em.close();
	}

}
