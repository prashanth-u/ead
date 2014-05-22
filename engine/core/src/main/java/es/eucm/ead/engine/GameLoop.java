/**
 * eAdventure is a research project of the
 *    e-UCM research group.
 *
 *    Copyright 2005-2014 e-UCM research group.
 *
 *    You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *
 *    e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *
 *          CL Profesor Jose Garcia Santesmases 9,
 *          28040 Madrid (Madrid), Spain.
 *
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *
 * ****************************************************************************
 *
 *  This file is part of eAdventure
 *
 *      eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with eAdventure.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.eucm.ead.engine;

import ashley.core.Component;
import ashley.core.Engine;
import ashley.core.Entity;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import es.eucm.ead.engine.entities.EngineEntity;

/**
 * Game loop. Updates if it is playing.
 */
public class GameLoop extends Engine {

	private EntityPool entityPool;

	private boolean playing = true;

	public GameLoop() {
		super();
		entityPool = new EntityPool();
	}

	public EntityPool getEntityPool() {
		return entityPool;
	}

	@Override
	public void update(float deltaTime) {
		if (playing) {
			super.update(deltaTime);
		}
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public boolean isPlaying() {
		return playing;
	}

	/**
	 * Retrieves a clean entity from the Engine pool. In order to add it to the
	 * world, use {@link #addEntity(Entity)}.
	 * 
	 * @return clean entity from pool.
	 */
	public EngineEntity createEntity() {
		return entityPool.obtain();
	}

	/**
	 * Remove an entity from this Engine
	 * 
	 * @param entity
	 *            The Entity to remove
	 */
	@Override
	public void removeEntity(Entity entity) {
		super.removeEntity(entity);

		if (EngineEntity.class.isAssignableFrom(entity.getClass())) {
			EngineEntity pooledEntity = EngineEntity.class.cast(entity);

			for (Actor child : pooledEntity.getGroup().getChildren()) {
				Object o = child.getUserObject();
				if (o instanceof EngineEntity) {
					removeEntity((EngineEntity) o);
				}
			}

			entityPool.free(pooledEntity);
		}
	}

	/**
	 * Retrieves a new component from the Engine pool. It will be placed back in
	 * the pool whenever it's removed from an entity or the entity itself it's
	 * removed.
	 * 
	 * @param componentType
	 *            type of the component to create
	 * @return obtains an available pooled component of the required type
	 */
	public <T extends Component> T createComponent(Class<T> componentType) {
		return Pools.obtain(componentType);
	}

	public class EntityPool extends Pool<EngineEntity> {

		@Override
		protected EngineEntity newObject() {
			return new EngineEntity();
		}
	}
}
