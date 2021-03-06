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
package es.eucm.ead.editor.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

import es.eucm.ead.editor.control.commands.CompositeCommand;
import es.eucm.ead.editor.control.commands.FieldCommand;
import es.eucm.ead.editor.control.commands.ListCommand.AddToListCommand;
import es.eucm.ead.engine.entities.EngineEntity;
import es.eucm.ead.schema.components.ModelComponent;
import es.eucm.ead.schema.editor.components.Documentation;
import es.eucm.ead.schema.editor.components.Parent;
import es.eucm.ead.schema.editor.components.SceneMap;
import es.eucm.ead.schema.editor.data.Cell;
import es.eucm.ead.schema.entities.ModelEntity;
import es.eucm.ead.schemax.FieldName;

/**
 * A class with statics methods to query parts of the model
 */
public class Q {

	/**
	 * Returns the component for the class. If the element has no component of
	 * the given type, is automatically created and added to it.
	 * 
	 * @param element
	 *            the element with the component
	 * @param componentClass
	 *            the component class
	 * @return the component inside the element
	 */
	@SuppressWarnings("unchecked")
	public static <T extends ModelComponent> T getComponent(
			ModelEntity element, Class<T> componentClass) {
		for (ModelComponent component : element.getComponents()) {
			if (component.getClass() == componentClass) {
				return (T) component;
			}
		}
		try {
			ModelComponent component = ClassReflection
					.newInstance(componentClass);
			element.getComponents().add(component);
			return (T) component;
		} catch (ReflectionException e) {
			Gdx.app.error("Model",
					"Error creating component " + componentClass, e);
		}
		return null;
	}

	/**
	 * Iterates through the model entity hierarchy to recover its root ancestor
	 */
	public static ModelEntity getRootAncestor(ModelEntity modelEntity) {
		if (modelEntity == null) {
			return null;
		}

		ModelEntity rootEntity = modelEntity;
		while (true) {
			Parent parent = getComponent(rootEntity, Parent.class);
			if (parent != null && parent.getParent() != null) {
				rootEntity = parent.getParent();
			} else {
				break;
			}
		}
		return rootEntity;
	}

	/**
	 * Returns the {@link Documentation#name} attribute of the model entity
	 * 
	 * @param defaultValue
	 *            value if the name is null
	 */
	public static String getName(ModelEntity modelEntity, String defaultValue) {
		Documentation documentation = getComponent(modelEntity,
				Documentation.class);
		return documentation.getName() == null ? defaultValue : documentation
				.getName();
	}

	/**
	 * @return whether the given element contains a component with the given
	 *         class
	 */
	public static <T extends ModelComponent> boolean hasComponent(
			ModelEntity element, Class<T> componentClass) {
		if (element == null) {
			return false;
		}

		for (ModelComponent component : element.getComponents()) {
			if (component.getClass() == componentClass) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the entity associated to the given actor. Returns {@code null} if
	 *         no entity associated is to the actor
	 */
	public static EngineEntity getActorEntity(Actor actor) {
		Object o = actor.getUserObject();
		if (o instanceof EngineEntity) {
			return ((EngineEntity) o);
		}
		return null;
	}

	/**
	 * @return the model entity associated to the given actor. Returns
	 *         {@code null} if no model entity is associated to the actor
	 */
	public static ModelEntity getModelEntity(Actor actor) {
		Object o = actor.getUserObject();
		if (o instanceof EngineEntity) {
			return ((EngineEntity) o).getModelEntity();
		}
		return null;
	}

	/**
	 * @return returns the first object of given class in the iterable
	 */
	public static <T> T getObjectOfClass(Iterable iterable, Class<T> clazz) {
		for (Object o : iterable) {
			if (o.getClass() == clazz) {
				return (T) o;
			}
		}
		return null;
	}

	/**
	 * Creates a cell with the given id in the first empty space found in the
	 * map and returns the cell. If there is no empty space a null will be
	 * returned. Note that the cell is not added to the map. It's responsibility
	 * of the developer to add the returned cell to the map if it isn't null.
	 * 
	 * @param id
	 * @param sceneMap
	 * @return the new {@link Cell} or null if no empty space was available.
	 */
	public static Cell createCell(String id, SceneMap sceneMap) {

		Array<Cell> cells = sceneMap.getCells();
		int columns = sceneMap.getColumns();
		int rows = sceneMap.getRows();

		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < columns; ++j) {
				if (Q.cellInCoords(i, j, cells) == null) {
					Cell cell = new Cell();
					cell.setSceneId(id);
					cell.setColumn(j);
					cell.setRow(i);
					return cell;
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * @param row
	 * @param column
	 * @param cells
	 * @return the {@link Cell} with the given coordinates or null if there is
	 *         none.
	 */
	public static Cell cellInCoords(int row, int column, Array<Cell> cells) {
		for (Cell cell : cells) {
			if (cell.getRow() == row && cell.getColumn() == column) {
				return cell;
			}
		}
		return null;
	}
}
