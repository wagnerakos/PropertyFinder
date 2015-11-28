package dal;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.Property;
import entities.QueryParameterHolder;

/**
 * Session Bean implementation class UserBean
 */
@Stateless
public class PropertyFacade extends AbstractFacade<Property> {
	
	@EJB
	private AddressFacade addressFacade;
	
	@PersistenceContext
	EntityManager em;

    /**
     * Default constructor. 
     */
    public PropertyFacade() {
    	super(Property.class);
    }
       
    @Override
	protected EntityManager em() {
		return em;
	}
    
    public List<Property> findAllWithAddressWithFilters(Integer first, Integer pageSize, List<QueryParameterHolder> filters, String sortField, String asc, boolean onlyActive) {
    	String queryString = "SELECT p FROM Property p LEFT JOIN FETCH p.address address JOIN p.user user";    	
    	String filterExpression = "";
    	if (onlyActive) {
    		filterExpression +=  " WHERE p.active = TRUE";
    	}
    	for (QueryParameterHolder param : filters) {
    		if (param.getValue() != null && !param.getValue().equals("")) {
    			if (filterExpression.isEmpty()) {
    				filterExpression += " WHERE ";
    			} else {
    				filterExpression += " AND ";
    			}
    			filterExpression += getFilterExpression(param);
    		}
    	}
    	queryString += filterExpression;
    	queryString += getSortExpression(sortField, asc);
    	TypedQuery<Property> query = em().createQuery(queryString, Property.class);
    	setParameters(query, filters);
    	query.setFirstResult(first);
    	query.setMaxResults(pageSize);
    	return query.getResultList();
    }
    
    public Integer getCountWithFilters(List<QueryParameterHolder> filters, boolean onlyActive) {
    	String queryString = "SELECT COUNT(p) FROM Property p JOIN p.address address JOIN p.user user";
    	String filterExpression = "";
    	if (onlyActive) {
    		filterExpression +=  " WHERE p.active = TRUE";
    	}
    	for (QueryParameterHolder param : filters) {
    		if (param.getValue() != null && !param.getValue().equals("")) {
    			if (filterExpression.isEmpty()) {
    				filterExpression += " WHERE ";
    			} else {
    				filterExpression += " AND ";
    			}
    			filterExpression += getFilterExpression(param);
    		}
    	}
    	queryString += filterExpression;
    	TypedQuery<Long> query = em().createQuery(queryString, Long.class);
    	setParameters(query, filters);
    	return query.getSingleResult().intValue();
    }
    
    private String getFilterExpression(QueryParameterHolder param) {
    	String res = "";
    	res += param.getFieldName();
    	res += " ";
    	res += param.getOperator();
    	res += " :";
    	res += param.getParameterName();
    	return res;
    }
    
    private String getSortExpression(String sortField, String asc) {
    	String res = "";
    	if (sortField != null) {
	    	res += " ORDER BY ";
	    	res += sortField;
	    	res += " ";
	    	res += asc;
    	}
    	return res;
    }
    
    private void setParameters(Query query, List<QueryParameterHolder> filters) {
    	for (QueryParameterHolder param : filters) {
    		if (param.getValue() != null && !param.getValue().equals("")) {
    			Object value = null;
    			if (param.getValueType().equals(Double.class)) {
    				value = Double.valueOf(param.getValue());
    			} else if (param.getValueType().equals(Integer.class)) {
    				value = Integer.valueOf(param.getValue());
    			} else if (param.getValueType().equals(Long.class)) {
    				value = Long.valueOf(param.getValue());
    			} else {
    				if (param.getOperator().equals("LIKE")) {
    					value = "%" + param.getValue() + "%";
    				} else {
    					value = param.getValue();
    				}
    			}
    			query.setParameter(param.getParameterName(), value);
    		}
    	}
    }
    
    public Property getWithDataById(Long id) {
    	TypedQuery<Property> query = em().createQuery("SELECT p FROM Property p LEFT JOIN FETCH p.address LEFT JOIN FETCH p.user LEFT JOIN FETCH p.images WHERE p.id = :id", Property.class);
    	query.setParameter("id", id);
    	return query.getSingleResult();
    }
}
