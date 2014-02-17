package ca.ulaval.glo4002.dao;

import javax.persistence.EntityExistsException;

import ca.ulaval.glo4002.exceptions.ItemNotFoundException;

public interface IDataAccessObject<PrimaryKey, Entity> {
	public void create(Entity entity) throws EntityExistsException;

	public void update(Entity entity);

	public void delete(Entity entity);
	
	public Entity get(PrimaryKey id) throws ItemNotFoundException;
}
