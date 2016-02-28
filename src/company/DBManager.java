package company;

import javax.persistence.EntityManager;

public class DBManager {
    public static final EntityManager em = 
            javax.persistence.Persistence.createEntityManagerFactory("companyPU").createEntityManager();
}